package com.example.redissondemo.config;

import com.example.redissondemo.lock.DistributedLock;
import com.example.redissondemo.lock.impl.RedissonDistributedLock;
import com.example.redissondemo.utils.RedissLockUtil;
import com.example.redissondemo.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Description Redission配置类
 * @Auther pengl
 * @Date 2021/3/30 15:28
 **/
@Slf4j
@Configuration
public class RedissonConfig {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Bean
    RedissonClient redissonSingle() throws IOException {
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config-"+springContextUtil.getActiveProfile()+".yml"));
        return Redisson.create(config);
    }

    /**
     * 装配lock类，并将实例注入到 RedissonDistributedLock中
     * @return
     */
    @Bean
    DistributedLock distributedLock(@Qualifier("redissonSingle") RedissonClient redissonClient) {
        RedissonDistributedLock lock = new RedissonDistributedLock();
        lock.setRedissonClient(redissonClient);
        RedissLockUtil.setLock(lock);
        return lock;
    }

}
