package com.classicLeathers.classicLeathersTool.production.controller;

import com.classicLeathers.classicLeathersTool.production.service.TeakWoodPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/twPurchaseOrder")
@CrossOrigin(origins = "*")
public class TeakWoodPurchaseOrderController {
    @Autowired
    private TeakWoodPurchaseOrderService teakWoodPurchaseOrderService;
}
