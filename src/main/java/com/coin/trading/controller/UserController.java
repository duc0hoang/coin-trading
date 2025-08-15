package com.coin.trading.controller;

import com.coin.trading.dto.HistoryTradingDto;
import com.coin.trading.dto.WalletDto;
import com.coin.trading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/current-wallet/{username}")
    public ResponseEntity<WalletDto> getCurrentWallet(@PathVariable String username){
        return ResponseEntity.ok(userService.getCurrentWallet(username));
    }

    @GetMapping("/history-trading/{username}")
    public ResponseEntity<List<HistoryTradingDto>> getHistoryTrading(@PathVariable String username){
        return ResponseEntity.ok(userService.getHistoryTrading(username));
    }
}
