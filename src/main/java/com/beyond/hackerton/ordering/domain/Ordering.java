package com.beyond.hackerton.ordering.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stock_name;
    private String stock_quantity;
    @Enumerated(EnumType.STRING)
    private ImmediateOrder yn;
    private String stock_price;
}
