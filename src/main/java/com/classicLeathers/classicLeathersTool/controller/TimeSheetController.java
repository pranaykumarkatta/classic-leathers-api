package com.classicLeathers.classicLeathersTool.controller;

import com.classicLeathers.classicLeathersTool.model.TimeSheet;
import com.classicLeathers.classicLeathersTool.model.TimeSheetDto;
import com.classicLeathers.classicLeathersTool.service.TimeSheetService;
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
    public ResponseEntity<List<TimeSheetDto>> getTimeSheetEntries() {
        return ResponseEntity.ok(timeSheetService.getTimeSheetEntries());
    }

    @PostMapping
    public void saveTimeSheet(@RequestBody TimeSheet timeSheet) {
        timeSheetService.saveTimeSheet(timeSheet);
    }
}
