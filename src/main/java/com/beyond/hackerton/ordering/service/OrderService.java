package com.beyond.hackerton.ordering.service;

import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.member.repository.MemberRepository;
import com.beyond.hackerton.ordering.domain.Ordering;
import com.beyond.hackerton.ordering.dto.OrderReqDto;
import com.beyond.hackerton.ordering.repository.OrderRepository;
import com.beyond.hackerton.stock.domain.Stock;
import com.beyond.hackerton.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;

    public Ordering buyStock(OrderReqDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("Member not found"));
        Stock stock = stockRepository.findById(dto.getStock_id()).orElseThrow(()->new EntityNotFoundException("Stock not found"));
        Ordering ordering = dto.toEntity(member,stock);
        return null;
    }
}
