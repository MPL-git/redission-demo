package com.example.redissondemo.utils;

import com.example.redissondemo.lock.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @Description redis分布式锁工具类
 * @Auther pengl
 * @Version: 1.0
 * @Date 2021/3/30 16:17
 **/
public class RedissLockUtil {

    private static DistributedLock redissLock;

    public static void setLock(DistributedLock lock) {
        redissLock = lock;
    }

    /**
     * 获取 RedissonClient
     * @return
     */
    public static RedissonClient getRedissonClient() {
        return redissLock.getRedissonClient();
    }

    /**
     * 加锁
     * @param lockKey
     * @return
     */
    public static RLock lock(String lockKey) {
        return redissLock.lock(lockKey);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unlock(RLock lock) {
        redissLock.unlock(lock);
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static RLock lock(String lockKey, int timeout) {
        return redissLock.lock(lockKey, timeout);
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    public static RLock lock(String lockKey, TimeUnit unit , int timeout) {
        return redissLock.lock(lockKey, unit, timeout);
    }

    /**
     * 尝试获取锁
     * @param lockKey
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }

    /**
     * 尝试获取锁
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }

}
