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

    public List<DrivingShoeStockEntry> getStockData() {
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
                drivingShoeStockEntry.setSearchString(drivingShoeStockEntry.getSearchString());
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
                drivingShoeStockEntry.setCostPrice(Integer.parseInt(cellData[13]));
                drivingShoeStockEntry.setStockInDate(cellData[15]);
                drivingShoeStockEntry.setLevel_0_Category(cellData[19]);
                drivingShoeStockEntry.setLevel_1_Category(cellData[20]);
                drivingShoeStockEntry.setProductDescription(cellData[21]);

                drivingShoeStockEntryList.add(drivingShoeStockEntry);
            }
        }
        return drivingShoeStockEntryList;
    }

    public Map<String, Set<String>> getStockAvailabilityData() {
        Map<String, Set<String>> map = new HashMap<>();
        getStockData().forEach(drivingShoeStockEntry -> {
            if (map.containsKey("brand")) {
                map.get("brand").add(drivingShoeStockEntry.getBrand());
            } else {
                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
                    Set<String> set = new TreeSet<>();
                    set.add(drivingShoeStockEntry.getBrand());
                    map.put("brand", set);
                }
            }
            if (map.containsKey(drivingShoeStockEntry.getBrand())) {
                map.get(drivingShoeStockEntry.getBrand()).add(drivingShoeStockEntry.getLevel_0_Category());
            } else {
                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
                    Set<String> set = new TreeSet<>();
                    set.add(drivingShoeStockEntry.getLevel_0_Category());
                    map.put(drivingShoeStockEntry.getBrand(), set);
                }
            }
            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category())) {
                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category())
                        .add(drivingShoeStockEntry.getLevel_1_Category());
            } else {
                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
                    Set<String> set = new TreeSet<>();
                    set.add(drivingShoeStockEntry.getLevel_1_Category());
                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category(), set);
                }
            }
            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                    + drivingShoeStockEntry.getLevel_1_Category())) {
                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                        + drivingShoeStockEntry.getLevel_1_Category()).add(drivingShoeStockEntry.getSku());
            } else {
                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
                    Set<String> set = new TreeSet<>();
                    set.add(drivingShoeStockEntry.getSku());
                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                            + drivingShoeStockEntry.getLevel_1_Category(), set);
                }
            }
            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                    + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku())) {
                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                        + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku()).add(drivingShoeStockEntry.getLeather());
            } else {
                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
                    Set<String> set = new TreeSet<>();
                    set.add(drivingShoeStockEntry.getLeather());
                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                            + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku(), set);
                }
            }
            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                    + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku() + "_" + drivingShoeStockEntry.getLeather())) {
                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                                + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku() + "_" + drivingShoeStockEntry.getLeather())
                        .add(drivingShoeStockEntry.getProductDescription());
            } else {
                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
                    Set<String> set = new TreeSet<>();
                    set.add(drivingShoeStockEntry.getProductDescription());
                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
                                    + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku() + "_" + drivingShoeStockEntry.getLeather()
                            , set);
                }
            }
        });
        return map;
    }

    public List<DrivingShoeStockEntry> getDrivingShoeStockReport() {

        List<DrivingShoeStockEntry> drivingShoeStockEntryList = getStockData();
        drivingShoeStockEntryList = mergeDrivingShoeStockEntries(drivingShoeStockEntryList).stream()
                .sorted((o1, o2) -> o1.getSku().compareTo(o2.getSku())).collect(Collectors.toList());
        List<DrivingShoeStockEntry> drivingShoeSalesEntryList = new ArrayList<>();

        //update i=0 from next year
        for (int i = 5; i < 12; i++) {
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
        mergeDrivingShoeStockEntries(drivingShoeSalesEntryList).forEach(obj -> {
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
        retailDrivingSalesEntryDtoList = retailSalesEntryDtoList.stream().filter(retailSalesEntryDto -> (
                (retailSalesEntryDto.getCategory().contains("LF") || retailSalesEntryDto.getCategory().contains("KORA") ||
                        retailSalesEntryDto.getCategory().contains("WAVES") || retailSalesEntryDto.getCategory().contains("A_0") ||
                        retailSalesEntryDto.getCategory().contains("RONALDO_") || retailSalesEntryDto.getCategory().contains("PRINCE_") ||
                        retailSalesEntryDto.getCategory().contains("AMERICAN_") ||
                        retailSalesEntryDto.getCategory().contains("MIRAT") || retailSalesEntryDto.getCategory().contains("_BAG") ||
                        retailSalesEntryDto.getCategory().contains("FORMAL") ||
                        retailSalesEntryDto.getCategory().contains("D_") || retailSalesEntryDto.getCategory().contains("CASUAL_") ||
                        retailSalesEntryDto.getCategory().contains("018_JUSTON") ||
                        retailSalesEntryDto.getCategory().contains("WLT") || retailSalesEntryDto.getCategory().contains("BLT"))
                        && retailSalesEntryDto.getSize() != "NA")).collect(Collectors.toList());
        List<DrivingShoeStockEntry> drivingShoeSalesEntryList = new ArrayList<>();
        retailDrivingSalesEntryDtoList.forEach(retailSalesEntryDto -> {
            DrivingShoeStockEntry drivingShoeStockEntry = new DrivingShoeStockEntry();
            drivingShoeStockEntry.setBrand(retailSalesEntryDto.getBrand());
            drivingShoeStockEntry.setSku(retailSalesEntryDto.getCategory());
            drivingShoeStockEntry.setLeather(retailSalesEntryDto.getLeather());
            drivingShoeStockEntry.setSearchString(retailSalesEntryDto.getSearchString());
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

        return mergeDrivingShoeStockEntries(drivingShoeSalesEntryList);
    }

    private List<DrivingShoeStockEntry> mergeDrivingShoeStockEntries(List<DrivingShoeStockEntry> drivingShoeEntryList) {
        Map<String, DrivingShoeStockEntry> drivingShoeSalesEntryMap = new HashMap();
        drivingShoeEntryList.forEach(obj -> {
            String key = obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather();
            if (drivingShoeSalesEntryMap.keySet().contains(key)) {
                DrivingShoeStockEntry existingEntry = drivingShoeSalesEntryMap.get(key);
                existingEntry.setSize_40_quantity(existingEntry.getSize_40_quantity() + obj.getSize_40_quantity());
                existingEntry.setSize_41_quantity(existingEntry.getSize_41_quantity() + obj.getSize_41_quantity());
                existingEntry.setSize_42_quantity(existingEntry.getSize_42_quantity() + obj.getSize_42_quantity());
                existingEntry.setSize_43_quantity(existingEntry.getSize_43_quantity() + obj.getSize_43_quantity());
                existingEntry.setSize_44_quantity(existingEntry.getSize_44_quantity() + obj.getSize_44_quantity());
                existingEntry.setSize_45_quantity(existingEntry.getSize_45_quantity() + obj.getSize_45_quantity());
                existingEntry.setSize_46_quantity(existingEntry.getSize_46_quantity() + obj.getSize_46_quantity());
                existingEntry.setSize_47_quantity(existingEntry.getSize_47_quantity() + obj.getSize_47_quantity());
                existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getTotalQuantity());
                if (existingEntry.getActualOrderedQuantity() != null && obj.getActualOrderedQuantity() != null)
                    existingEntry.setActualOrderedQuantity(existingEntry.getActualOrderedQuantity() + obj.getActualOrderedQuantity());
            } else {
                drivingShoeSalesEntryMap.put(key, obj);
            }
        });
        return new ArrayList<>(drivingShoeSalesEntryMap.values());
    }

    public String exportStockReport() {
        getDrivingShoeStockReport().forEach(drivingShoeStockEntry -> {
            Object[] data = new Object[]{drivingShoeStockEntry.getSku(),
                    drivingShoeStockEntry.getLeather(),
                    drivingShoeStockEntry.getBrand(),
                    drivingShoeStockEntry.getSize_40_quantity().toString(),
                    drivingShoeStockEntry.getSize_41_quantity().toString(),
                    drivingShoeStockEntry.getSize_42_quantity().toString(),
                    drivingShoeStockEntry.getSize_43_quantity().toString(),
                    drivingShoeStockEntry.getSize_44_quantity().toString(),
                    drivingShoeStockEntry.getSize_45_quantity().toString(),
                    drivingShoeStockEntry.getSize_46_quantity().toString(),
                    drivingShoeStockEntry.getSize_47_quantity().toString(),
                    drivingShoeStockEntry.getTotalQuantity().toString()
            };
            try {
                new FileUtils().WriteData("D:\\onedrive\\Tag_classicLeathers\\Reports\\STOCK_AUDIT_REPORT.xlsx", 0, data);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return "Report saved successfully";
    }

    public void saveStockEntry(DrivingShoeStockEntry drivingShoeStockEntry) {
        Object[] data = null;
        if (drivingShoeStockEntry.getEntryType().equals("OUTWARD")) {
            data = new Object[]{new SimpleDateFormat("yyyyMMdH").format(new Date()).toString(), drivingShoeStockEntry.getSku(),
                    drivingShoeStockEntry.getLeather(),
                    drivingShoeStockEntry.getBrand(),
                    "-" + drivingShoeStockEntry.getSize_40_quantity(),
                    "-" + drivingShoeStockEntry.getSize_41_quantity(),
                    "-" + drivingShoeStockEntry.getSize_42_quantity(),
                    "-" + drivingShoeStockEntry.getSize_43_quantity(),
                    "-" + drivingShoeStockEntry.getSize_44_quantity(),
                    "-" + drivingShoeStockEntry.getSize_45_quantity(),
                    "-" + drivingShoeStockEntry.getSize_46_quantity(),
                    "-" + drivingShoeStockEntry.getSize_47_quantity(),
                    "-" + drivingShoeStockEntry.getTotalQuantity(),
                    "-" + drivingShoeStockEntry.getCostPrice(),
                    "-" + drivingShoeStockEntry.getTotalQuantity() * drivingShoeStockEntry.getCostPrice(),
                    new SimpleDateFormat("d-MMM-yyyy").format(new Date()),
                    drivingShoeStockEntry.getEntryType(),
                    drivingShoeStockEntry.getTo(),
                    drivingShoeStockEntry.getFrom(),
                    drivingShoeStockEntry.getLevel_0_Category(),
                    drivingShoeStockEntry.getLevel_1_Category(),
                    drivingShoeStockEntry.getProductDescription(),

            };
        } else {
            data = new Object[]{new SimpleDateFormat("yyyyMMdH").format(new Date()).toString(), drivingShoeStockEntry.getSku(),
                    drivingShoeStockEntry.getLeather(),
                    drivingShoeStockEntry.getBrand(),
                    drivingShoeStockEntry.getSize_40_quantity(),
                    drivingShoeStockEntry.getSize_41_quantity(),
                    drivingShoeStockEntry.getSize_42_quantity(),
                    drivingShoeStockEntry.getSize_43_quantity(),
                    drivingShoeStockEntry.getSize_44_quantity(),
                    drivingShoeStockEntry.getSize_45_quantity(),
                    drivingShoeStockEntry.getSize_46_quantity(),
                    drivingShoeStockEntry.getSize_47_quantity(),
                    drivingShoeStockEntry.getTotalQuantity(),
                    drivingShoeStockEntry.getCostPrice(),
                    drivingShoeStockEntry.getTotalQuantity() * drivingShoeStockEntry.getCostPrice(),
                    new SimpleDateFormat("d-MMM-yyyy").format(new Date()),
                    drivingShoeStockEntry.getEntryType(),
                    drivingShoeStockEntry.getTo(),
                    drivingShoeStockEntry.getFrom(),
                    drivingShoeStockEntry.getLevel_0_Category(),
                    drivingShoeStockEntry.getLevel_1_Category(),
                    drivingShoeStockEntry.getProductDescription(),

            };
        }
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\STOCK_AUDIT_REPORTS_V2.xlsx", 0, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
