package com.tianchi.filter.service;

import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.tianchi.filter.common.cons.KeyConst;
import com.tianchi.filter.common.util.SplitterUtil;
import com.tianchi.filter.entity.FilterBean;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
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
     * 判断http.status_code 不为200 error 为1
     *
     * @param bean
     * @return
     */
    public boolean errorData(FilterBean bean) {
        Map<String, String> map = new HashMap<>();

        String tags = bean.getTags();
        List<String> tagsStr = SplitterUtil.splitter2ListWithSplitter(tags, KeyConst.SPLITTER_AND);
        for (String keyValue : tagsStr) {
            String[] split = keyValue.split(":");
            if (KeyConst.HTTP_STATUS.equals(split[0]) || KeyConst.ERROR.equals(split[0])) {
                map.put(split[0], split[1]);
            }
        }
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
}
