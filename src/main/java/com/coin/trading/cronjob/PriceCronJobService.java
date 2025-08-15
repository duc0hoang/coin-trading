package com.coin.trading.cronjob;

import com.coin.trading.service.HistoryPriceService;
import com.coin.trading.service.PriceService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceCronJobService {
    @Autowired
    private PriceService priceService;
    @Autowired
    private HistoryPriceService historyPriceService;
    @Scheduled(fixedRate = 10000)
    public void jobEvery10Seconds() {
        historyPriceService.save(priceService.getBestPricing());
    }
}
