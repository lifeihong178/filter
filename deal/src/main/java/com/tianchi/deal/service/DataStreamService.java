package com.tianchi.deal.service;

import com.tianchi.deal.common.util.BeanFieldOrderUtil;
import com.tianchi.deal.entity.FilterBean;
import com.tianchi.deal.thread.DealDataThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-11
 * @Time: 18:58
 */
@Slf4j
@Service
public class DataStreamService {

    @Autowired
    private BloomFilterService bloomFilterService;


    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));


    /**
     * 614959183521b4b|1587457762873000|d614959183521b4b|0|311601|order|getOrder|192.168.1.3|http.status_code:200
     * 转为对象
     *
     * @param fileStr
     * @return
     */
    public List<FilterBean> dealDataAll(String fileStr) {

        if (StringUtils.isBlank(fileStr)) {
            log.info("fileStr:{} 空----", fileStr);
        }
        List<FilterBean> filterBeanList = null;
        try {
            filterBeanList = BeanFieldOrderUtil.str2List(fileStr, FilterBean.class);
        } catch (Exception e) {
            log.error("获取有序的字段出现异常：{}", e.getMessage());
        }
        return filterBeanList;
    }

    /**
     * 处理单个为对象
     *
     * @param fileStr
     * @return
     */
    public FilterBean dealDataSingle(String fileStr) {
        FilterBean filterBean = null;
        try {
            filterBean = BeanFieldOrderUtil.str2Obj(fileStr, FilterBean.class);
        } catch (Exception e) {
            log.error("获取有序的字段出现异常：{}", e.getMessage());
        }
        return filterBean;
    }
    public  void readFile() throws Exception{
        File file = new File("/Users/h/Downloads/trace2.data");
        BufferedReader buffered = new BufferedReader(new FileReader(file));
        String s = "";
        List<String> list = new ArrayList<>();
        while ((s = buffered.readLine()) != null) {
            list.add(s);
            if (list.size() == 50000) {
                executorService.execute(new DealDataThread(bloomFilterService, list));
                list = new ArrayList<>();
            }
        }
        if (list.size() > 0) {
            executorService.execute(new DealDataThread(bloomFilterService, list));
        }

    }
    public  void readStream(InputStream inputStream) throws Exception{
        BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));
        String s;
        List<String> list = new ArrayList<>();
        while ((s = buffered.readLine()) != null) {
            list.add(s);
            if (list.size() == 50000) {
                executorService.execute(new DealDataThread(bloomFilterService, list));
                list = new ArrayList<>();
            }
        }
        if (list.size() > 0) {
            executorService.execute(new DealDataThread(bloomFilterService, list));
        }

    }

    /**
     * 反射效率可能不高，使用直接赋值的方式
     *
     * @param fileStr
     * @return
     */
    public FilterBean dealDataSingleBySplitter(String fileStr, FilterBean filterBean) {
//        FilterBean filterBean = new FilterBean();

        try {
            String[] split = fileStr.split("\\|",-1);
            filterBean.setTraceId(split[0]);
            filterBean.setStartTime(split[1]);
            filterBean.setSpanId(split[2]);
            filterBean.setParentSpanId(split[3]);
            filterBean.setDuration(split[4]);
            filterBean.setServiceName(split[5]);
            filterBean.setSpanName(split[6]);
            filterBean.setHost(split[7]);
            filterBean.setTags(split[8]);
        } catch (Exception e) {
            log.error("获取有序的字段出现异常：{}", e.getMessage());
        }
        return filterBean;
    }

    public  FilterBean dealDataBySplitter(String fileStr) {
        FilterBean filterBean = new FilterBean();

        try {
            String[] split = fileStr.split("\\|");
            filterBean.setTraceId(split[0]);
            filterBean.setStartTime(split[1]);
            filterBean.setSpanId(split[2]);
            filterBean.setParentSpanId(split[3]);
            filterBean.setDuration(split[4]);
            filterBean.setServiceName(split[5]);
            filterBean.setSpanName(split[6]);
            filterBean.setHost(split[7]);
            filterBean.setTags(split[8]);
        } catch (Exception e) {
            log.error("获取有序的字段出现异常：{}", e.getMessage());
        }
        return filterBean;
    }

}
