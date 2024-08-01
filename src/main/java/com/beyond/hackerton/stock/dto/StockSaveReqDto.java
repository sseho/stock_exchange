package com.beyond.hackerton.stock.dto;

import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.stock.domain.Stock;
import com.beyond.hackerton.stock.domain.StockDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockSaveReqDto {
    private String name;
    private Long stock_quantity;
    private Long stock_price;
    private MultipartFile image;

    public Stock toEntity(){
        return Stock.builder()
                .name(this.name)
                .stockDetails(new ArrayList<>()).build();
    }
}
