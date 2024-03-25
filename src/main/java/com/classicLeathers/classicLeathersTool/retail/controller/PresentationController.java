package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.DailySalesDto;
import com.classicLeathers.classicLeathersTool.retail.service.PresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presentation")
@CrossOrigin(origins = "*")
public class PresentationController {

    @Autowired
    private PresentationService presentationService;

    //
//    @GetMapping("/avgSales")
//    public ResponseEntity<List<AverageSalesDto>> getAverageSalesData( ) {
//        return ResponseEntity.ok(presentationService.getAverageSalesData());
//    }
//
//    @GetMapping("/hourlySales")
//    public ResponseEntity<List<HourlySalesDto>> getHourlySalesData(@RequestParam Boolean showHour8Data, @RequestParam Boolean showHour9Data,
//                                                                   @RequestParam Boolean showHour10Data, @RequestParam Boolean showHour11Data,
//                                                                   @RequestParam Boolean showHour12Data, @RequestParam Boolean showHour13Data,
//                                                                   @RequestParam Boolean showHour14Data, @RequestParam Boolean showHour15Data,
//                                                                   @RequestParam Boolean showHour16Data, @RequestParam Boolean showHour17Data,
//                                                                   @RequestParam Boolean showHour18Data, @RequestParam Boolean showHour19Data,
//                                                                   @RequestParam Boolean showHour20Data, @RequestParam Boolean showHour21Data,
//                                                                   @RequestParam Boolean showHour22Data, @RequestParam Boolean showHour00Data) {
//        return ResponseEntity.ok(presentationService.getHourlySalesData(showHour8Data, showHour9Data, showHour10Data, showHour11Data,
//                showHour12Data, showHour13Data, showHour14Data, showHour15Data, showHour16Data, showHour17Data, showHour18Data, showHour19Data,
//                showHour20Data, showHour21Data, showHour22Data, showHour00Data));
//    }
//
//    @GetMapping("/stepIn")
//    public ResponseEntity<List<HourlyStepInDto>> getHourlyStepInData(@RequestParam Boolean showHour8Data, @RequestParam Boolean showHour9Data,
//                                                                     @RequestParam Boolean showHour10Data, @RequestParam Boolean showHour11Data,
//                                                                     @RequestParam Boolean showHour12Data, @RequestParam Boolean showHour13Data,
//                                                                     @RequestParam Boolean showHour14Data, @RequestParam Boolean showHour15Data,
//                                                                     @RequestParam Boolean showHour16Data, @RequestParam Boolean showHour17Data,
//                                                                     @RequestParam Boolean showHour18Data, @RequestParam Boolean showHour19Data,
//                                                                     @RequestParam Boolean showHour20Data, @RequestParam Boolean showHour21Data,
//                                                                     @RequestParam Boolean showHour22Data, @RequestParam Boolean showHour00Data) {
//        return ResponseEntity.ok(presentationService.getHourlyStepInData(showHour8Data, showHour9Data, showHour10Data, showHour11Data,
//                showHour12Data, showHour13Data, showHour14Data, showHour15Data, showHour16Data, showHour17Data, showHour18Data, showHour19Data,
//                showHour20Data, showHour21Data, showHour22Data, showHour00Data));
//    }
//
    @GetMapping("/dailySales")
    public ResponseEntity<List<DailySalesDto>> getDailySales(@RequestParam Integer monthNumber, @RequestParam Integer year) {
        return ResponseEntity.ok(presentationService.getDailySales(year, monthNumber));
    }


    @GetMapping("/totalSales")
    public ResponseEntity<List<String>> getTotalSales(@RequestParam String filterString) {
        return ResponseEntity.ok(presentationService.getTotalSales(filterString));
    }

    @GetMapping("/profitGraph")
    public ResponseEntity<List<String>> getProfitData(@RequestParam String filterString) {
        return ResponseEntity.ok(presentationService.getProfitPercent(filterString));
    }
}
