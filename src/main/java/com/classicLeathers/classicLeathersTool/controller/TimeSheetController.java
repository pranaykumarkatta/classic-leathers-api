package com.classicLeathers.classicLeathersTool.controller;

import com.classicLeathers.classicLeathersTool.model.TimeSheet;
import com.classicLeathers.classicLeathersTool.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timeSheet")
@CrossOrigin(origins = "*")
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    @PostMapping
    public void saveTimeSheet(@RequestBody TimeSheet timeSheet) {
        timeSheetService.saveTimeSheet(timeSheet);
    }
}
