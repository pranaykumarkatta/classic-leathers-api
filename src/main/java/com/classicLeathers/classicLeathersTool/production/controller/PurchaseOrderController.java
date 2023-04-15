package com.classicLeathers.classicLeathersTool.production.controller;

import com.classicLeathers.classicLeathersTool.production.model.PurchaseOrder;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import com.classicLeathers.classicLeathersTool.production.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/twPurchaseOrder")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/getArticles")
    public ResponseEntity<List<ArticleDto>> getArticles() {
        return ResponseEntity.ok(purchaseOrderService.getArticles());
    }

    @PostMapping(path="/savePO",consumes = "application/json")
    public void savePurchaseOrder(@RequestBody List<PurchaseOrder> purchaseOrderList, @RequestParam String fileName) {
       purchaseOrderService.savePurchaseOrder(purchaseOrderList, fileName);
    }
}
