package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.DrivingShoeStockEntry;
import com.classicLeathers.classicLeathersTool.retail.service.RetailStockReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/retailStockReport")
@CrossOrigin(origins = "*")
public class StockReportController {

    @Autowired
    private RetailStockReportService retailStockReportService;

    @GetMapping(path = "/driving")
    public ResponseEntity<List<DrivingShoeStockEntry>> getDrivingShoeStockReport() {
        return ResponseEntity.ok(retailStockReportService.getDrivingShoeStockReport());
    }

    @GetMapping(path = "/availability")
    public ResponseEntity<Map<String, Set<String>>> getStockAvailabilityData() {
        return ResponseEntity.ok(retailStockReportService.getStockAvailabilityData());
    }

    @GetMapping(path = "/exportStockReport")
    public ResponseEntity<String> exportStockReport() {
        return ResponseEntity.ok(retailStockReportService.exportStockReport());
    }

    @PostMapping(path = "/saveStockEntry")
    public void saveStockEntry(@RequestBody DrivingShoeStockEntry drivingShoeStockEntry) {
        retailStockReportService.saveStockEntry(drivingShoeStockEntry);
    }

}
