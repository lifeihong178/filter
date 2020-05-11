package com.tianchi.filter.service;

import com.tianchi.filter.common.util.BeanFieldOrderUtil;
import com.tianchi.filter.entity.FilterBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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


    /**
     * 614959183521b4b|1587457762873000|d614959183521b4b|0|311601|order|getOrder|192.168.1.3|http.status_code:200
     * 转为对象
     *
     * @param fileStr
     * @return
     */
    public List<FilterBean> dealDataAll(String fileStr) {
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

}
