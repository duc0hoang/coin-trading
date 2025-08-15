package com.coin.trading.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 20, scale = 8)
    private BigDecimal usdt;

    @Column(precision = 20, scale = 8)
    private BigDecimal ethusdt;

    @Column(precision = 20, scale = 8)
    private BigDecimal btcusdt;

    @Version
    private Long version;

    @OneToOne(mappedBy = "wallet")
    private User user;
}
