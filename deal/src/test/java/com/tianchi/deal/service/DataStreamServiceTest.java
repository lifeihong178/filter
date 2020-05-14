package com.tianchi.deal.service;

import com.tianchi.deal.DealApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-14
 * @Time: 09:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DealApplication.class)
public class DataStreamServiceTest {

    @Autowired
    private DataStreamService dataStreamService;

    @Test
    public void readFile() throws Exception {
        dataStreamService.readFile();
    }
}
