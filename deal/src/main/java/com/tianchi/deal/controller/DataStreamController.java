package com.tianchi.deal.controller;

import com.tianchi.deal.common.util.OkHttpClientUtils;
import com.tianchi.deal.service.BloomFilterService;
import com.tianchi.deal.service.DataStreamService;
import okhttp3.Response;
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

    @Autowired
    private DataStreamService dataStreamService;

    @RequestMapping("ready")
    public String dealData() {
        return "HttpStatus.OK";
    }


    /**
     * 流的方式获取数据
     *
     * @param dataport
     * @throws Exception
     */
    @RequestMapping("/setParamter")
    public void getParam(String dataport) throws Exception {
        Response response = OkHttpClientUtils.get("localhost:" + dataport + "/trace1.data");
        dataStreamService.readStream(response.body().byteStream());
    }
}
