package com.tianchi.deal.common;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.base.Charsets;
import com.tianchi.deal.entity.FilterBean;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-11
 * @Time: 09:31
 */
public class BloomFilterDemo {


    public static void main(String[] args) {
//        fpp 是误判率
        int total = 1000000; // 总数量
        BloomFilter<CharSequence> bf =
                BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total,0.00001);
        // 初始化 1000000 条数据到过滤器中
//        for (int i = 0; i < total; i++) {
//            bf.put("" + i);
//        }
//        // 判断值是否存在过滤器中
//        int count = 0;
//        for (int i = 0; i < total + 10000; i++) {
//            if (bf.mightContain("" + i)) {
//                count++;
//            }
//        }
//        System.out.println("已匹配数量 " + count);
        for (int i = 0; i < 10000; i++) {

            FilterBean bean = FilterBean.builder()
                    .traceId("tr" + i)
                    .spanId("sp" + String.valueOf(i > 2))
                    .build();
            bf.put(bean.toString());



        }
        FilterBean bean = FilterBean.builder()
                .traceId("tr" + 1)
                .spanId("sp" + String.valueOf(1 > 2))
                .build();
        boolean tr = bf.mightContain(bean.toString());
        System.out.println(tr);
    }


}
