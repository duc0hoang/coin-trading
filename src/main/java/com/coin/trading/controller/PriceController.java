package com.coin.trading.controller;

import com.coin.trading.dto.HistoryPriceDto;
import com.coin.trading.service.HistoryPriceService;
import com.coin.trading.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price")
public class PriceController {
    @Autowired
    private HistoryPriceService historyPriceService;
    @Autowired
    private PriceService priceService;

    @GetMapping("/get-all-history")
    public ResponseEntity<List<HistoryPriceDto>> getAllHistory(){
        return ResponseEntity.ok(historyPriceService.getAll());
    }

    @GetMapping("/get-best-pricing")
    public ResponseEntity<HistoryPriceDto> getBestPricing(){
        return ResponseEntity.ok(priceService.getBestPricing());
    }

}
