package com.tianchi.filter.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tianchi.filter.common.annoation.BeanFieldOrder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2019-06-14
 * @Time: 9:18
 */
public class BeanFieldOrderUtil {

    /**
     * 对含有BeanFileOrder进行排序
     *
     * @param fields
     * @return
     */
    public static Field[] getOrderedField(Field[] fields) throws Exception {
        List<Field> fieldList = Lists.newArrayList();

        for (Field field : fields) {
            BeanFieldOrder annotation = field.getAnnotation(BeanFieldOrder.class);
            if (annotation != null) {
                fieldList.add(field);
            }
        }
        if (fieldList.size() == 0) {
            throw new Exception("没有含有BeanFieldOrder的字段");
        }
        fieldList.sort(Comparator.comparingInt(o -> o.getAnnotation(BeanFieldOrder.class).order()));

        Field[] stringField = new Field[fieldList.size()];
        for (int i = 0; i < fieldList.size(); i++) {
            stringField[i] = fieldList.get(i);
        }
        return stringField;
    }

    /**
     * getList
     *
     * @param fileStr
     * @param tClass
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> List<T> str2List(String fileStr, Class<T> tClass) throws Exception {
        List<T> tList = new ArrayList<>();
        String[] splitObject = fileStr.split("\n\r");
        for (String obj : splitObject) {
            String[] split2 = obj.split("\\|");
            JSONObject jsonObject = new JSONObject(32, true);
            Field[] field = tClass.getDeclaredFields();
            Field[] orderedField = BeanFieldOrderUtil.getOrderedField(field);
            for (int i = 0; i < split2.length; i++) {
                orderedField[i].setAccessible(true);
                jsonObject.put(orderedField[i].getName(), split2[i]);
            }
            T newResult = JSONObject.parseObject(jsonObject.toJSONString(), tClass);
            tList.add(newResult);
        }
        return tList;
    }


    public static <T> T str2Obj(String fileStr, Class<T> tClass) throws Exception {
        String[] split2 = fileStr.split("\\|");
        JSONObject jsonObject = new JSONObject(32, true);
        Field[] field = tClass.getDeclaredFields();
        Field[] orderedField = BeanFieldOrderUtil.getOrderedField(field);
        for (int i = 0; i < split2.length; i++) {
            orderedField[i].setAccessible(true);
            jsonObject.put(orderedField[i].getName(), split2[i]);
        }
        T newResult = JSONObject.parseObject(jsonObject.toJSONString(), tClass);
        return newResult;
    }
}
