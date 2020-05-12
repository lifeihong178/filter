package com.tianchi.filter.service;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.tianchi.filter.common.cons.KeyConst;
import com.tianchi.filter.entity.FilterBean;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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

    public boolean getBloomFilter(FilterBean bean) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean contains = bloomFilter.contains(bean);
        log.info("{}ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return contains;
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
