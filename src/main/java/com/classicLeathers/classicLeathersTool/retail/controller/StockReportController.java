package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.StockAvailabilityDto;
import com.classicLeathers.classicLeathersTool.retail.model.StockReportDto;
import com.classicLeathers.classicLeathersTool.retail.service.RetailStockReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/retailStockReport")
@CrossOrigin(origins = "*")
public class StockReportController {

    @Autowired
    private RetailStockReportService retailStockReportService;

    @GetMapping(path = "/getStockReport")
    public ResponseEntity<Collection<StockAvailabilityDto>> getStockReport() {
        return ResponseEntity.ok(retailStockReportService.getStockReport());
    }
    @GetMapping(path = "/getAmburStockReport")
    public ResponseEntity<Collection<StockAvailabilityDto>> getAmburStockReport() {
        return ResponseEntity.ok(retailStockReportService.getAmburStockReport());
    }
    @GetMapping(path = "/getMelvisharamrStockReport")
    public ResponseEntity<Collection<StockAvailabilityDto>> getMelvisharamStockReport() {
        return ResponseEntity.ok(retailStockReportService.getMelvisharamStockReport());
    }

    @GetMapping(path = "/getStockAuditReport")
    public ResponseEntity<Collection<StockAvailabilityDto>> getStockAuditReport() {
        return ResponseEntity.ok(retailStockReportService.getStockAuditReport());
    }

    //
//    @GetMapping(path = "/availability")
//    public ResponseEntity<Map<String, Set<String>>> getStockAvailabilityData() {
//        return ResponseEntity.ok(retailStockReportService.getStockAvailabilityData());
//    }
//
    @GetMapping(path = "/exportStockReport")
    public ResponseEntity<String> exportStockReport() {
        return ResponseEntity.ok(retailStockReportService.exportStockReport());
    }

    @GetMapping(path = "/exportAuditReport")
    public ResponseEntity<String> exportAuditReport() {
        return ResponseEntity.ok(retailStockReportService.exportAuditReport());
    }
//
//    @PostMapping(path = "/saveStockEntry")
//    public void saveStockEntry(@RequestBody DrivingShoeStockEntry drivingShoeStockEntry) {
//        retailStockReportService.saveStockEntry(drivingShoeStockEntry);
//    }


}
