package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.ClassicLeathersToolApplication;
import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import com.classicLeathers.classicLeathersTool.retail.service.RetailSalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
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
    public void addRetailSalesEntry(@RequestBody List<RetailSalesEntryDto> retailSalesEntryDtoList, @RequestParam String invoiceNumber) throws ParseException {
        retailSalesEntryDtoList.forEach(retailSalesEntryDto -> {
            String[] strs = retailSalesEntryDto.getCategory().split("_");
            RetailSalesEntryDto newSalesEntryDto = new RetailSalesEntryDto();
            newSalesEntryDto.setSaleDate(new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()));
            newSalesEntryDto.setCustomerName(retailSalesEntryDto.getCustomerName());
            newSalesEntryDto.setGender(retailSalesEntryDto.getGender());
            newSalesEntryDto.setMobileNumber(retailSalesEntryDto.getMobileNumber());
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
            newSalesEntryDto.setStepInType(retailSalesEntryDto.getStepInType());
            newSalesEntryDto.setBrand(retailSalesEntryDto.getBrand());
            if (strs.length == 1) {
                newSalesEntryDto.setCategory(strs[0]);
                newSalesEntryDto.setLeather("NA");
            } if (strs.length == 2) {
                newSalesEntryDto.setCategory(strs[0]);
                newSalesEntryDto.setLeather(strs[1]);
            } if (strs.length == 3) {
                newSalesEntryDto.setCategory(strs[0]+"_"+strs[1]);
                newSalesEntryDto.setLeather(strs[2]);
            }
            retailSalesReportService.addRetailSalesEntry(newSalesEntryDto, invoiceNumber);
        });
        try {
            new FileUtils().saveInvoice(retailSalesEntryDtoList, invoiceNumber);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
