package com.filot.filotshop.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@RedisHash(value = "code", timeToLive = 30)
@ToString
public class VerifyCode {


    @Id
    private String id;

    private String email;
    private String code;
    private LocalDateTime createAt;


    public VerifyCode(String email, String code) {
        this.email = email;
        this.code = code;
        this.createAt = LocalDateTime.now();
    }
}
