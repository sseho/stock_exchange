package com.beyond.hackerton.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockListResDto {
    private Long stock_id;
    private String name;
    private String image;
//    private Long now_price;
    private List<StockDetailResDto> stock_detail;
}
