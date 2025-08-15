package com.coin.trading.dto;

import com.coin.trading.constant.Source;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PriceDto {
    private Source source;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
