package com.classicLeathers.classicLeathersTool.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.model.TimeSheet;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeSheetService {
    public void saveTimeSheet(TimeSheet timeSheet) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\23_24_TIME_SHEET_V2.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        int pageLength = rowData.size();
        List<TimeSheet> timeSheetList = null;
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            timeSheetList = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData.length > 4 && timeSheet.getEmployeeName().equals(cellData[1])) {
                    TimeSheet timeSheetDto = new TimeSheet();
                    timeSheetDto.setDate(cellData[0]);
                    timeSheetDto.setEmployeeName(cellData[1]);
                    timeSheetDto.setPresent(cellData[2]);
                    timeSheetDto.setInTime(cellData[3]);
                    timeSheetDto.setOutTime(cellData[4]);
                    timeSheetDto.setUpdated_time(cellData[5]);
                    timeSheetDto.setRowNumber(cellData[6]);
                    timeSheetList.add(timeSheetDto);
                }
            }
        }
        List<TimeSheet> sortedTimeSheetList = timeSheetList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        TimeSheet employeeLatestTimeSheetEntry = sortedTimeSheetList.size() > 0 ? sortedTimeSheetList.get(0) : null;
        Object[] data;
        if (timeSheet.getEntryType().equals("In")) {
            if (employeeLatestTimeSheetEntry == null) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), "NA", "NA", String.valueOf(pageLength)};
                System.out.println("First Entry");
                saveData(data);
            }
            if (employeeLatestTimeSheetEntry != null && !employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), "NA", "NA", String.valueOf(pageLength)};
                saveData(data);
            }
            if (employeeLatestTimeSheetEntry != null && employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{employeeLatestTimeSheetEntry.getDate(), employeeLatestTimeSheetEntry.getEmployeeName(), "No", employeeLatestTimeSheetEntry.getInTime(), "Invalid", new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data);
                pageLength++;
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), "NA", "NA", String.valueOf(pageLength)};
                saveData(data);
            }
        }
        if (timeSheet.getEntryType().equals("Out")) {
            if (employeeLatestTimeSheetEntry == null) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data);
            }
            if (employeeLatestTimeSheetEntry != null && employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{employeeLatestTimeSheetEntry.getDate(), employeeLatestTimeSheetEntry.getEmployeeName(), employeeLatestTimeSheetEntry.getPresent(), employeeLatestTimeSheetEntry.getInTime(), new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data);
            }
            if (employeeLatestTimeSheetEntry != null && !employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data);
            }
        }

    }

    private void saveData(Object[] data) {
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\23_24_TIME_SHEET_V2.xlsx", 0, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
