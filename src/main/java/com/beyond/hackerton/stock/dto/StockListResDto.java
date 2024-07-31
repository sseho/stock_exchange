package com.beyond.hackerton.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockListResDto {
    private String name;
    private Long now_price;
    private String image;
}
