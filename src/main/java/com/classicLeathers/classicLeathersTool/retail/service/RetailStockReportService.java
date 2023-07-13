package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.DrivingShoeStockEntry;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RetailStockReportService {

    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public List<DrivingShoeStockEntry> getDrivingShoeStockReport() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\STOCK_AUDIT_REPORTS_V2.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        List<DrivingShoeStockEntry> drivingShoeStockEntryList = new ArrayList<>();
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            for (String row : rowData) {
                String[] cellData = row.split(",");
                DrivingShoeStockEntry drivingShoeStockEntry = new DrivingShoeStockEntry();
                drivingShoeStockEntry.setRowNumber(cellData[0]);
                drivingShoeStockEntry.setSku(cellData[1]);
                drivingShoeStockEntry.setLeather(cellData[2]);
                drivingShoeStockEntry.setBrand(cellData[3]);
                drivingShoeStockEntry.setSize_40_quantity(Integer.parseInt(cellData[4]));
                drivingShoeStockEntry.setSize_41_quantity(Integer.parseInt(cellData[5]));
                drivingShoeStockEntry.setSize_42_quantity(Integer.parseInt(cellData[6]));
                drivingShoeStockEntry.setSize_43_quantity(Integer.parseInt(cellData[7]));
                drivingShoeStockEntry.setSize_44_quantity(Integer.parseInt(cellData[8]));
                drivingShoeStockEntry.setSize_45_quantity(Integer.parseInt(cellData[9]));
                drivingShoeStockEntry.setSize_46_quantity(Integer.parseInt(cellData[10]));
                drivingShoeStockEntry.setSize_47_quantity(Integer.parseInt(cellData[11]));
                drivingShoeStockEntry.setTotalQuantity(Integer.parseInt(cellData[12]));
                drivingShoeStockEntry.setActualOrderedQuantity(Integer.parseInt(cellData[12]));

                drivingShoeStockEntryList.add(drivingShoeStockEntry);
            }
        }

        List<DrivingShoeStockEntry> drivingShoeSalesEntryList = new ArrayList<>();

        //update i=0 from next year
        for (int i = 3; i < 12; i++) {
            if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date())))) - 4) % 12)
                drivingShoeSalesEntryList.addAll(getDrivingShoeSalesByMonth(i));
        }

        return adjustStockLevel(drivingShoeStockEntryList, drivingShoeSalesEntryList);
    }

    private List<DrivingShoeStockEntry> adjustStockLevel(List<DrivingShoeStockEntry> drivingShoeStockEntryList,
                                                         List<DrivingShoeStockEntry> drivingShoeSalesEntryList) {
        Map<String, DrivingShoeStockEntry> drivingShoeStockEntryMap = new HashMap<>();
        Map<String, DrivingShoeStockEntry> drivingShoeSalesEntryMap = new HashMap<>();
        List<DrivingShoeStockEntry> adjustedDrivingShoeStockEntryList = new ArrayList<>();
        drivingShoeStockEntryList.forEach(obj -> {
            drivingShoeStockEntryMap.put(obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather(), obj);
        });
        drivingShoeSalesEntryList.forEach(obj -> {
            drivingShoeSalesEntryMap.put(obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather(), obj);
        });

        drivingShoeStockEntryList.forEach(obj -> {
            String key = obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather();
            DrivingShoeStockEntry saleEntry = drivingShoeSalesEntryMap.get(key);
            obj.setSize_40_SalesStockInfo("STOCKED_IN : " + obj.getSize_40_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_40_quantity()));
            obj.setSize_41_SalesStockInfo("STOCKED_IN : " + obj.getSize_41_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_41_quantity()));
            obj.setSize_42_SalesStockInfo("STOCKED_IN : " + obj.getSize_42_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_42_quantity()));
            obj.setSize_43_SalesStockInfo("STOCKED_IN : " + obj.getSize_43_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_43_quantity()));
            obj.setSize_44_SalesStockInfo("STOCKED_IN : " + obj.getSize_44_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_44_quantity()));
            obj.setSize_45_SalesStockInfo("STOCKED_IN : " + obj.getSize_45_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_45_quantity()));
            obj.setSize_46_SalesStockInfo("STOCKED_IN : " + obj.getSize_46_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_46_quantity()));
            obj.setSize_47_SalesStockInfo("STOCKED_IN : " + obj.getSize_47_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_47_quantity()));
            obj.setTotal_Quantity_SalesStockInfo("STOCKED_IN : " + obj.getTotalQuantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getTotalQuantity()));
            if (!drivingShoeSalesEntryMap.containsKey(key)) {
                adjustedDrivingShoeStockEntryList.add(obj);
            } else {
                obj.setSize_40_quantity(obj.getSize_40_quantity() - saleEntry.getSize_40_quantity());
                obj.setSize_41_quantity(obj.getSize_41_quantity() - saleEntry.getSize_41_quantity());
                obj.setSize_42_quantity(obj.getSize_42_quantity() - saleEntry.getSize_42_quantity());
                obj.setSize_43_quantity(obj.getSize_43_quantity() - saleEntry.getSize_43_quantity());
                obj.setSize_44_quantity(obj.getSize_44_quantity() - saleEntry.getSize_44_quantity());
                obj.setSize_45_quantity(obj.getSize_45_quantity() - saleEntry.getSize_45_quantity());
                obj.setSize_46_quantity(obj.getSize_46_quantity() - saleEntry.getSize_46_quantity());
                obj.setSize_47_quantity(obj.getSize_47_quantity() - saleEntry.getSize_47_quantity());
                obj.setTotalQuantity(obj.getTotalQuantity() - saleEntry.getTotalQuantity());

                if (obj.getSize_40_quantity() < 0 || obj.getSize_41_quantity() < 0 || obj.getSize_42_quantity() < 0 ||
                        obj.getSize_43_quantity() < 0 || obj.getSize_44_quantity() < 0 || obj.getSize_45_quantity() < 0 ||
                        obj.getSize_46_quantity() < 0 || obj.getSize_47_quantity() < 0) {
                    obj.setValidEntry(false);
                }
                adjustedDrivingShoeStockEntryList.add(obj);
            }
        });
        return adjustedDrivingShoeStockEntryList;
    }

    private List<DrivingShoeStockEntry> getDrivingShoeSalesByMonth(int sheetNumber) {
        List<RetailSalesEntryDto> retailDrivingSalesEntryDtoList = new ArrayList<>();
        List<RetailSalesEntryDto> retailSalesEntryDtoList = retailSalesReportService.getSalesDataByMonth(sheetNumber);
        retailDrivingSalesEntryDtoList = retailSalesEntryDtoList.stream().filter(retailSalesEntryDto -> (retailSalesEntryDto.getCategory().contains("LF")
                && retailSalesEntryDto.getSize() != "NA")).collect(Collectors.toList());
        List<DrivingShoeStockEntry> drivingShoeSalesEntryList = new ArrayList<>();
        retailDrivingSalesEntryDtoList.forEach(retailSalesEntryDto -> {
            DrivingShoeStockEntry drivingShoeStockEntry = new DrivingShoeStockEntry();
            drivingShoeStockEntry.setBrand("99CRAFTS");
            drivingShoeStockEntry.setSku(retailSalesEntryDto.getCategory());
            drivingShoeStockEntry.setLeather(retailSalesEntryDto.getProductDetails());
            switch (retailSalesEntryDto.getSize()) {
                case "40":
                    drivingShoeStockEntry.setSize_40_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "41":
                    drivingShoeStockEntry.setSize_41_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "42":
                    drivingShoeStockEntry.setSize_42_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "43":
                    drivingShoeStockEntry.setSize_43_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "44":
                    drivingShoeStockEntry.setSize_44_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "45":
                    drivingShoeStockEntry.setSize_45_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "46":
                    drivingShoeStockEntry.setSize_46_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
                case "47":
                    drivingShoeStockEntry.setSize_47_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                    break;
            }
            drivingShoeStockEntry.setTotalQuantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
            drivingShoeSalesEntryList.add(drivingShoeStockEntry);
        });
        Map<String, DrivingShoeStockEntry> drivingShoeSalesEntryMap = new HashMap();
        drivingShoeSalesEntryList.forEach(obj -> {
            String key = obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather();
            if (drivingShoeSalesEntryMap.keySet().contains(key)) {
                DrivingShoeStockEntry existingEntry = drivingShoeSalesEntryMap.get(key);
                if (obj.getSize_40_quantity() > 0) {
                    existingEntry.setSize_40_quantity(existingEntry.getSize_40_quantity() + obj.getSize_40_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_40_quantity());
                } else if (obj.getSize_41_quantity() > 0) {
                    existingEntry.setSize_41_quantity(existingEntry.getSize_41_quantity() + obj.getSize_41_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_41_quantity());
                } else if (obj.getSize_42_quantity() > 0) {
                    existingEntry.setSize_42_quantity(existingEntry.getSize_42_quantity() + obj.getSize_42_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_42_quantity());
                } else if (obj.getSize_43_quantity() > 0) {
                    existingEntry.setSize_43_quantity(existingEntry.getSize_43_quantity() + obj.getSize_43_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_43_quantity());
                } else if (obj.getSize_44_quantity() > 0) {
                    existingEntry.setSize_44_quantity(existingEntry.getSize_44_quantity() + obj.getSize_44_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_44_quantity());
                } else if (obj.getSize_45_quantity() > 0) {
                    existingEntry.setSize_45_quantity(existingEntry.getSize_45_quantity() + obj.getSize_45_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_45_quantity());
                } else if (obj.getSize_46_quantity() > 0) {
                    existingEntry.setSize_46_quantity(existingEntry.getSize_46_quantity() + obj.getSize_46_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_46_quantity());
                } else if (obj.getSize_47_quantity() > 0) {
                    existingEntry.setSize_47_quantity(existingEntry.getSize_47_quantity() + obj.getSize_47_quantity());
                    existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getSize_47_quantity());
                }
            } else {
                drivingShoeSalesEntryMap.put(key, obj);
            }
        });
        return new ArrayList<>(drivingShoeSalesEntryMap.values());
    }
}
