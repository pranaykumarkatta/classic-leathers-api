package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.*;
import com.classicLeathers.classicLeathersTool.retail.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/presentation")
@CrossOrigin(origins = "*")
public class PresentationController {

    @Autowired
    private PresentationService presentationService;

    @GetMapping("/totalSales")
    public ResponseEntity<List<TotalSalesDto>> getTotalSalesData(@RequestParam Boolean showDriving, @RequestParam Boolean showMirat,
                                                                 @RequestParam Boolean showFormal, @RequestParam Boolean showCasual,
                                                                 @RequestParam Boolean showSlippers, @RequestParam Boolean showHandBag,
                                                                 @RequestParam Boolean showBeltAndWallet, @RequestParam Boolean showOther,
                                                                 @RequestParam Boolean showNA) {
        return ResponseEntity.ok(presentationService.getTotalSalesData(showDriving, showMirat,showFormal,showCasual,
                showSlippers, showHandBag, showBeltAndWallet,showOther,showNA));
    }

    @GetMapping("/avgSales")
    public ResponseEntity<List<AverageSalesDto>> getAverageSalesData( ) {
        return ResponseEntity.ok(presentationService.getAverageSalesData());
    }

    @GetMapping("/hourlySales")
    public ResponseEntity<List<HourlySalesDto>> getHourlySalesData(@RequestParam Boolean showHour8Data, @RequestParam Boolean showHour9Data,
                                                                   @RequestParam Boolean showHour10Data, @RequestParam Boolean showHour11Data,
                                                                   @RequestParam Boolean showHour12Data, @RequestParam Boolean showHour13Data,
                                                                   @RequestParam Boolean showHour14Data, @RequestParam Boolean showHour15Data,
                                                                   @RequestParam Boolean showHour16Data, @RequestParam Boolean showHour17Data,
                                                                   @RequestParam Boolean showHour18Data, @RequestParam Boolean showHour19Data,
                                                                   @RequestParam Boolean showHour20Data, @RequestParam Boolean showHour21Data,
                                                                   @RequestParam Boolean showHour22Data, @RequestParam Boolean showHour00Data) {
        return ResponseEntity.ok(presentationService.getHourlySalesData(showHour8Data, showHour9Data, showHour10Data, showHour11Data,
                showHour12Data, showHour13Data, showHour14Data, showHour15Data, showHour16Data, showHour17Data, showHour18Data, showHour19Data,
                showHour20Data, showHour21Data, showHour22Data, showHour00Data));
    }

    @GetMapping("/stepIn")
    public ResponseEntity<List<HourlyStepInDto>> getHourlyStepInData(@RequestParam Boolean showHour8Data, @RequestParam Boolean showHour9Data,
                                                                     @RequestParam Boolean showHour10Data, @RequestParam Boolean showHour11Data,
                                                                     @RequestParam Boolean showHour12Data, @RequestParam Boolean showHour13Data,
                                                                     @RequestParam Boolean showHour14Data, @RequestParam Boolean showHour15Data,
                                                                     @RequestParam Boolean showHour16Data, @RequestParam Boolean showHour17Data,
                                                                     @RequestParam Boolean showHour18Data, @RequestParam Boolean showHour19Data,
                                                                     @RequestParam Boolean showHour20Data, @RequestParam Boolean showHour21Data,
                                                                     @RequestParam Boolean showHour22Data, @RequestParam Boolean showHour00Data) {
        return ResponseEntity.ok(presentationService.getHourlyStepInData(showHour8Data, showHour9Data, showHour10Data, showHour11Data,
                showHour12Data, showHour13Data, showHour14Data, showHour15Data, showHour16Data, showHour17Data, showHour18Data, showHour19Data,
                showHour20Data, showHour21Data, showHour22Data, showHour00Data));
    }

    @GetMapping("/profitGraph")
    public ResponseEntity<List<ProfitDto>> getProfitData(@RequestParam Boolean showDriving, @RequestParam Boolean showMirat,
                                                         @RequestParam Boolean showFormal, @RequestParam Boolean showCasual,
                                                         @RequestParam Boolean showSlippers, @RequestParam Boolean showHandBag,
                                                         @RequestParam Boolean showBeltAndWallet, @RequestParam Boolean showOther,
                                                         @RequestParam Boolean showNA) {
        return ResponseEntity.ok(presentationService.getProfitData(showDriving, showMirat,showFormal,showCasual,
                showSlippers, showHandBag, showBeltAndWallet,showOther,showNA));
    }

    @GetMapping("/dailySales")
    public ResponseEntity<List<DailySalesDto>> getDailySales(@RequestParam Integer monthNumber, @RequestParam Integer year) {
        return ResponseEntity.ok(presentationService.getDailySales(year, monthNumber));
    }
}
