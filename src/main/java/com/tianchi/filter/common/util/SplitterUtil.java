package com.tianchi.filter.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.tianchi.filter.common.cons.KeyConst;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2019-08-26
 * @Time: 11:05
 */
public class SplitterUtil {

    /**
     * str2List
     *
     * @param splitterStr
     * @return
     */
    public static List<String> splitter2List(String splitterStr) {
        if (StringUtils.isBlank(splitterStr)) {
            return Lists.newArrayList();
        }
        List<String> res = Lists.newArrayList();

        List<String> splitToList = Splitter.on(KeyConst.SPLITTER_SYMBOL).trimResults().omitEmptyStrings().splitToList(splitterStr);

        if (CollectionUtils.isNotEmpty(splitToList)) {
            splitToList.stream().map(split -> res.add(split)).collect(Collectors.toList());

        }
        return res;

    }

    /**
     * 通过splitter分割字符串
     * @param splitterStr
     * @param splitter
     * @return
     */
    public static List<String> splitter2ListWithSplitter(String splitterStr, String splitter) {
        if (StringUtils.isBlank(splitterStr)) {
            return Lists.newArrayList();
        }
        List<String> res = Lists.newArrayList();

        List<String> splitToList = Splitter.on(splitter).trimResults().omitEmptyStrings().splitToList(splitterStr);
        if (CollectionUtils.isNotEmpty(splitToList)) {
            splitToList.stream().map(split -> res.add(split)).collect(Collectors.toList());

        }
        return res;
    }

    public static void main(String[] args) {
        List<String> strings = splitter2List("123,456,789");

        strings.add("str");
        String s = ListJoinStr(strings);
        System.out.println(s);
        System.out.println(strings);
    }

    /**
     * list2Str
     *
     * @param argList
     * @return
     */
    public static String ListJoinStr(List<String> argList) {
        if (CollectionUtils.isEmpty(argList)) {
            return "";
        }
        return Joiner.on(KeyConst.SPLITTER_SYMBOL).skipNulls().join(argList);
    }

    /**
     * 对象转换为map
     * @param object
     * @return
     */
    public static Map<String, String> toMap(Object object) {
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(object));
        Map map = JSONObject.toJavaObject(jsonObject, Map.class);
        return map;
    }

}
