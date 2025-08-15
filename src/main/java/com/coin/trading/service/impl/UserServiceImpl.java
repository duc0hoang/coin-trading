package com.coin.trading.service.impl;

import com.coin.trading.dto.HistoryTradingDto;
import com.coin.trading.dto.WalletDto;
import com.coin.trading.entity.HistoryTrading;
import com.coin.trading.entity.User;
import com.coin.trading.repository.HistoryTradingRepository;
import com.coin.trading.repository.UserRepository;
import com.coin.trading.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HistoryTradingRepository historyTradingRepository;

    @Override
    public WalletDto getCurrentWallet(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not exist!"));
        WalletDto dto = new WalletDto();
        BeanUtils.copyProperties(user.getWallet(), dto);
        return dto;
    }

    @Override
    public List<HistoryTradingDto> getHistoryTrading(String username) {
        List<HistoryTrading> historyTradings = historyTradingRepository.findByUsername(username);
        return historyTradings.stream()
                .map(ht -> {
                    HistoryTradingDto dto = new HistoryTradingDto();
                    BeanUtils.copyProperties(ht, dto);
                    return dto;
                })
                .toList();
    }
}
