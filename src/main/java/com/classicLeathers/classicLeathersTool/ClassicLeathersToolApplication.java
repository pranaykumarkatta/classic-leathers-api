package com.classicLeathers.classicLeathersTool;

import com.classicLeathers.classicLeathersTool.retail.model.RetailCustomerSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import com.classicLeathers.classicLeathersTool.retail.model.Sku;
import com.classicLeathers.classicLeathersTool.retail.service.PresentationService;
import com.classicLeathers.classicLeathersTool.retail.service.RetailSalesReportService;
import com.classicLeathers.classicLeathersTool.retail.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
@EnableScheduling
public class ClassicLeathersToolApplication {
    public static final Map<String, String> availableStoreMap = new HashMap<>();
    public static final Map<String, String> availableBrandMap = new HashMap<>();
    public static final Map<String, Sku> availableSkuMap = new HashMap<>();
    public static final Map<String, String> categoryMap = new HashMap<>();
    public static final List<RetailSalesEntryDto> retailSalesEntryDtoList = new ArrayList<>();
    public static final Map<String, List<RetailSalesEntryDto>> salesData = new HashMap<>();
    @Autowired
    private StockService stockService;
    @Autowired
    private PresentationService presentationService;
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public static void main(String[] args) {
        SpringApplication.run(ClassicLeathersToolApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 12 1/1 * ?")
    public void doSomethingAfterStartup() {
        availableStoreMap.clear();
        availableBrandMap.clear();
        availableSkuMap.clear();
        categoryMap.clear();
        salesData.clear();
        availableStoreMap.putAll(stockService.getAvailableStores());
        availableBrandMap.putAll(stockService.getAvailableBrands());
        availableSkuMap.putAll(stockService.getAvailableSkus());
        categoryMap.putAll(stockService.getCategoryMap());
        salesData.putAll(presentationService.updateSalesData(2024,0));

        for (int i = 1; i < 13; i++) {
            retailSalesEntryDtoList.addAll(retailSalesReportService.getSalesDataByMonth2023(i - 1));
        }
        for (int i = 1; i < 13; i++) {
            if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date())))) - 1))
                retailSalesEntryDtoList.addAll(retailSalesReportService.getSalesDataByMonth(i - 1));
        }
        System.out.println("Data Loaded Successfully");
    }

}
