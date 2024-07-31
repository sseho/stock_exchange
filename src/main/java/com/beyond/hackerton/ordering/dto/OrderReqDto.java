package com.beyond.hackerton.ordering.dto;

import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.ordering.domain.ImmediateOrder;
import com.beyond.hackerton.ordering.domain.Ordering;
import com.beyond.hackerton.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderReqDto {
    private Long stock_id;
    private String stock_quantity;
    private ImmediateOrder yn;
    private String stock_price;

    public Ordering toEntity(Member member, Stock stock) {
        return Ordering.builder()
                .member(member)
                .stock(stock)
                .stock_quantity(this.stock_quantity)
                .stock_price(this.stock_price)
                .yn(this.yn).build();
    }
}
