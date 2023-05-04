package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.TimeSheet;
import com.classicLeathers.classicLeathersTool.retail.model.TimeSheetDto;
import com.classicLeathers.classicLeathersTool.retail.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeSheet")
@CrossOrigin(origins = "*")
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    @GetMapping
    public ResponseEntity<List<TimeSheetDto>> getTimeSheetEntries(@RequestParam Integer monthNumber) {
        return ResponseEntity.ok(timeSheetService.getTimeSheetEntries(monthNumber));
    }

    @GetMapping(path = "/exportData")
    public String  exportData(@RequestParam Integer monthNumber) {
        return timeSheetService.exportData(monthNumber);
    }

    @PostMapping
    public void saveTimeSheet(@RequestBody TimeSheet timeSheet) {
        timeSheetService.saveTimeSheet(timeSheet);
    }
}
