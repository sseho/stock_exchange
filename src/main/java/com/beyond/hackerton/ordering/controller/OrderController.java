package com.beyond.hackerton.ordering.controller;

import com.beyond.hackerton.ordering.domain.Ordering;
import com.beyond.hackerton.ordering.dto.OrderReqDto;
import com.beyond.hackerton.ordering.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("stock/buy")
    public void stockBuy(@RequestBody OrderReqDto dto) {
        Ordering ordering = orderService.buyStock(dto);
    }
}
