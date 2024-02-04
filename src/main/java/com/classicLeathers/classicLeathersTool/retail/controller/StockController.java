package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.ClassicLeathersToolApplication;
import com.classicLeathers.classicLeathersTool.retail.model.StockEntry;
import com.classicLeathers.classicLeathersTool.retail.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/stores")
    private ResponseEntity<Collection<String>> getAvailableStores() {
        return ResponseEntity.ok(ClassicLeathersToolApplication.availableStoreMap.values());
    }

    @GetMapping("/brands")
    private ResponseEntity<Collection<String>> getAvailableBrands() {
        return ResponseEntity.ok(ClassicLeathersToolApplication.availableBrandMap.values());
    }

    @GetMapping("/skus")
    private ResponseEntity<Collection<String>> getAvailableSkus() {
        return ResponseEntity.ok(ClassicLeathersToolApplication.availableSkuMap.values().parallelStream()
                .map(o -> o.getSku())
                .collect(Collectors.toList()));
    }

    @GetMapping("/availabilityMap")
    private ResponseEntity<Map<String, Set<String>>> getAvailabilityMap() {
        return ResponseEntity.ok(stockService.getAvailabilityMap());
    }

    @PostMapping(consumes = "application/json")
    public void addStockEntry(@RequestBody List<StockEntry> stockDTOList,@RequestParam Boolean isAudit) throws ParseException {
        stockService.addStockEntry(stockDTOList,isAudit);
    }

}
