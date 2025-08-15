package com.coin.trading.service;

import com.coin.trading.dto.request.TradingRequest;

public interface TradingService {
    void buy(TradingRequest tradingRequest);
    void sell(TradingRequest tradingRequest);
}
