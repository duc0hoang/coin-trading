package com.coin.trading.controller;

import com.coin.trading.dto.HistoryPriceDto;
import com.coin.trading.dto.request.TradingRequest;
import com.coin.trading.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trading")
public class TradingController {
    @Autowired
    private TradingService tradingService;

    @PostMapping("/buy")
    public ResponseEntity<Void> buy(@RequestBody TradingRequest tradingRequest){
        tradingService.buy(tradingRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sell")
    public ResponseEntity<HistoryPriceDto> sell(@RequestBody TradingRequest tradingRequest) {
        tradingService.sell(tradingRequest);
        return ResponseEntity.ok().build();
    }
}
