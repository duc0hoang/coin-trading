package com.coin.trading.service.impl;

import com.coin.trading.constant.Action;
import com.coin.trading.constant.Source;
import com.coin.trading.dto.HistoryPriceDto;
import com.coin.trading.dto.HistoryTradingDto;
import com.coin.trading.dto.request.TradingRequest;
import com.coin.trading.entity.HistoryTrading;
import com.coin.trading.entity.User;
import com.coin.trading.entity.Wallet;
import com.coin.trading.repository.HistoryTradingRepository;
import com.coin.trading.repository.UserRepository;
import com.coin.trading.repository.WalletRepository;
import com.coin.trading.service.PriceService;
import com.coin.trading.service.TradingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TradingServiceImpl implements TradingService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private HistoryTradingRepository historyTradingRepository;
    @Autowired
    private PriceService priceService;

    @Override
    public void buy(TradingRequest tradingRequest) {
        User user = userRepository.findByUsername(tradingRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not exist!"));
        HistoryPriceDto priceDto = priceService.getBestPricing();
        BigDecimal price = BigDecimal.ZERO;
        Source source = null;
        switch (tradingRequest.getCrypto()) {
            case BTCUSDT -> {
                price = priceDto.getAskBtcusdt();
                source = priceDto.getSourceAskBtcusdt();
            }
            case ETHUSDT -> {
                price = priceDto.getAskEthusdt();
                source = priceDto.getSourceAskEthusdt();
            }
        }
        BigDecimal amount = tradingRequest.getQuantity().multiply(price);
        processBuying(tradingRequest, user.getWallet().getId(), amount);
        HistoryTradingDto historyTradingDto = HistoryTradingDto.builder()
                .username(tradingRequest.getUsername())
                .action(Action.BUY)
                .price(price)
                .quantity(tradingRequest.getQuantity())
                .source(source)
                .crypto(tradingRequest.getCrypto())
                .time(priceDto.getTime())
                .build();
        updateTradingHistory(historyTradingDto);
    }

    private void updateTradingHistory(HistoryTradingDto dto) {
        HistoryTrading historyTrading = new HistoryTrading();
        BeanUtils.copyProperties(dto, historyTrading);
        historyTradingRepository.save(historyTrading);
    }

    @Retryable(
            retryFor = OptimisticLockingFailureException.class,
            backoff = @Backoff(delay = 100)
    )
    private void processBuying(TradingRequest tradingRequest,
                               Long walletId,
                               BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        if (wallet.getUsdt().compareTo(amount) < 0) {
            throw new RuntimeException("Not enough money");
        }
        switch (tradingRequest.getCrypto()) {
            case ETHUSDT -> wallet.setEthusdt(wallet.getEthusdt().add(tradingRequest.getQuantity()));
            case BTCUSDT -> wallet.setBtcusdt(wallet.getBtcusdt().add(tradingRequest.getQuantity()));
        }
        wallet.setUsdt(wallet.getUsdt().subtract(amount));
        walletRepository.save(wallet);
    }

    @Override
    public void sell(TradingRequest tradingRequest) {
        User user = userRepository.findByUsername(tradingRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not exist!"));
        HistoryPriceDto priceDto = priceService.getBestPricing();
        BigDecimal price = BigDecimal.ZERO;
        Source source = null;
        switch (tradingRequest.getCrypto()) {
            case BTCUSDT -> {
                price = priceDto.getBidBtcusdt();
                source = priceDto.getSourceBidBtcusdt();
            }
            case ETHUSDT -> {
                price = priceDto.getBidEthusdt();
                source = priceDto.getSourceBidEthusdt();
            }
        }
        BigDecimal amount = tradingRequest.getQuantity().multiply(price);
        processSelling(tradingRequest, user.getWallet().getId(), amount);
        HistoryTradingDto historyTradingDto = HistoryTradingDto.builder()
                .username(tradingRequest.getUsername())
                .action(Action.SELL)
                .price(price)
                .quantity(tradingRequest.getQuantity())
                .source(source)
                .crypto(tradingRequest.getCrypto())
                .time(priceDto.getTime())
                .build();
        updateTradingHistory(historyTradingDto);
    }

    @Retryable(
            retryFor = OptimisticLockingFailureException.class,
            backoff = @Backoff(delay = 100)
    )
    private void processSelling(TradingRequest tradingRequest,
                                Long walletId,
                                BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow();
        switch (tradingRequest.getCrypto()) {
            case ETHUSDT -> {
                if (wallet.getEthusdt().compareTo(tradingRequest.getQuantity()) < 0) {
                    throw new RuntimeException("Not enough crypto");
                }
                wallet.setEthusdt(wallet.getEthusdt().subtract(tradingRequest.getQuantity()));
            }
            case BTCUSDT -> {
                if (wallet.getBtcusdt().compareTo(tradingRequest.getQuantity()) < 0) {
                    throw new RuntimeException("Not enough crypto");
                }
                wallet.setBtcusdt(wallet.getBtcusdt().subtract(tradingRequest.getQuantity()));
            }
        }
        wallet.setUsdt(wallet.getUsdt().add(amount));
        walletRepository.save(wallet);
    }
}
