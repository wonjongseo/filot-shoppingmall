package com.filot.filotshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

//@Component
public class HerokuRedisService {

    private String url = "redis://default:1BZceU1M8tcmu5Xca9jFyiG4vGSYa3Zz@redis-18106.c81.us-east-1-2.ec2.cloud.redislabs.com:18106";

    @Bean
    public JedisPool jedisPool() throws URISyntaxException {
        URI redisUri = new URI(url);
        JedisPool pool = new JedisPool(new JedisPoolConfig(),
                redisUri.getHost(),
                redisUri.getPort(),
                Protocol.DEFAULT_TIMEOUT,
                redisUri.getUserInfo().split(":",2)[1]);

        return pool;
    }
}
