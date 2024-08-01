package com.beyond.hackerton.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockSellReqDto {
    private Long stock_id;
    private Long stock_quantity;
    private Long stock_price;
}
