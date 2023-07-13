package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.Customer;
import com.classicLeathers.classicLeathersTool.retail.service.RetailCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
public class RetailCustomerController {
    @Autowired
    private RetailCustomerService retailCustomerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(retailCustomerService.getCustomers());
    }
    @PostMapping(consumes = "application/json")
    public void getVendorList(@RequestBody Customer c) {
        Customer customer = new Customer(c.getName(), c.getGender(), c.getMobileNumber());
        retailCustomerService.addCustomer(customer);
    }
}
