package com.tianchi.filter.common.controller;

import com.tianchi.filter.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-12
 * @Time: 18:12
 */
@RestController
@RequestMapping("/")
public class DataStreamController {

    @Autowired
    private BloomFilterService bloomFilterService;

    @RequestMapping("ready")
    public String dealData() {
        return "HttpStatus.OK";
    }
}
