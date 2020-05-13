//package com.tianchi.filter.common.bloom;
//
///**
// * @Author: zhouheng
// * @Created: with IntelliJ IDEA.
// * @Description:
// * @Date: 2020-05-11
// * @Time: 14:21
// */
//
//import com.google.common.hash.Funnels;
//
//import java.nio.charset.Charset;
//
///**
// * 基于guava分布式布隆过滤器
// */
//public class Test {
//    public static void main(String[] args) {
//        BloomFilterHelper bloomFilterHelper =  new BloomFilterHelper<>(Funnels.stringFunnel(Charset.defaultCharset()), 1000, 0.1);
//
//        RedisBloomFilter redisBloomFilter = new RedisBloomFilter();
//
//        int j = 0;
//        for (int i = 0; i < 100; i++) {
//            redisBloomFilter.addByBloomFilter(bloomFilterHelper, "bloom", i+"");
//        }
//        for (int i = 0; i < 1000; i++) {
//            boolean result = redisBloomFilter.includeByBloomFilter(bloomFilterHelper, "bloom", i+"");
//            if (!result) {
//                j++;
//            }
//        }
//        System.out.println("漏掉了" + j + "个");
//    }
//}
//
//
