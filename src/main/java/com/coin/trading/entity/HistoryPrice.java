package com.coin.trading.entity;

import com.coin.trading.constant.Source;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_price")
@Getter
@Setter
@NoArgsConstructor
public class HistoryPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bid_ethusdt", precision = 20, scale = 8)
    private BigDecimal bidEthusdt;

    @Column(name = "source_bid_ethusdt")
    @Enumerated(EnumType.STRING)
    private Source sourceBidEthusdt;

    @Column(name = "ask_ethusdt", precision = 20, scale = 8)
    private BigDecimal askEthusdt;

    @Column(name = "source_ask_ethusdt")
    @Enumerated(EnumType.STRING)
    private Source sourceAskEthusdt;

    @Column(name = "bid_btcusdt", precision = 20, scale = 8)
    private BigDecimal bidBtcusdt;

    @Column(name = "source_bid_btcusdt")
    @Enumerated(EnumType.STRING)
    private Source sourceBidBtcusdt;

    @Column(name = "ask_btcusdt", precision = 20, scale = 8)
    private BigDecimal askBtcusdt;

    @Column(name = "source_ask_btcusdt")
    @Enumerated(EnumType.STRING)
    private Source sourceAskBtcusdt;

    private LocalDateTime time;
}
