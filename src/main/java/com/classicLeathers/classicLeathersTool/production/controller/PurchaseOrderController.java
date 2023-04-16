package com.classicLeathers.classicLeathersTool.production.controller;

import com.classicLeathers.classicLeathersTool.production.model.PurchaseOrder;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import com.classicLeathers.classicLeathersTool.production.service.PurchaseOrderService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchaseOrder")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/getArticles")
    public ResponseEntity<List<ArticleDto>> getArticles(@RequestParam Integer sheetIndex) {
        return ResponseEntity.ok(purchaseOrderService.getArticles(sheetIndex));
    }

    @GetMapping("/getPOFiles")
    public ResponseEntity<List<String>> getPOFiles() {
        return ResponseEntity.ok(purchaseOrderService.getPOFiles());
    }

    @PostMapping(path="/savePO",consumes = "application/json")
    public void savePurchaseOrder(@RequestBody List<PurchaseOrder> purchaseOrderList, @RequestParam String fileName,
                                  @RequestParam String customer,@RequestParam String brand,
                                  @RequestParam String poNumber,@RequestParam String jobWorkVendor,
                                  @RequestParam String poDate) {
       purchaseOrderService.savePurchaseOrder(purchaseOrderList, fileName, customer,brand,poNumber,jobWorkVendor, poDate.substring(0,15));
    }
}
