package com.coin.trading.dto;

import com.coin.trading.constant.Action;
import com.coin.trading.constant.Crypto;
import com.coin.trading.constant.Source;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HistoryTradingDto {
    private String username;
    private Action action;
    private BigDecimal quantity;
    private BigDecimal price;
    private Source source;
    private Crypto crypto;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
