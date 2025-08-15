package com.coin.trading.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private BigDecimal usdt;
    private BigDecimal ethusdt;
    private BigDecimal btcusdt;
}
