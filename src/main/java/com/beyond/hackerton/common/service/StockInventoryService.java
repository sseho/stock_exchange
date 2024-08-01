package com.beyond.hackerton.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class StockInventoryService {

    @Qualifier("stock")
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public StockInventoryService(@Qualifier("stock") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addStock(Long stockId, Long sellerId, Long price, Long quantity) {
        // zset에 주식 추가
        redisTemplate.opsForZSet().add("stock:" + stockId, sellerId + ":" + quantity, price);

        // hash에 상세 정보 저장
        Map<String, Object> stockDetails = new HashMap<>();
        stockDetails.put("sellerId", sellerId);
        stockDetails.put("price", price);
        stockDetails.put("quantity", quantity);
        redisTemplate.opsForHash().putAll("stock:details:" + stockId, stockDetails);
    }

    public Set<Object> getStocksByPrice(Long stockId, Long minPrice, Long maxPrice) {
        // 주어진 가격 범위 내의 주식 리스트 가져오기
        return redisTemplate.opsForZSet().rangeByScore("stock:" + stockId, minPrice, maxPrice);
    }

    public Map<Object, Object> getStockDetails(Long stockId) {
        // 주식의 상세 정보 가져오기
        return redisTemplate.opsForHash().entries("stock:details:" + stockId);
    }
}
