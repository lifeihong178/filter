package com.tianchi.filter.entity;

import com.tianchi.filter.common.annoation.BeanFieldOrder;
import lombok.Data;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-11
 * @Time: 10:06
 */
@Data
public class FilterBean {
    /**
     * 全局唯一的Id，用作整个链路的唯一标识与组装
     */
    @BeanFieldOrder(order = 1)
    private String traceId;

    /**
     * 调用的开始时间
     */
    @BeanFieldOrder(order = 2)
    private String startTime;

    /**
     * 调用链中某条数据(span)的id
     */
    @BeanFieldOrder(order = 3)
    private String spanId;

    /**
     * 调用链中某条数据(span)的父亲id，头节点的span的parantSpanId为0
     */
    @BeanFieldOrder(order = 4)
    private String parentSpanId;

    /**
     * 调用耗时
     */
    @BeanFieldOrder(order = 5)
    private String duration;

    /**
     * 调用的服务名
     */
    @BeanFieldOrder(order = 6)
    private String serviceName;

    /**
     * 调用的埋点名
     */
    @BeanFieldOrder(order = 7)
    private String spanName;

    /**
     * 机器标识，比如ip，机器名
     */
    @BeanFieldOrder(order = 8)
    private String host;

    /**
     * 链路信息中tag信息，存在多个tag的key和value信息。格式为key1:val1&key2:val2&key3:val3 比如 http.status_code:200&error:1
     */
    @BeanFieldOrder(order = 9)
    private String tags;
}
