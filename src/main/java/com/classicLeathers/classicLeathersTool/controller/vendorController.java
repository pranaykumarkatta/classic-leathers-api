package com.classicLeathers.classicLeathersTool.controller;

import com.classicLeathers.classicLeathersTool.model.Vendor;
import com.classicLeathers.classicLeathersTool.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendorController")
@CrossOrigin(origins = "*")
public class vendorController {
    @Autowired
    private VendorService vendorService;
    List<Vendor> vendors = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Vendor>> getVendorList() {
        return ResponseEntity.ok(vendorService.getVendors());
    }

    @PostMapping(consumes = "application/json")
    public void getVendorList(@RequestBody Vendor vendor) {
        Vendor newVendor = new Vendor();
        newVendor.setName(vendor.getName());
        newVendor.setPlace(vendor.getPlace());
        vendorService.addVendor(vendor);
    }

}
