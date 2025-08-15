package com.coin.trading.dto;

import com.coin.trading.constant.Source;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HistoryPriceDto {
    private BigDecimal bidEthusdt;
    private Source sourceBidEthusdt;
    private BigDecimal askEthusdt;
    private Source sourceAskEthusdt;
    private BigDecimal bidBtcusdt;
    private Source sourceBidBtcusdt;
    private BigDecimal askBtcusdt;
    private Source sourceAskBtcusdt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
