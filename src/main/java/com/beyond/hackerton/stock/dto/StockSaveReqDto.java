package com.beyond.hackerton.stock.dto;

import com.beyond.hackerton.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockSaveReqDto {
    private String name;
    private Long stock_quantity;
    private Long stock_price;

    public Stock toEntity(){
        return Stock.builder()
                .name(this.name)
                .stock_quantity(this.stock_quantity)
                .stock_price(this.stock_price).build();
    }
}
