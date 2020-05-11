package com.tianchi.filter.service;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.tianchi.filter.entity.FilterBean;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.reflect.generics.tree.Tree;

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
public class BloomFilterService {

    @Autowired
    private RedissonClient redisson;

    public boolean getBloomFilter( long expectedInsertions, double falseProbability) {

        Stopwatch stopwatch = Stopwatch.createStarted();


        RBloomFilter<FilterBean> bloomFilter = redisson.getBloomFilter("sample");
        //
// 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(expectedInsertions, falseProbability);

        bloomFilter.add(new FilterBean("field1Value", "field2Value", "", "", "", "", "", "", ""));
        bloomFilter.add(new FilterBean("field5Value", "field8Value", "", "", "", "", "", "", ""));
        boolean contains = bloomFilter.contains(new FilterBean("field1Value", "field2Value", "", "", "", "", "", "", ""));
        log.info("{}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        Set<Double> doubles = hashMap(expectedInsertions, falseProbability, stopwatch.elapsed(TimeUnit.MILLISECONDS));

        for (Double aDouble : doubles) {
            System.out.println(aDouble);
        }
        return contains;
    }


    public Set<Double> hashMap(long expectedInsertions, double falseProbability, double res) {

        System.out.println("expecteds" + expectedInsertions + "false " + falseProbability + "res " + res);

        ArrayListMultimap<Double, String> objectObjectArrayListMultimap = ArrayListMultimap.create();
        objectObjectArrayListMultimap.put(res, expectedInsertions + " " + falseProbability);
        Collection<Map.Entry<Double, String>> entries = objectObjectArrayListMultimap.entries();
        Set<Double> set = new TreeSet<>();
        for (Map.Entry<Double, String> entry : entries) {

//            System.out.println(entry.getKey() + "===>" + entry.getValue());
            set.add(entry.getKey());
        }

        return set;


    }
}
