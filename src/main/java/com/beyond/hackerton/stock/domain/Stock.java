package com.beyond.hackerton.stock.domain;

import com.beyond.hackerton.stock.dto.StockDetailResDto;
import com.beyond.hackerton.stock.dto.StockListResDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String image;
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockDetail> stockDetails;

    public StockListResDto fromEntity() {
        List<StockDetailResDto> stockDetailResDtos = new ArrayList<>();
        for (StockDetail stockDetail : this.stockDetails) {
            stockDetailResDtos.add(stockDetail.fromEntity());
        }
        return StockListResDto.builder()
                .name(this.name)
                .stock_detail(stockDetailResDtos)
                .image(this.image).build();
    }

    public void updateImagePath(String imagePath) {
        this.image = imagePath;
    }
}
