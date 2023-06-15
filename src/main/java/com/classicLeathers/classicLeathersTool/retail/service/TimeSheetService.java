package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.PdfUtils;
import com.classicLeathers.classicLeathersTool.retail.model.TimeSheet;
import com.classicLeathers.classicLeathersTool.retail.model.TimeSheetDto;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TimeSheetService {

    private static int month = ((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))));

    public List<TimeSheetDto> getTimeSheetEntries(Integer monthNumber) {
        Integer sheetIndex = (monthNumber - 4) % 12;
        List<String> rowData = getTimeSheetData(sheetIndex);
        List<TimeSheet> timeSheetList = null;
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            timeSheetList = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData.length > 4) {
                    TimeSheet timeSheet = new TimeSheet();
                    timeSheet.setDate(cellData[0]);
                    timeSheet.setEmployeeName(cellData[1]);
                    timeSheet.setPresent(cellData[2]);
                    timeSheet.setInTime(cellData[3]);
                    timeSheet.setOutTime(cellData[4]);
                    timeSheet.setUpdated_time(cellData[5]);
                    timeSheet.setRowNumber(cellData[6]);
                    timeSheetList.add(timeSheet);
                }
            }
        }
        return buildTimeSheetDtiList(timeSheetList);
    }

    private List<TimeSheetDto> buildTimeSheetDtiList(List<TimeSheet> timeSheetList) {
        Map<String, List<TimeSheet>> map = new HashMap<>();
        for (TimeSheet timeSheet : timeSheetList) {

            if (map.get(new SimpleDateFormat("MMM-d-yyyy").format(new Date(timeSheet.getDate())) + "_" + timeSheet.getEmployeeName()) != null) {
                List<TimeSheet> list = map.get(new SimpleDateFormat("MMM-d-yyyy").format(new Date(timeSheet.getDate())) + "_" + timeSheet.getEmployeeName());
                list.add(timeSheet);
                map.put(new SimpleDateFormat("MMM-d-yyyy").format(new Date(timeSheet.getDate())) + "_" + timeSheet.getEmployeeName(), list);
            } else {
                List<TimeSheet> list = new ArrayList<>();
                list.add(timeSheet);
                map.put(new SimpleDateFormat("MMM-d-yyyy").format(new Date(timeSheet.getDate())) + "_" + timeSheet.getEmployeeName(), list);
            }
        }
        List<TimeSheetDto> finalTimeSheetDtos = new ArrayList<>();
        for (String mapKey : map.keySet()) {
            TimeSheetDto dto = new TimeSheetDto();
            String[] strings = mapKey.split("_");
            dto.setIsTodayEntry("" + (new SimpleDateFormat("MM-d-yyyy").format(new Date(strings[0]))
                    .equals(new SimpleDateFormat("MM-d-yyyy").format(new Date()))));
            dto.setDate(strings[0]);
            dto.setEmployeeName(strings[1]);
            String present = "Yes";
            long workingTime = 0;
            String rowNumber = "0";

            for (TimeSheet timeSheet : map.get(mapKey)) {
                if (timeSheet.getPresent().equals("No")) {
                    present = "No";
                }
                if (timeSheet.getPresent().equals("Yes") &&
                        !timeSheet.getInTime().equals("NA") && !timeSheet.getInTime().equals("Invalid")
                        && !timeSheet.getOutTime().equals("NA") && !timeSheet.getOutTime().equals("Invalid")) {

                    try {
                        workingTime = workingTime + new SimpleDateFormat("h:mm a").parse(timeSheet.getOutTime()).getTime() -
                                new SimpleDateFormat("h:mm a").parse(timeSheet.getInTime()).getTime();

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                rowNumber = timeSheet.getRowNumber();

            }

            long secs = workingTime / 1000;
            float hours = (float) (secs / 3600);
            float mins = (float) ((secs % 3600) / 60);
            dto.setPresent(present);
            dto.setTotalWorkingHours(String.format("%.2f", (hours + (mins / 60))));
            dto.setRowNumber(rowNumber);
            finalTimeSheetDtos.add(dto);
        }

        return finalTimeSheetDtos.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    public void saveTimeSheet(TimeSheet timeSheet) {
        Integer sheetIndex = (month - 4) % 12;
        List<String> rowData = getTimeSheetData(sheetIndex);
        int pageLength = rowData.size();
        List<TimeSheet> timeSheetList = null;
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            timeSheetList = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData.length > 4 && timeSheet.getEmployeeName().equals(cellData[1])) {
                    TimeSheet timeSheet1 = new TimeSheet();
                    timeSheet1.setDate(cellData[0]);
                    timeSheet1.setEmployeeName(cellData[1]);
                    timeSheet1.setPresent(cellData[2]);
                    timeSheet1.setInTime(cellData[3]);
                    timeSheet1.setOutTime(cellData[4]);
                    timeSheet1.setUpdated_time(cellData[5]);
                    timeSheet1.setRowNumber(cellData[6]);
                    timeSheetList.add(timeSheet1);
                }
            }
        }
        List<TimeSheet> sortedTimeSheetList = timeSheetList.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        TimeSheet employeeLatestTimeSheetEntry = sortedTimeSheetList.size() > 0 ? sortedTimeSheetList.get(0) : null;
        Object[] data;
        if (timeSheet.getEntryType().equals("In")) {
            if (employeeLatestTimeSheetEntry == null) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), "NA", "NA", String.valueOf(pageLength)};
                saveData(data, sheetIndex);
            }
            if (employeeLatestTimeSheetEntry != null && !employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), "NA", "NA", String.valueOf(pageLength)};
                saveData(data, sheetIndex);
            }
            if (employeeLatestTimeSheetEntry != null && employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{employeeLatestTimeSheetEntry.getDate(), employeeLatestTimeSheetEntry.getEmployeeName(), "No", employeeLatestTimeSheetEntry.getInTime(), "Invalid", new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data, sheetIndex);
                pageLength++;
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), "NA", "NA", String.valueOf(pageLength)};
                saveData(data, sheetIndex);
            }
        }
        if (timeSheet.getEntryType().equals("Out")) {
            if (employeeLatestTimeSheetEntry == null) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data, sheetIndex);
            }
            if (employeeLatestTimeSheetEntry != null && employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{employeeLatestTimeSheetEntry.getDate(), employeeLatestTimeSheetEntry.getEmployeeName(), employeeLatestTimeSheetEntry.getPresent(), employeeLatestTimeSheetEntry.getInTime(), new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data, sheetIndex);
            }
            if (employeeLatestTimeSheetEntry != null && !employeeLatestTimeSheetEntry.getOutTime().equals("NA")) {
                data = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), timeSheet.getEmployeeName(), "Yes", new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("h:mm a").format(new Date()), new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()), String.valueOf(pageLength)};
                saveData(data, sheetIndex);
            }
        }

    }

    private List<String> getTimeSheetData(Integer sheetIndex) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\23_24_TIME_SHEET_V2.xlsx", sheetIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        return rowData;
    }

    private void saveData(Object[] data, Integer sheetIndex) {
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\23_24_TIME_SHEET_V2.xlsx", sheetIndex, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String exportData(Integer monthNumber) {
        Table table = new Table(3);
        Cell cell = new Cell(1, 3);
        cell.add(new Paragraph("TimeSheet Data"));
        table.addHeaderCell(cell);
        table.addHeaderCell("Date");
        table.addHeaderCell("Name");
        table.addHeaderCell("Total Working Hours");
        List<TimeSheetDto> timeSheetDtos = getTimeSheetEntries(monthNumber);

        timeSheetDtos.forEach(timeSheetDto -> {
            table.addCell(timeSheetDto.getDate());
            table.addCell(timeSheetDto.getEmployeeName());
            table.addCell(timeSheetDto.getTotalWorkingHours());
        });


        return new PdfUtils("TimeSheet_" + monthNumber).SaveAsPdf(table);


    }

}
