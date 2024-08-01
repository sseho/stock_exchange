package com.beyond.hackerton.stock.service;

import com.beyond.hackerton.common.service.StockInventoryService;
import com.beyond.hackerton.member.domain.Member;
import com.beyond.hackerton.member.repository.MemberRepository;
import com.beyond.hackerton.stock.domain.Stock;
import com.beyond.hackerton.stock.domain.StockDetail;
import com.beyond.hackerton.stock.dto.StockListResDto;
import com.beyond.hackerton.stock.dto.StockSaveReqDto;
import com.beyond.hackerton.stock.dto.StockSellReqDto;
import com.beyond.hackerton.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final StockRepository stockRepository;
    private final S3Client s3Client;
    private final MemberRepository memberRepository;
    private final StockInventoryService stockInventoryService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Stock createStock(StockSaveReqDto dto) {
        return stockRepository.save(dto.toEntity());
    }

    public Stock createStockAws(StockSaveReqDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("Member not found"));
        MultipartFile image = dto.getImage();
        Stock stock = null;
        try {
            stock = stockRepository.save(dto.toEntity());
            stock.getStockDetails().add(new StockDetail(dto.getStock_price(), dto.getStock_quantity(), stock, member));
//            stock.getStockDetails().add(StockDetail.builder().stock_price(dto.getStock_price()).stock_quantity(dto.getStock_quantity()).stock(stock).build());
            byte[] bytes = image.getBytes();
            String fileName = stock.getId() + "_" + image.getOriginalFilename();
            Path path = Paths.get("C:/Users/PlayData/Desktop/tmp/", fileName);
//            local pc에 임시 저장
            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

//            aws에 pc에 저장된 파일을 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromFile(path));
            String s3Path = s3Client.utilities().getUrl(a->a.bucket(bucket).key(fileName)).toExternalForm();

            stock.updateImagePath(s3Path);
            stockInventoryService.addStock(stock.getId(),member.getId(),dto.getStock_price(), dto.getStock_quantity());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 저장 실패");
        }
        return stock;
    }

    public List<StockListResDto> stockList() {
        List<Stock> stocks = stockRepository.findAll();
        List<StockListResDto> stockListResDtos = new ArrayList<>();
        for (Stock stock : stocks) {
            stockListResDtos.add(stock.fromEntity());
        }
        return stockListResDtos;
    }

    public Stock sellStock(StockSellReqDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("Member not found"));
        Stock stock = stockRepository.findById(dto.getStock_id()).orElseThrow(()->new EntityNotFoundException("Stock not found"));
        stock.getStockDetails().add(new StockDetail(dto.getStock_price(), dto.getStock_quantity(), stock, member));
        stockInventoryService.addStock(stock.getId(),member.getId(),dto.getStock_price(), dto.getStock_quantity());
        return stock;
    }

    public Map<Object, Object> stockDetail(Long id){
        return stockInventoryService.getStockDetails(id);
    }
}
