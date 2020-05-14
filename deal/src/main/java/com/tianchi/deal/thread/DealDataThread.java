package com.tianchi.deal.thread;

import com.tianchi.deal.service.BloomFilterService;

import java.util.List;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-14
 * @Time: 09:39
 */
public class DealDataThread implements Runnable {

    private BloomFilterService bloomFilterService;
    private List<String> filterStrList;

    public DealDataThread(BloomFilterService bloomFilterService, List<String> filterStrList) {

        this.bloomFilterService = bloomFilterService;
        this.filterStrList = filterStrList;
    }

    @Override
    public void run() {
        for (String filterStr : filterStrList) {
            bloomFilterService.dealStreamDate(filterStr);
        }
    }
}
