package com.beyond.hackerton.ordering.dto;

import com.beyond.hackerton.ordering.domain.ImmediateOrder;
import com.beyond.hackerton.ordering.domain.Ordering;
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
    private String stock_name;
    private String stock_quantity;
    private ImmediateOrder yn;
    private String stock_price;

    public Ordering toEntity(){
        return Ordering.builder()
                .stock_name(this.stock_name)
                .stock_quantity(this.stock_quantity)
                .yn(this.yn)
                .stock_price(this.stock_price).build();
    }
}
