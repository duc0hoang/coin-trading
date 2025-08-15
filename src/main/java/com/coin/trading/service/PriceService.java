package com.coin.trading.service;

import com.coin.trading.dto.HistoryPriceDto;

public interface PriceService {
    HistoryPriceDto getBestPricing();
}
