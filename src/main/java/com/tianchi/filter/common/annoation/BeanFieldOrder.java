package com.tianchi.filter.common.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description: bean 的字段排序
 * @Date: 2019-06-14
 * @Time: 9:02
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanFieldOrder {

    /**
     * 标注属性的顺序
     * @return
     */
    int order();
}
