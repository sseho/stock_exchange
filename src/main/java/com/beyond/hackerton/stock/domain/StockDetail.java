package com.beyond.hackerton.stock.domain;

import com.beyond.hackerton.stock.dto.StockDetailResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long stock_price;
    private Long stock_quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public StockDetail(Long stockPrice, Long stockQuantity, Stock stock) {
        this.stock_price = stockPrice;
        this.stock_quantity = stockQuantity;
        this.stock = stock;
    }

    public StockDetailResDto fromEntity() {
        return StockDetailResDto.builder()
                .stock_price(this.stock_price)
                .stock_quantity(this.stock_quantity)
                .build();
    }
}
