package com.beyond.hackerton.stock.domain;

import com.beyond.hackerton.stock.dto.StockListResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long stock_quantity;
    private Long stock_price;

    public StockListResDto fromEntity() {
        return StockListResDto.builder()
                .name(this.name)
                .now_price(this.stock_price).build();
    }
}
