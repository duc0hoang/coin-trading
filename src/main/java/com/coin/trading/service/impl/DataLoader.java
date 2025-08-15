package com.coin.trading.service.impl;

import com.coin.trading.entity.User;
import com.coin.trading.entity.Wallet;
import com.coin.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        List<User> users = new ArrayList<>();
        User userA = new User();
        userA.setUsername("userA");
        Wallet walletA = new Wallet();
        walletA.setUsdt(BigDecimal.valueOf(50000).setScale(8, RoundingMode.HALF_UP));
        walletA.setEthusdt(BigDecimal.ZERO);
        walletA.setBtcusdt(BigDecimal.ZERO);
        userA.setWallet(walletA);
        users.add(userA);
        User userB = new User();
        userB.setUsername("userB");
        Wallet walletB = new Wallet();
        walletB.setUsdt(BigDecimal.valueOf(50000).setScale(8, RoundingMode.HALF_UP));
        walletB.setEthusdt(BigDecimal.ZERO);
        walletB.setBtcusdt(BigDecimal.ZERO);
        userB.setWallet(walletB);
        users.add(userB);
        User userC = new User();
        userC.setUsername("userC");
        Wallet walletC = new Wallet();
        walletC.setUsdt(BigDecimal.valueOf(50000));
        walletC.setEthusdt(BigDecimal.ZERO);
        walletC.setBtcusdt(BigDecimal.ZERO);
        userC.setWallet(walletC);
        users.add(userC);
        userRepository.saveAll(users);
    }
}
