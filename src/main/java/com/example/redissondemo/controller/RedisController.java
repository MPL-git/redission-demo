package com.example.redissondemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description
 * @Auther pengl
 * @Version: 1.0
 * @Date 2021/4/21 14:41
 **/
public class RedisController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;



}
