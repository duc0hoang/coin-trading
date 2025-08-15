package com.coin.trading.entity;

import com.coin.trading.constant.Action;
import com.coin.trading.constant.Crypto;
import com.coin.trading.constant.Source;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_trading")
@Getter
@Setter
@NoArgsConstructor
public class HistoryTrading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Enumerated(EnumType.STRING)
    private Action action;
    @Column(precision = 20, scale = 8)
    private BigDecimal quantity;
    @Column(precision = 20, scale = 8)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Source source;
    @Enumerated(EnumType.STRING)
    private Crypto crypto;
    private LocalDateTime time;
}
