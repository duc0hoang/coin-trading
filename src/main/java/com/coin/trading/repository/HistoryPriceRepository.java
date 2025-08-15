package com.coin.trading.repository;

import com.coin.trading.entity.HistoryPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryPriceRepository extends JpaRepository<HistoryPrice, Long> {
}
