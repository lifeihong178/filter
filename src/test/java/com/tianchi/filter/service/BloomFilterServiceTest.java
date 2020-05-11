package com.tianchi.filter.service;

import com.tianchi.filter.FilterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-11
 * @Time: 14:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilterApplication.class)
//@ActiveProfiles("dev")
public class BloomFilterServiceTest {

    @Autowired
    private BloomFilterService service;

    @Test
    public void getBloomFilter() {

        boolean bloomFilter = service.getBloomFilter();
        System.out.println(bloomFilter);

    }
}

