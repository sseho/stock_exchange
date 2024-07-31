package com.beyond.hackerton.stock.service;

import com.beyond.hackerton.stock.domain.Stock;
import com.beyond.hackerton.stock.dto.StockListResDto;
import com.beyond.hackerton.stock.dto.StockSaveReqDto;
import com.beyond.hackerton.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    public Stock createStock(StockSaveReqDto dto) {
        return stockRepository.save(dto.toEntity());
    }

    public List<StockListResDto> stockList() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockListResDto> stockListResDtos = new ArrayList<>();
        for (Stock stock : stocks) {
            stockListResDtos.add(stock.fromEntity());
        }
        return stockListResDtos;
    }
}
