package com.beyond.hackerton.common.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

//    application.yml의 spring.redis.host의 정보를 소스코드의 변수로 가져오는것
    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.port}")
    public int port;

//    refreshtoken service
    @Bean
    @Qualifier("refreshtoken")
//    RedisConnectionFactory는 Redis서버와의 연결을 설정하는 역할
//    LettuceConnectionFactory는 RedisConnectionFactory의 구현체로서 실질적인 역할 수행
    public RedisConnectionFactory redisConnectionFactory(){
//        return new LettuceConnectionFactory(host, port);
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
//        0번 db 사용
        configuration.setDatabase(0);
//        configuration.setPassword("1234");
        return new LettuceConnectionFactory(configuration);
    }

//  redisTamplate은 redis와 상호작용할 때 redis key,value의 형식을 정의
    @Bean
    @Qualifier("refreshtoken")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("refreshtoken") RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
//    redisTemplate.opsForValue().set(key,value)
//    redisTemplate.opsForValue().get(key)
//    redisTemplate.opsForValue().increment or decrement


    @Bean
    @Qualifier("stock")
    public RedisConnectionFactory stockFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
//        2번 db 사용
        configuration.setDatabase(1);
        return new LettuceConnectionFactory(configuration);
    }

    //  redisTamplate은 redis와 상호작용할 때 redis key,value의 형식을 정의
    @Bean
    @Qualifier("stock")
    public RedisTemplate<String, Object> stockRedisTemplate(@Qualifier("stock") RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory); // 매개변수로 받은 RedisConnectionFactory 객체 값을 넣어줘야 경로가 지정한대로 설정된다
        return redisTemplate;
    }
}
