package com.coin.trading.dto.request;

import com.coin.trading.constant.Crypto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradingRequest {
    private String username;
    private Crypto crypto;
    private BigDecimal quantity;
}
