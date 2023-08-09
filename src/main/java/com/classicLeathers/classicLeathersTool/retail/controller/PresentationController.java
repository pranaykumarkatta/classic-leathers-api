package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.Presentation;
import com.classicLeathers.classicLeathersTool.retail.model.TotalSalesDto;
import com.classicLeathers.classicLeathersTool.retail.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/presentation")
@CrossOrigin(origins = "*")
public class PresentationController {

    @Autowired
    private PresentationService presentationService;

    @GetMapping
    public ResponseEntity<List<TotalSalesDto>> getData(@RequestParam Boolean showDriving,@RequestParam Boolean showKora,
                                                       @RequestParam Boolean showWaves,@RequestParam Boolean ShowASeries,
                                                       @RequestParam Boolean showMirat,@RequestParam Boolean showHandBag,
                                                       @RequestParam Boolean showBeltAndWallet,@RequestParam Boolean showNA) {
        return ResponseEntity.ok(presentationService.getData(showDriving, showKora, showWaves,
                ShowASeries, showMirat, showHandBag, showBeltAndWallet, showNA));
    }
}
