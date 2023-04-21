package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.DrivingShoeStockEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RetailStockReportService {
    public List<DrivingShoeStockEntry> getDrivingShoeStockReport() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\STOCK AUDIT REPORTS_V2.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            List<DrivingShoeStockEntry> drivingShoeStockEntryList = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                DrivingShoeStockEntry drivingShoeStockEntry = new DrivingShoeStockEntry();
                drivingShoeStockEntry.setSku(cellData[0]);
                drivingShoeStockEntry.setLeather(cellData[1]);
                drivingShoeStockEntry.setBrand(cellData[2]);
                drivingShoeStockEntry.setSize_40_quantity(cellData[3]);
                drivingShoeStockEntry.setSize_41_quantity(cellData[4]);
                drivingShoeStockEntry.setSize_42_quantity(cellData[5]);
                drivingShoeStockEntry.setSize_43_quantity(cellData[6]);
                drivingShoeStockEntry.setSize_44_quantity(cellData[7]);
                drivingShoeStockEntry.setSize_45_quantity(cellData[8]);
                drivingShoeStockEntry.setSize_46_quantity(cellData[9]);
                drivingShoeStockEntry.setSize_47_quantity(cellData[10]);
                drivingShoeStockEntry.setTotalQuantity("" + (
                        Integer.parseInt(cellData[3]) + Integer.parseInt(cellData[4])
                                + Integer.parseInt(cellData[5]) + Integer.parseInt(cellData[6])
                                + Integer.parseInt(cellData[7]) + Integer.parseInt(cellData[8])
                                + Integer.parseInt(cellData[9]) + Integer.parseInt(cellData[10])));

                drivingShoeStockEntryList.add(drivingShoeStockEntry);
            }

            return drivingShoeStockEntryList;
        }
        return Collections.emptyList();
    }
}
