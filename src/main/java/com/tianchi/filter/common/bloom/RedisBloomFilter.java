//package com.tianchi.filter.common.bloom;
//
///**
// * @Author: zhouheng
// * @Created: with IntelliJ IDEA.
// * @Description:
// * @Date: 2020-05-11
// * @Time: 14:20
// */
//import com.google.common.base.Preconditions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class RedisBloomFilter {
//    @Autowired
//    RedisTemplate redisTemplate;
////    private JedisCluster cluster;
//
////    public RedisBloomFilter(JedisCluster jedisCluster) {
////        this.cluster = jedisCluster;
////    }
//
//    /**
//     * 根据给定的布隆过滤器添加值
//     */
//    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
//        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
//        int[] offset = bloomFilterHelper.murmurHashOffset(value);
//        for (int i : offset) {
//            redisTemplate.opsForValue().setBit(key, i, true);
//        }
//    }
//
//    /**
//     * 根据给定的布隆过滤器判断值是否存在
//     */
//    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
//        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
//        int[] offset = bloomFilterHelper.murmurHashOffset(value);
//        for (int i : offset) {
//            if (!redisTemplate.opsForValue().getBit(key, i)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//}
//
//
