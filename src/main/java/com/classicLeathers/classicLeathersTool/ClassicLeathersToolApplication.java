package com.classicLeathers.classicLeathersTool;

import com.classicLeathers.classicLeathersTool.retail.model.RetailCustomerSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import com.classicLeathers.classicLeathersTool.retail.model.Sku;
import com.classicLeathers.classicLeathersTool.retail.service.RetailSalesReportService;
import com.classicLeathers.classicLeathersTool.retail.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class ClassicLeathersToolApplication {
    public static final Map<String, String> availableStoreMap = new HashMap<>();
    public static final Map<String, String> availableBrandMap = new HashMap<>();
    public static final Map<String, Sku> availableSkuMap = new HashMap<>();
    public static final List<RetailSalesEntryDto> retailSalesEntryDtoList = new ArrayList<>();
    @Autowired
    private StockService stockService;
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public static void main(String[] args) {
        SpringApplication.run(ClassicLeathersToolApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        availableStoreMap.putAll(stockService.getAvailableStores());
        availableBrandMap.putAll(stockService.getAvailableBrands());
        availableSkuMap.putAll(stockService.getAvailableSkus());

        for (int i = 1; i < 13; i++) {
            retailSalesEntryDtoList.addAll(retailSalesReportService.getSalesDataByMonth2023(i - 1));
        }
        for (int i = 1; i < 13; i++) {
            if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))-1))
                retailSalesEntryDtoList.addAll(retailSalesReportService.getSalesDataByMonth(i - 1));
        }
        System.out.println("Data Loaded Successfully");
    }

}
