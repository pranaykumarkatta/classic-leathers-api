package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.DrivingShoeStockEntry;
import com.classicLeathers.classicLeathersTool.retail.service.RetailStockReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
