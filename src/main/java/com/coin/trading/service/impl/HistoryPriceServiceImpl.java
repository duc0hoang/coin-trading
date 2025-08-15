package com.coin.trading.service.impl;

import com.coin.trading.dto.HistoryPriceDto;
import com.coin.trading.entity.HistoryPrice;
import com.coin.trading.repository.HistoryPriceRepository;
import com.coin.trading.service.HistoryPriceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HistoryPriceServiceImpl implements HistoryPriceService {
    @Autowired
    private HistoryPriceRepository historyPriceRepository;

    @Override
    public List<HistoryPriceDto> getAll() {
        return historyPriceRepository.findAll()
                .stream()
                .map(hp -> {
                    HistoryPriceDto dto = new HistoryPriceDto();
                    BeanUtils.copyProperties(hp, dto);
                    return dto;
                })
                .toList();
    }

    @Override
    public void save(HistoryPriceDto dto) {
        HistoryPrice historyPrice = new HistoryPrice();
        BeanUtils.copyProperties(dto, historyPrice);
        historyPriceRepository.save(historyPrice);
    }
}
