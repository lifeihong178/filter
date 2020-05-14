package com.tianchi.deal.common;

import com.tianchi.deal.common.cons.KeyConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @Author: zhouheng
 * @Created: with IntelliJ IDEA.
 * @Description:
 * @Date: 2020-05-14
 * @Time: 13:45
 */
@Slf4j
@Component
public class RedisInitializing implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {

        redisTemplate.opsForSet().getOperations().delete(KeyConst.SET_KEY);

    }
}
