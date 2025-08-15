package com.coin.trading.service.impl;

import com.coin.trading.constant.Crypto;
import com.coin.trading.constant.Source;
import com.coin.trading.dto.HistoryPriceDto;
import com.coin.trading.dto.PriceDto;
import com.coin.trading.service.PriceService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class PriceServiceImpl implements PriceService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ExecutorService executorService;
    final String BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";
    final String HUOBI_URL = "https://api.huobi.pro/market/tickers";

    private Map<Crypto, PriceDto> getPrice(Source source) {
        switch (source) {
            case HOUBI -> {
                return getHoubiPrice();
            }
            case BINANCE -> {
                return getBinancePrice();
            }
            default -> {
                return Map.of();
            }
        }
    }

    private Map<Crypto, PriceDto> getBinancePrice() {
        JsonNode body = restTemplate.getForObject(BINANCE_URL, JsonNode.class);
        Map<Crypto, PriceDto> result = new HashMap<>();
        assert body != null;
        for (JsonNode jn : body) {
            String crypto = jn.get("symbol").asText();
            if (EnumUtils.isValidEnumIgnoreCase(Crypto.class, crypto)) {
                PriceDto dto = PriceDto.builder()
                        .source(Source.BINANCE)
                        .bidPrice(new BigDecimal(jn.get("bidPrice").asText()).setScale(8, RoundingMode.HALF_UP))
                        .askPrice(new BigDecimal(jn.get("askPrice").asText()).setScale(8, RoundingMode.HALF_UP))
                        .build();
                result.put(EnumUtils.getEnumIgnoreCase(Crypto.class, crypto), dto);
            }
        }
        return result;
    }

    private Map<Crypto, PriceDto> getHoubiPrice() {
        JsonNode body = restTemplate.getForObject(HUOBI_URL, JsonNode.class);
        Map<Crypto, PriceDto> result = new HashMap<>();
        assert body != null;
        for (JsonNode jn : body.get("data")) {
            String crypto = jn.get("symbol").asText();
            if (EnumUtils.isValidEnumIgnoreCase(Crypto.class, crypto)) {
                PriceDto dto = PriceDto.builder()
                        .source(Source.HOUBI)
                        .bidPrice(new BigDecimal(jn.get("bid").asText()).setScale(8, RoundingMode.HALF_UP))
                        .askPrice(new BigDecimal(jn.get("ask").asText()).setScale(8, RoundingMode.HALF_UP))
                        .build();
                result.put(EnumUtils.getEnumIgnoreCase(Crypto.class, crypto), dto);
            }
        }
        return result;
    }


    @Override
    @SneakyThrows
    public HistoryPriceDto getBestPricing() {
        List<Callable<Map<Crypto, PriceDto>>> tasks = new ArrayList<>();
        for (Source s : Source.values()) {
            tasks.add(() -> getPrice(s));
        }
        List<Future<Map<Crypto, PriceDto>>> futures = executorService.invokeAll(tasks);
        HistoryPriceDto historyPriceDto = new HistoryPriceDto();
        historyPriceDto.setTime(LocalDateTime.now(ZoneOffset.UTC));
        for (Crypto c : Crypto.values()) {
            List<PriceDto> priceDtos = new ArrayList<>();
            for (Future<Map<Crypto, PriceDto>> f : futures) {
                priceDtos.add(f.get().get(c));
            }

            PriceDto bestBid = priceDtos.stream()
                    .max(Comparator.comparing(PriceDto::getBidPrice))
                    .orElseThrow();
            PriceDto bestAsk = priceDtos.stream()
                    .min(Comparator.comparing(PriceDto::getAskPrice))
                    .orElseThrow();
            updateHistoryPriceDto(historyPriceDto,
                    bestBid, bestAsk, c);
        }
        return historyPriceDto;
    }

    private void updateHistoryPriceDto(HistoryPriceDto dto,
                                       PriceDto bestBid,
                                       PriceDto bestAsk,
                                       Crypto crypto) {
        switch (crypto) {
            case BTCUSDT -> {
                dto.setAskBtcusdt(bestAsk.getAskPrice());
                dto.setSourceAskBtcusdt(bestAsk.getSource());
                dto.setBidBtcusdt(bestBid.getBidPrice());
                dto.setSourceBidBtcusdt(bestBid.getSource());
            }
            case ETHUSDT -> {
                dto.setAskEthusdt(bestAsk.getAskPrice());
                dto.setSourceAskEthusdt(bestAsk.getSource());
                dto.setBidEthusdt(bestBid.getBidPrice());
                dto.setSourceBidEthusdt(bestBid.getSource());
            }
        }
    }
}
