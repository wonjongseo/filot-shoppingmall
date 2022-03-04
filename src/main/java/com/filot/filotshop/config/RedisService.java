package com.filot.filotshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class RedisService {
    @Value("{spring.redis.host}")
    private String url;

    @Bean
    public JedisPool jedisPool() throws URISyntaxException {

        System.out.println("url = " + url);
        URI redisUri = new URI(url);
        JedisPool pool = new JedisPool(new JedisPoolConfig(),
                redisUri.getHost(),
                redisUri.getPort(),
                Protocol.DEFAULT_TIMEOUT,
                redisUri.getUserInfo().split(":",2)[1]);

        return pool;
    }
}
