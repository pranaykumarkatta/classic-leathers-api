package com.classicLeathers.classicLeathersTool.production.controller;

import com.classicLeathers.classicLeathersTool.production.InvalidCountException;
import com.classicLeathers.classicLeathersTool.production.model.*;
import com.classicLeathers.classicLeathersTool.production.service.JobCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobCard")
@CrossOrigin(origins = "*")
public class JobCardController {
    @Autowired
    private JobCardService jobCardService;

    @GetMapping("/getArticles")
    public ResponseEntity<List<ArticleDto>> getArticles(@RequestParam Integer sheetIndex) {
        return ResponseEntity.ok(jobCardService.getArticles(sheetIndex));
    }

    @GetMapping("/getJobCardFiles")
    public ResponseEntity<List<String>> getJobCardFiles() {
        return ResponseEntity.ok(jobCardService.getJobCardFiles());
    }

    @GetMapping("/getJobCardDetails")
    public ResponseEntity<List<JobCard>> getJobCardDetails(@RequestParam String fileName) {
        return ResponseEntity.ok(jobCardService.getJobCardDetails(fileName));
    }

    @GetMapping("/getNextJobCardNumber")
    public ResponseEntity<Integer> getNextJobCardNumber() {
        return ResponseEntity.ok(jobCardService.getNextJobCardNumber());
    }

    @GetMapping("/jobCardProgressSkus")
    public ResponseEntity<List<String>> getJobCardProgressSkus(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getJobCardProgressSkus(jobCardFileName));
    }

    @GetMapping("/jobCardProgressList")
    public ResponseEntity<List<JobCardProgress>> getJobCardProgressList(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getJobCardProgressList(jobCardFileName));
    }

    @GetMapping("/jobCardOverAllProgressList")
    public ResponseEntity<List<OverAllJobCardProgress>> getJobCardOverAllProgressList(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getJobCardOverAllProgressList(jobCardFileName));
    }

    @GetMapping("/packingList")
    public ResponseEntity<List<PackingListEntry>> getPackingList(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getPackingList(jobCardFileName));
    }

    @GetMapping("/dispatchDetails")
    public ResponseEntity<List<PackingListEntry>> getDispatchDetails(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getDispatchDetails(jobCardFileName));
    }


    @PostMapping(path = "/saveJobCard", consumes = "application/json")
    public void saveJobCard(@RequestBody List<JobCard> jobCardList, @RequestParam String jobCardNumber,
                            @RequestParam String customer, @RequestParam String brand,
                            @RequestParam String poNumber, @RequestParam String jobWorkVendor,
                            @RequestParam String poDate) {
        jobCardService.saveJobCard(jobCardList, jobCardNumber, customer, brand, poNumber, jobWorkVendor, poDate.substring(0, 15));
    }

    @PostMapping(path = "/saveJobCardProgress", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> saveJobCardProgress(@RequestBody JobCardProgress jobCardProgress,
                                                      @RequestParam String jobCardFileName) {
        try {
            jobCardService.saveJobCardProgress(jobCardProgress, jobCardFileName);
            return ResponseEntity.ok("");
        } catch (InvalidCountException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
    }

    @PostMapping(path = "/closeJobCard", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> closeJobCard(@RequestParam String jobCardFileName) {
        jobCardService.closeJobCard(jobCardFileName);
        return ResponseEntity.ok("JobCard Closed "+ jobCardFileName);
    }

    @PostMapping(path = "/exportJobCard", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> exportJobCard(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.exportJobCard(jobCardFileName));
    }

    @PostMapping(path = "/exportPackingList", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> exportPackingList(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.exportPackingList(jobCardFileName));
    }
}
