package com.coin.trading.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    private void test(){
        System.out.println("hello");
        String url = "https://api.binance.com/api/v3/ticker/bookTicker";
        JsonNode body = restTemplate.getForObject(url, JsonNode.class);
        List<JsonNode> datas = new ArrayList<>();
        for (JsonNode jn : body) {
            if (jn.get("symbol").asText().equalsIgnoreCase("ETHUSDT")
            || jn.get("symbol").asText().equalsIgnoreCase("BTCUSDT")) {
                datas.add(jn);
            }
        }
        System.out.println();
    }
}
