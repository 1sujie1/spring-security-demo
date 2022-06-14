package com.test.springsecuritydemo.resourceuser.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class BaseConfig {

    @Value("${redis.security.host}")
    private String hostName;
    @Value("${redis.security.port}")
    private int port;
    @Value("${redis.security.password}")
    private String password;
    @Value("${redis.security.database}")
    private int index;

    //最小空闲连接数
    @Value("${redis.min-idle}")
    private int minIdle;
    //最大空闲连接数
    @Value("${redis.max-idle}")
    private int maxIdle;
    //最大连接数
    @Value("${redis.max-active}")
    private int maxTotal;
    //连接池最大阻塞等待时间
    @Value("${redis.max-wait}")
    private long maxWaitMillis;
    //连接超时时间(ms)
    private int timeout;

}
