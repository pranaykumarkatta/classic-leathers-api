package com.classicLeathers.classicLeathersTool.production.controller;

import com.classicLeathers.classicLeathersTool.production.model.JobCard;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import com.classicLeathers.classicLeathersTool.production.model.JobCardProgressDto;
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

    @GetMapping("/jobCardProgressSkus")
    public ResponseEntity<List<String>> getJobCardProgressSkus(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getJobCardProgressSkus(jobCardFileName));
    }

    @GetMapping("/jobCardProgressList")
    public ResponseEntity<List<JobCardProgressDto>> getJobCardProgressList(@RequestParam String jobCardFileName) {
        return ResponseEntity.ok(jobCardService.getJobCardProgressList(jobCardFileName));
    }


    @PostMapping(path = "/saveJobCard", consumes = "application/json")
    public void saveJobCard(@RequestBody List<JobCard> jobCardList, @RequestParam String fileName,
                            @RequestParam String customer, @RequestParam String brand,
                            @RequestParam String poNumber, @RequestParam String jobWorkVendor,
                            @RequestParam String poDate) {
        jobCardService.saveJobCard(jobCardList, fileName, customer, brand, poNumber, jobWorkVendor, poDate.substring(0, 15));
    }

    @PostMapping(path = "/saveJobCardProgress", consumes = "application/json")
    public void saveJobCardProgress(@RequestBody JobCardProgressDto jobCardProgressDto,
                                    @RequestParam String jobCardFileName) {
        jobCardService.saveJobCardProgress(jobCardProgressDto, jobCardFileName);
    }
}
