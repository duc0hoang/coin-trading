package com.coin.trading.service;

import com.coin.trading.dto.HistoryTradingDto;
import com.coin.trading.dto.WalletDto;

import java.util.List;

public interface UserService {
    WalletDto getCurrentWallet(String username);

    List<HistoryTradingDto> getHistoryTrading(String username);
}
