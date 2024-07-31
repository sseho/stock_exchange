package com.beyond.hackerton.ordering.domain;

import com.beyond.hackerton.common.domain.BaseTimeEntity;
import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.stock.domain.Stock;
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
public class Ordering extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stock_quantity;
    private String stock_price;

    @Enumerated(EnumType.STRING)
    private ImmediateOrder yn;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ExecutionYN yn_execution = ExecutionYN.N;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
