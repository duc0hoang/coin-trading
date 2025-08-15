package com.coin.trading.service;

import com.coin.trading.dto.HistoryPriceDto;

import java.util.List;

public interface HistoryPriceService {
    List<HistoryPriceDto> getAll();
    void save(HistoryPriceDto dto);
}
