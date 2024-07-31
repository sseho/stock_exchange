package com.beyond.hackerton.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailResDto {
    private Long stock_price;
    private Long stock_quantity;
}
