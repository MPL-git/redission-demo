package com.example.redissondemo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.example.redissondemo.utils.RedissLockUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Auther pengl
 * @Version: 1.0
 * @Date 2021/3/30 18:34
 **/
@Slf4j
@Api(value = "redissons API", tags = "redissons API")
@ApiModel
@RestController
@RequestMapping("/redissons")
public class RedissonController {

    int a = 5;

    @ApiOperation(value = "并发测试")
    @PostMapping(value = "/test")
    public void test() {
        try {
            String name = Thread.currentThread().getName();
            log.info("start==============================>{}", name);
            Thread.sleep(5*1000);
            log.info("end==============================>{}", name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "并发测试 lock")
    @PostMapping(value = "/testLock")
    public void testLock() {
//        System.out.println("start==============================>"+ DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));
        RLock lock = RedissLockUtil.lock("lock-123");
//        System.out.println("lock==============================>"+ lock);
//        System.out.println("lock===name===========================>"+ lock.getName());
//        System.out.println("end==============================>"+ DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));
        try {

            if(a > 0) {
                a = a - 1;
            }
            log.info("a===============>{}", a);
            /*String name = Thread.currentThread().getName();
            log.info("start==============================>{}", name);
            Thread.sleep(5*1000);
            log.info("end==============================>{}", name);
        } catch (InterruptedException e) {
            e.printStackTrace();*/
        } finally {
            lock.unlock();
        }
    }

    @ApiOperation(value = "并发测试 lock timeout")
    @PostMapping(value = "/testLockTimeout")
    public void testLockTimeout() {
        RLock lock = RedissLockUtil.lock("testLockTimeout-123", 2);
        try {
            String name = Thread.currentThread().getName();
            log.info("start==============================>{}", name);
            Thread.sleep(5*1000);
            log.info("end==============================>{}", name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @ApiOperation(value = "并发测试 tryLock")
    @PostMapping(value = "/testTryLock")
    public void testTryLock() {
        boolean b = RedissLockUtil.tryLock("testLockTimeout-123", 15, 3);
        if(b) {
            try {
                String name = Thread.currentThread().getName();
                log.info("start==============================>{}", name);
                Thread.sleep(10*1000);
                log.info("end==============================>{}", name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                RedissLockUtil.unlock("testLockTimeout-123");
            }
        }
    }

}
