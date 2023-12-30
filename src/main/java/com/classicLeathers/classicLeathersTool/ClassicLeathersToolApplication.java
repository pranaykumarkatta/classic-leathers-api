package com.classicLeathers.classicLeathersTool;

import com.classicLeathers.classicLeathersTool.retail.model.Sku;
import com.classicLeathers.classicLeathersTool.retail.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ClassicLeathersToolApplication {
    public static final Map<String, String> availableStoreMap = new HashMap<>();
    public static final Map<String, String> availableBrandMap = new HashMap<>();
    public static final Map<String, Sku> availableSkuMap = new HashMap<>();
    public static final Map<String, String> stockMap = new HashMap<>();
    @Autowired
    private StockService stockService;

    public static void main(String[] args) {
        SpringApplication.run(ClassicLeathersToolApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        availableStoreMap.putAll(stockService.getAvailableStores());
        availableBrandMap.putAll(stockService.getAvailableBrands());
        availableSkuMap.putAll(stockService.getAvailableSkus());
    }

}
