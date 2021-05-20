package com.example.redissondemo.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.redissondemo.utils.RedissLockUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 联锁（MultiLock）
 * @Auther pengl
 * @Version: 1.0
 * @Date 2021/4/23 15:00
 **/
@Slf4j
@Api(value = "联锁（MultiLock）", tags = "联锁（MultiLock）")
@ApiModel
@RestController
@RequestMapping("/redissonMultiLocks")
public class RedissonMultiLockController {
    
    int a = 10;

    @ApiOperation(value = "aa")
    @PostMapping(value = "/aa")
    public void aa() {
        a = 10;
    }
    
    @ApiOperation(value = "并发测试")
    @PostMapping(value = "/test")
    public void test() {
        try {
            Thread.sleep(1*1000);
            if(a > 0) {
                log.info("a=================>{}", --a);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "并发测试联锁（MultiLock）全部加锁")
    @PostMapping(value = "/testMultiLockAll")
    public void testMultiLockAll() {
        RedissonMultiLock lock = null;
        try {

            String name = Thread.currentThread().getName();
            log.info("start==========ThreadName:{}=============================>{}",
                    name, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

            RLock rLock1 = RedissLockUtil.getRedissonClient().getLock("lock1");
            RLock rLock2 = RedissLockUtil.getRedissonClient().getLock("lock2");
            RLock rLock3 = RedissLockUtil.getRedissonClient().getLock("lock3");
            lock = new RedissonMultiLock(rLock1, rLock2, rLock3);
            lock.lock();

            Thread.sleep(10*1000);
            log.info("end==========ThreadName:{}===rLock1:{}===rLock2:{}===rLock3:{}===============>{}",
                    name, rLock1.getName(), rLock2==null?"null":rLock2.getName(),
                    rLock3==null?"null":rLock3.getName(), DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @ApiOperation(value = "并发测试联锁（MultiLock）")
    @PostMapping(value = "/testMultiLock")
    public void testMultiLock() {
        RedissonMultiLock lock = null;
        try {
            String name = Thread.currentThread().getName();
            log.info("@start======ThreadName:{}===============================>{}",
                    name, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

            int randomInt = RandomUtil.randomInt(1, 3);
            RLock[] rLocks = new RLock[randomInt];
            for (int i = 1; i <= randomInt; i++) {
                rLocks[i-1] = RedissLockUtil.getRedissonClient().getLock("lock"+i);
            }

            RedissonRedLock redissonRedLock = new RedissonRedLock(rLocks);
            redissonRedLock.lock();

            lock = new RedissonMultiLock(rLocks);
            lock.lock();

            Thread.sleep(1*1000);
            if(a > 0) {
                --a;
            }
            log.info("@end===randomInt:{}===ThreadName:{}========a:{}========>{}",
                    randomInt, name, a, DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }



}
