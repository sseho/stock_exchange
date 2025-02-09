package com.beyond.hackerton.stock.controller;

import com.beyond.hackerton.common.dto.CommonResDto;
import com.beyond.hackerton.stock.domain.Stock;
import com.beyond.hackerton.stock.dto.StockListResDto;
import com.beyond.hackerton.stock.dto.StockSaveReqDto;
import com.beyond.hackerton.stock.dto.StockSellReqDto;
import com.beyond.hackerton.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("stock")
public class StockController {

    private final StockService stockService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<?> create(StockSaveReqDto dto) {
        Stock stock = stockService.createStockAws(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED,"stock created successful!",stock.getId());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @GetMapping("list")
    public ResponseEntity<?> list() {
        List<StockListResDto> stockList = stockService.stockList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,"stock list is successfully retrieved",stockList);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    @PostMapping("sell")
    public ResponseEntity<?> sell(@RequestBody StockSellReqDto dto) {
        Stock stock = stockService.sellStock(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED,"stock created successful!",stock.getId());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Map<Object,Object> map = stockService.stockDetail(id);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,"stock detail successful!",map);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }
}
