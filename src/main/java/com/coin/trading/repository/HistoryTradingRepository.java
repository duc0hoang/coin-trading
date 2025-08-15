package com.coin.trading.repository;

import com.coin.trading.entity.HistoryTrading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryTradingRepository extends JpaRepository<HistoryTrading, Long> {
    List<HistoryTrading> findByUsername(String username);
}
