package com.tianchi.deal.service;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.tianchi.deal.common.cons.KeyConst;
import com.tianchi.deal.common.util.SplitterUtil;
import com.tianchi.deal.entity.FilterBean;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-11
 * @Time: 14:36
 */
@Slf4j
@Service
public class BloomFilterService implements InitializingBean {

    @Autowired
    private DataStreamService dataStreamService;

    @Autowired
    private RedissonClient redisson;


    public RBloomFilter<FilterBean> bloomFilter;


    /**
     * 判断布隆过滤
     *
     * @param bean
     * @return
     */
    public boolean containBloomFilter(FilterBean bean) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean contains = bloomFilter.contains(bean);
        log.info("{}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return contains;
    }

    /**
     * 判断是否是肯定需要上报的
     *
     * @param filterBean
     * @return
     */
    public boolean judgeMustSubmit(FilterBean filterBean) {

        boolean contains = getSetQueue().contains(filterBean.getTraceId());
        return contains;

    }

    /**
     * 将值存入
     * @param filterBean
     * @return
     */
    public boolean putMustSubmit(FilterBean filterBean) {

        boolean contains = getSetQueue().add(filterBean.getTraceId());
        return contains;

    }

    /**
     * 获取set
     * @return
     */
    private RSet<Object> getSetQueue() {
        return redisson.getSet(KeyConst.SET_KEY);
    }

    /**
     * 判断http.status_code 不为200 error 为1
     *
     * @param bean
     * @return
     */
    public boolean errorData(FilterBean bean) {
        return contains(getMap(bean));
    }

    /**
     * 构件map
     * @param bean
     * @return
     */
    private Map<String, String> getMap(FilterBean bean) {
        Map<String, String> map = new HashMap<>();

        String tags = bean.getTags();
        List<String> tagsStr = SplitterUtil.splitter2ListWithSplitter(tags, KeyConst.SPLITTER_AND);
        for (String keyValue : tagsStr) {
            String[] split = keyValue.split(":");
            if (KeyConst.HTTP_STATUS.equals(split[0]) || KeyConst.ERROR.equals(split[0])) {
                map.put(split[0], split[1]);
            }
        }
        return map;
    }

    /**
     * 判断是否存在需要上报的
     * @param map
     * @return
     */
    private boolean contains(Map<String, String> map) {
        if (map.containsKey(KeyConst.HTTP_STATUS)) {
            String s = map.get(KeyConst.HTTP_STATUS);
            if (!KeyConst.HTTP_STATUS_VALUE.equals(s)) {
                return true;
            }
        }
        if (map.containsKey(KeyConst.ERROR)) {
            String s = map.get(KeyConst.ERROR);
            if (KeyConst.ERROR_VALUE.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 增加数据到布隆过滤
     *
     * @param bean
     * @return
     */
    public boolean addBloomFilter(FilterBean bean) {

        return bloomFilter.add(bean);
    }

    public Set<Double> hashMap(long expectedInsertions, double falseProbability, double res) {

        System.out.println("expecteds" + expectedInsertions + "false " + falseProbability + "res " + res);

        ArrayListMultimap<Double, String> objectObjectArrayListMultimap = ArrayListMultimap.create();
        objectObjectArrayListMultimap.put(res, expectedInsertions + " " + falseProbability);
        Collection<Map.Entry<Double, String>> entries = objectObjectArrayListMultimap.entries();
        Set<Double> set = new TreeSet<>();
        for (Map.Entry<Double, String> entry : entries) {
            set.add(entry.getKey());
        }
        return set;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.bloomFilter = redisson.getBloomFilter(KeyConst.BLOOM_FILTER);
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.0002
        bloomFilter.tryInit(55000000, 0.0002);
    }

    public void dealStreamDate(String filterStr) {
        /**
         * 转化字符串为对象
         */
        FilterBean filterBean = dataStreamService.dealDataSingleBySplitter(filterStr, new FilterBean());

        /**
         * 根据要求判断是否需要必须上报
         */
        boolean mustSubmit = judgeMustSubmit(filterBean);

        // 必须上报的
        if (mustSubmit) {

            // TODO: 2020/5/13 上报给后端程序,上报完成以后终止
            addBloomFilter(filterBean);
            putMustSubmit(filterBean);
            return;

        }

        // 判断是否需要上报的bean
        boolean errorData = errorData(filterBean);
        if (errorData) {
            // 判断字符串是否在过滤器中存在
            boolean result = containBloomFilter(filterBean);
//            如果不存在过滤其中则需要上报
            if (!result) {

                // TODO: 2020/5/13 走上报
//                上报完成后
                addBloomFilter(filterBean);
                /**
                 * 上报完成以后把tradeId存储到必须上传的
                 */
                putMustSubmit(filterBean);
            } else {
                // 存在过滤器中，则有可能需要上报
                // TODO: 2020/5/13 暂时不做处理

            }
        }
    }

    /**
     * 将需要上报的程序发送给服务端
     *
     * @param filterBean
     * @return
     */
    private boolean sendToServer(FilterBean filterBean) {

        return true;
    }
}
