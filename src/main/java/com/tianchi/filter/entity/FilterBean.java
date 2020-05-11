package com.tianchi.filter.entity;

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
    private String traceId;

    /**
     * 调用的开始时间
     */
    private String startTime;

    /**
     * 调用链中某条数据(span)的id
     */
    private String spanId;

    /**
     * 调用链中某条数据(span)的父亲id，头节点的span的parantSpanId为0
     */
    private String parentSpanId;

    /**
     * 调用耗时
     */
    private String duration;

    /**
     * 调用的服务名
     */
    private String serviceName;

    /**
     * 调用的埋点名
     */
    private String spanName;

    /**
     * 机器标识，比如ip，机器名
     */
    private String host;

    /**
     * 链路信息中tag信息，存在多个tag的key和value信息。格式为key1:val1&key2:val2&key3:val3 比如 http.status_code:200&error:1
     */
    private String tags;
}
