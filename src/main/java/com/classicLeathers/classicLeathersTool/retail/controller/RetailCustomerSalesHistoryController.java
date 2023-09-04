package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.ProductSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailCustomerSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.service.RetailCustomerSalesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retailCustomerSales")
@CrossOrigin(origins = "*")
public class RetailCustomerSalesHistoryController {
    @Autowired
    private RetailCustomerSalesHistoryService retailCustomerSalesHistoryService;

    @GetMapping
    public ResponseEntity<List<RetailCustomerSalesHistoryDto>> getRetailCustomerSalesHistory(@RequestParam Long mobileNumber) {
        return ResponseEntity.ok(retailCustomerSalesHistoryService.getRetailCustomerSalesHistory(mobileNumber));
    }

    @GetMapping("/productSaleHistory")
    public List<ProductSalesHistoryDto> getProductSalesHistoryDto(@RequestParam String productSearchString,
                                                                  @RequestParam Integer monthNumber) {
        return retailCustomerSalesHistoryService.getProductSalesHistoryDto(productSearchString, monthNumber);
    }

}
