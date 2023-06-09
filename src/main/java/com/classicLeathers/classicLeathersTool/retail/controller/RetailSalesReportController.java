package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import com.classicLeathers.classicLeathersTool.retail.service.RetailSalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/retailSalesReport")
@CrossOrigin(origins = "*")
public class RetailSalesReportController {

    @Autowired
    private RetailSalesReportService retailSalesReportService;

    @GetMapping
    public ResponseEntity<List<RetailSalesEntryDto>> getSalesDataByMonth(@RequestParam Integer sheetNumber) {
        return ResponseEntity.ok(retailSalesReportService.getSalesDataByMonth(sheetNumber));
    }

    @PostMapping(consumes = "application/json")
    public void addRetailSalesEntry(@RequestBody RetailSalesEntryDto retailSalesEntryDto) throws ParseException {
        RetailSalesEntryDto newSalesEntryDto = new RetailSalesEntryDto();
        newSalesEntryDto.setSaleDate(new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()));
        newSalesEntryDto.setCustomerName(retailSalesEntryDto.getCustomerName());
        newSalesEntryDto.setGender(retailSalesEntryDto.getGender());
        newSalesEntryDto.setMobileNumber(retailSalesEntryDto.getMobileNumber());
        newSalesEntryDto.setCategory(retailSalesEntryDto.getCategory());
        newSalesEntryDto.setProductDetails(retailSalesEntryDto.getProductDetails());
        newSalesEntryDto.setQuantity(retailSalesEntryDto.getQuantity());
        newSalesEntryDto.setSize(retailSalesEntryDto.getSize());
        newSalesEntryDto.setMrp(retailSalesEntryDto.getMrp());
        newSalesEntryDto.setDiscount(retailSalesEntryDto.getDiscount());
        newSalesEntryDto.setSalePrice(retailSalesEntryDto.getSalePrice());
        newSalesEntryDto.setModeOfPayment(retailSalesEntryDto.getModeOfPayment());
        newSalesEntryDto.setCashPayment(retailSalesEntryDto.getCashPayment());
        newSalesEntryDto.setgPayPayment(retailSalesEntryDto.getgPayPayment());
        newSalesEntryDto.setSwipePayment(retailSalesEntryDto.getSwipePayment());
        newSalesEntryDto.setUpdatedBy(retailSalesEntryDto.getUpdatedBy());
        retailSalesReportService.addRetailSalesEntry(newSalesEntryDto);
    }

}
