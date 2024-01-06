package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.StockAvailabilityDto;
import com.classicLeathers.classicLeathersTool.retail.model.StockReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RetailStockReportService {

    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public Collection<StockAvailabilityDto> getStockReport() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\STOCK_AUDIT_REPORT.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        StockReportDto stockReportDto;
        Map<String, StockReportDto> stockReportDtoMap = new HashMap();
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            for (String row : rowData) {
                String[] cellData = row.split(",");
                stockReportDto = new StockReportDto();
                stockReportDto.setArticle(cellData[4]);
                stockReportDto.setBrand(cellData[1]);
                stockReportDto.setFrom(cellData[2]);
                stockReportDto.setTo(cellData[3]);
                if (stockReportDtoMap.containsKey(stockReportDto.getBrand() + "||" + stockReportDto.getArticle())) {
                    stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle());
                    if (stockReportDto.getTo().equals("CLASSIC LEATHERS RETAIL")) {
                        stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle()).setStockInQuantity(
                                stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle()).getStockInQuantity() + 1);
                    } else if (stockReportDto.getTo().equals("RETAIL")) {
                        stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle()).setRetailSalesQuantity(
                                stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle()).getRetailSalesQuantity() + 1);
                    } else if (stockReportDto.getTo().equals("AJIO")) {
                        stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle()).setOnlineSalesQuantity(
                                stockReportDtoMap.get(stockReportDto.getBrand() + "||" + stockReportDto.getArticle()).getRetailSalesQuantity() + 1);
                    } else {
                        System.out.println("INVALID STOCK ENTRY FOR :" + cellData[4]);
                    }
                } else {
                    stockReportDto = new StockReportDto();
                    stockReportDto.setArticle(cellData[4]);
                    stockReportDto.setBrand(cellData[1]);
                    stockReportDto.setFrom(cellData[2]);
                    stockReportDto.setTo(cellData[3]);
                    if (stockReportDto.getTo().equals("CLASSIC LEATHERS RETAIL")) {
                        stockReportDto.setStockInQuantity(1);
                        stockReportDto.setRetailSalesQuantity(0);
                        stockReportDto.setOnlineSalesQuantity(0);
                    } else {
                        System.out.println("INVALID STOCK ENTRY FOR :" + cellData[4]);
                    }
                    stockReportDtoMap.put(stockReportDto.getBrand() + "||" + stockReportDto.getArticle(), stockReportDto);
                }
            }
        }

        Map<String, StockAvailabilityDto> stockAvailabilityDtoMap = new HashMap<>();
        stockReportDtoMap.keySet().forEach(s -> {
            String substring = s.substring(0, s.length() - 2);
            String size =  s.substring(s.length() - 2, s.length());
            if (stockAvailabilityDtoMap.keySet().contains(substring)) {
                if (size.equals("39")) {
                    stockAvailabilityDtoMap.get(substring).setSize_39(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("40")) {
                    stockAvailabilityDtoMap.get(substring).setSize_40(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("41")) {
                    stockAvailabilityDtoMap.get(substring).setSize_41(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("42")) {
                    stockAvailabilityDtoMap.get(substring).setSize_42(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("43")) {
                    stockAvailabilityDtoMap.get(substring).setSize_43(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("44")) {
                    stockAvailabilityDtoMap.get(substring).setSize_44(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("45")) {
                    stockAvailabilityDtoMap.get(substring).setSize_45(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("46")) {
                    stockAvailabilityDtoMap.get(substring).setSize_46(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("47")) {
                    stockAvailabilityDtoMap.get(substring).setSize_47(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                }
            } else {
                StockAvailabilityDto stockAvailabilityDto = new StockAvailabilityDto();
                stockAvailabilityDto.setBrand(substring.split("\\|\\|")[0]);
                stockAvailabilityDto.setArticle(substring.split("\\|\\|")[1]);
                if (size.equals("39")) {
                    stockAvailabilityDto.setSize_39(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("40")) {
                    stockAvailabilityDto.setSize_40(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("41")) {
                    stockAvailabilityDto.setSize_41(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("42")) {
                    stockAvailabilityDto.setSize_42(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("43")) {
                    stockAvailabilityDto.setSize_43(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("44")) {
                    stockAvailabilityDto.setSize_44(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("45")) {
                    stockAvailabilityDto.setSize_45(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("46")) {
                    stockAvailabilityDto.setSize_46(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                } else if (size.equals("47")) {
                    stockAvailabilityDto.setSize_47(stockReportDtoMap.get(s).getStockInQuantity()
                            - stockReportDtoMap.get(s).getOnlineSalesQuantity()
                            - stockReportDtoMap.get(s).getRetailSalesQuantity());
                }
                stockAvailabilityDtoMap.put(substring, stockAvailabilityDto);
            }
        });
        return stockAvailabilityDtoMap.values();
    }
//    public Map<String, Set<String>> getStockAvailabilityData() {
//        Map<String, Set<String>> map = new HashMap<>();
//        getStockData().forEach(drivingShoeStockEntry -> {
//            if (map.containsKey("brand")) {
//                map.get("brand").add(drivingShoeStockEntry.getBrand());
//            } else {
//                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
//                    Set<String> set = new TreeSet<>();
//                    set.add(drivingShoeStockEntry.getBrand());
//                    map.put("brand", set);
//                }
//            }
//            if (map.containsKey(drivingShoeStockEntry.getBrand())) {
//                map.get(drivingShoeStockEntry.getBrand()).add(drivingShoeStockEntry.getLevel_0_Category());
//            } else {
//                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
//                    Set<String> set = new TreeSet<>();
//                    set.add(drivingShoeStockEntry.getLevel_0_Category());
//                    map.put(drivingShoeStockEntry.getBrand(), set);
//                }
//            }
//            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category())) {
//                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category())
//                        .add(drivingShoeStockEntry.getLevel_1_Category());
//            } else {
//                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
//                    Set<String> set = new TreeSet<>();
//                    set.add(drivingShoeStockEntry.getLevel_1_Category());
//                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category(), set);
//                }
//            }
//            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                    + drivingShoeStockEntry.getLevel_1_Category())) {
//                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                        + drivingShoeStockEntry.getLevel_1_Category()).add(drivingShoeStockEntry.getSku());
//            } else {
//                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
//                    Set<String> set = new TreeSet<>();
//                    set.add(drivingShoeStockEntry.getSku());
//                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                            + drivingShoeStockEntry.getLevel_1_Category(), set);
//                }
//            }
//            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                    + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku())) {
//                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                        + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku()).add(drivingShoeStockEntry.getLeather());
//            } else {
//                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
//                    Set<String> set = new TreeSet<>();
//                    set.add(drivingShoeStockEntry.getLeather());
//                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                            + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku(), set);
//                }
//            }
//            if (map.containsKey(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                    + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku() + "_" + drivingShoeStockEntry.getLeather())) {
//                map.get(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                                + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku() + "_" + drivingShoeStockEntry.getLeather())
//                        .add(drivingShoeStockEntry.getProductDescription());
//            } else {
//                if (drivingShoeStockEntry.getTotalQuantity() > 0) {
//                    Set<String> set = new TreeSet<>();
//                    set.add(drivingShoeStockEntry.getProductDescription());
//                    map.put(drivingShoeStockEntry.getBrand() + "_" + drivingShoeStockEntry.getLevel_0_Category() + "_"
//                                    + drivingShoeStockEntry.getLevel_1_Category() + "_" + drivingShoeStockEntry.getSku() + "_" + drivingShoeStockEntry.getLeather()
//                            , set);
//                }
//            }
//        });
//        return map;
//    }
//
//    public List<DrivingShoeStockEntry> getDrivingShoeStockReport() {
//
//        List<DrivingShoeStockEntry> drivingShoeStockEntryList = getStockData();
//        drivingShoeStockEntryList = mergeDrivingShoeStockEntries(drivingShoeStockEntryList).stream()
//                .sorted((o1, o2) -> o1.getSku().compareTo(o2.getSku())).collect(Collectors.toList());
//        List<DrivingShoeStockEntry> drivingShoeSalesEntryList = new ArrayList<>();
//
//        //update i=0 from next year
//        for (int i = 1; i < 13; i++) {
//            if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))))
//                drivingShoeSalesEntryList.addAll(getDrivingShoeSalesByMonth(i));
//        }
//
//        return adjustStockLevel(drivingShoeStockEntryList, drivingShoeSalesEntryList);
//    }
//
//    private List<DrivingShoeStockEntry> adjustStockLevel(List<DrivingShoeStockEntry> drivingShoeStockEntryList,
//                                                         List<DrivingShoeStockEntry> drivingShoeSalesEntryList) {
//        Map<String, DrivingShoeStockEntry> drivingShoeStockEntryMap = new HashMap<>();
//        Map<String, DrivingShoeStockEntry> drivingShoeSalesEntryMap = new HashMap<>();
//        List<DrivingShoeStockEntry> adjustedDrivingShoeStockEntryList = new ArrayList<>();
//        drivingShoeStockEntryList.forEach(obj -> {
//            drivingShoeStockEntryMap.put(obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather(), obj);
//        });
//        mergeDrivingShoeStockEntries(drivingShoeSalesEntryList).forEach(obj -> {
//            drivingShoeSalesEntryMap.put(obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather(), obj);
//        });
//
//        drivingShoeStockEntryList.forEach(obj -> {
//            String key = obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather();
//            DrivingShoeStockEntry saleEntry = drivingShoeSalesEntryMap.get(key);
//            obj.setSize_40_SalesStockInfo("STOCKED_IN : " + obj.getSize_40_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_40_quantity()));
//            obj.setSize_41_SalesStockInfo("STOCKED_IN : " + obj.getSize_41_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_41_quantity()));
//            obj.setSize_42_SalesStockInfo("STOCKED_IN : " + obj.getSize_42_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_42_quantity()));
//            obj.setSize_43_SalesStockInfo("STOCKED_IN : " + obj.getSize_43_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_43_quantity()));
//            obj.setSize_44_SalesStockInfo("STOCKED_IN : " + obj.getSize_44_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_44_quantity()));
//            obj.setSize_45_SalesStockInfo("STOCKED_IN : " + obj.getSize_45_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_45_quantity()));
//            obj.setSize_46_SalesStockInfo("STOCKED_IN : " + obj.getSize_46_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_46_quantity()));
//            obj.setSize_47_SalesStockInfo("STOCKED_IN : " + obj.getSize_47_quantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getSize_47_quantity()));
//            obj.setTotal_Quantity_SalesStockInfo("STOCKED_IN : " + obj.getTotalQuantity() + "   SOLED_OUT : " + (saleEntry == null ? 0 : saleEntry.getTotalQuantity()));
//            if (!drivingShoeSalesEntryMap.containsKey(key)) {
//                adjustedDrivingShoeStockEntryList.add(obj);
//            } else {
//                obj.setSize_40_quantity(obj.getSize_40_quantity() - saleEntry.getSize_40_quantity());
//                obj.setSize_41_quantity(obj.getSize_41_quantity() - saleEntry.getSize_41_quantity());
//                obj.setSize_42_quantity(obj.getSize_42_quantity() - saleEntry.getSize_42_quantity());
//                obj.setSize_43_quantity(obj.getSize_43_quantity() - saleEntry.getSize_43_quantity());
//                obj.setSize_44_quantity(obj.getSize_44_quantity() - saleEntry.getSize_44_quantity());
//                obj.setSize_45_quantity(obj.getSize_45_quantity() - saleEntry.getSize_45_quantity());
//                obj.setSize_46_quantity(obj.getSize_46_quantity() - saleEntry.getSize_46_quantity());
//                obj.setSize_47_quantity(obj.getSize_47_quantity() - saleEntry.getSize_47_quantity());
//                obj.setTotalQuantity(obj.getTotalQuantity() - saleEntry.getTotalQuantity());
//
//                if (obj.getSize_40_quantity() < 0 || obj.getSize_41_quantity() < 0 || obj.getSize_42_quantity() < 0 ||
//                        obj.getSize_43_quantity() < 0 || obj.getSize_44_quantity() < 0 || obj.getSize_45_quantity() < 0 ||
//                        obj.getSize_46_quantity() < 0 || obj.getSize_47_quantity() < 0) {
//                    obj.setValidEntry(false);
//                }
//                adjustedDrivingShoeStockEntryList.add(obj);
//            }
//        });
//        return adjustedDrivingShoeStockEntryList;
//    }
//
//    private List<DrivingShoeStockEntry> getDrivingShoeSalesByMonth(int sheetNumber) {
//        List<RetailSalesEntryDto> retailDrivingSalesEntryDtoList = new ArrayList<>();
//        List<RetailSalesEntryDto> retailSalesEntryDtoList = retailSalesReportService.getSalesDataByMonth(sheetNumber);
//        retailDrivingSalesEntryDtoList = retailSalesEntryDtoList.stream().filter(retailSalesEntryDto -> (
//                (retailSalesEntryDto.getCategory().contains("LF") || retailSalesEntryDto.getCategory().contains("KORA") ||
//                        retailSalesEntryDto.getCategory().contains("WAVES") || retailSalesEntryDto.getCategory().contains("A_0") ||
//                        retailSalesEntryDto.getCategory().contains("RONALDO_") || retailSalesEntryDto.getCategory().contains("PRINCE_") ||
//                        retailSalesEntryDto.getCategory().contains("AMERICAN_") ||
//                        retailSalesEntryDto.getCategory().contains("MIRAT") || retailSalesEntryDto.getCategory().contains("_BAG") ||
//                        retailSalesEntryDto.getCategory().contains("FORMAL") ||
//                        retailSalesEntryDto.getCategory().contains("D_") || retailSalesEntryDto.getCategory().contains("CASUAL_") ||
//                        retailSalesEntryDto.getCategory().contains("018_JUSTON") ||
//                        retailSalesEntryDto.getCategory().contains("WLT") || retailSalesEntryDto.getCategory().contains("BLT"))
//                        && retailSalesEntryDto.getSize() != "NA")).collect(Collectors.toList());
//        List<DrivingShoeStockEntry> drivingShoeSalesEntryList = new ArrayList<>();
//        retailDrivingSalesEntryDtoList.forEach(retailSalesEntryDto -> {
//            DrivingShoeStockEntry drivingShoeStockEntry = new DrivingShoeStockEntry();
//            drivingShoeStockEntry.setBrand(retailSalesEntryDto.getBrand());
//            drivingShoeStockEntry.setSku(retailSalesEntryDto.getCategory());
//            drivingShoeStockEntry.setLeather(retailSalesEntryDto.getLeather());
//            drivingShoeStockEntry.setSearchString(retailSalesEntryDto.getSearchString());
//            switch (retailSalesEntryDto.getSize()) {
//                case "40":
//                    drivingShoeStockEntry.setSize_40_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "41":
//                    drivingShoeStockEntry.setSize_41_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "42":
//                    drivingShoeStockEntry.setSize_42_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "43":
//                    drivingShoeStockEntry.setSize_43_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "44":
//                    drivingShoeStockEntry.setSize_44_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "45":
//                    drivingShoeStockEntry.setSize_45_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "46":
//                    drivingShoeStockEntry.setSize_46_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//                case "47":
//                    drivingShoeStockEntry.setSize_47_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//                    break;
//            }
//            drivingShoeStockEntry.setTotalQuantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
//            drivingShoeSalesEntryList.add(drivingShoeStockEntry);
//        });
//
//        return mergeDrivingShoeStockEntries(drivingShoeSalesEntryList);
//    }
//
//    private List<DrivingShoeStockEntry> mergeDrivingShoeStockEntries(List<DrivingShoeStockEntry> drivingShoeEntryList) {
//        Map<String, DrivingShoeStockEntry> drivingShoeSalesEntryMap = new HashMap();
//        drivingShoeEntryList.forEach(obj -> {
//            String key = obj.getBrand() + "_" + obj.getSku() + "_" + obj.getLeather();
//            if (drivingShoeSalesEntryMap.keySet().contains(key)) {
//                DrivingShoeStockEntry existingEntry = drivingShoeSalesEntryMap.get(key);
//                existingEntry.setSize_40_quantity(existingEntry.getSize_40_quantity() + obj.getSize_40_quantity());
//                existingEntry.setSize_41_quantity(existingEntry.getSize_41_quantity() + obj.getSize_41_quantity());
//                existingEntry.setSize_42_quantity(existingEntry.getSize_42_quantity() + obj.getSize_42_quantity());
//                existingEntry.setSize_43_quantity(existingEntry.getSize_43_quantity() + obj.getSize_43_quantity());
//                existingEntry.setSize_44_quantity(existingEntry.getSize_44_quantity() + obj.getSize_44_quantity());
//                existingEntry.setSize_45_quantity(existingEntry.getSize_45_quantity() + obj.getSize_45_quantity());
//                existingEntry.setSize_46_quantity(existingEntry.getSize_46_quantity() + obj.getSize_46_quantity());
//                existingEntry.setSize_47_quantity(existingEntry.getSize_47_quantity() + obj.getSize_47_quantity());
//                existingEntry.setTotalQuantity(existingEntry.getTotalQuantity() + obj.getTotalQuantity());
//                if (existingEntry.getActualOrderedQuantity() != null && obj.getActualOrderedQuantity() != null)
//                    existingEntry.setActualOrderedQuantity(existingEntry.getActualOrderedQuantity() + obj.getActualOrderedQuantity());
//            } else {
//                drivingShoeSalesEntryMap.put(key, obj);
//            }
//        });
//        return new ArrayList<>(drivingShoeSalesEntryMap.values());
//    }
//
//    public String exportStockReport() {
//        getDrivingShoeStockReport().forEach(drivingShoeStockEntry -> {
//            Object[] data = new Object[]{drivingShoeStockEntry.getSku(),
//                    drivingShoeStockEntry.getLeather(),
//                    drivingShoeStockEntry.getBrand(),
//                    drivingShoeStockEntry.getSize_40_quantity().toString(),
//                    drivingShoeStockEntry.getSize_41_quantity().toString(),
//                    drivingShoeStockEntry.getSize_42_quantity().toString(),
//                    drivingShoeStockEntry.getSize_43_quantity().toString(),
//                    drivingShoeStockEntry.getSize_44_quantity().toString(),
//                    drivingShoeStockEntry.getSize_45_quantity().toString(),
//                    drivingShoeStockEntry.getSize_46_quantity().toString(),
//                    drivingShoeStockEntry.getSize_47_quantity().toString(),
//                    drivingShoeStockEntry.getTotalQuantity().toString()
//            };
//            try {
//                new FileUtils().WriteData("D:\\onedrive\\Tag_classicLeathers\\Reports\\STOCK_AUDIT_REPORT.xlsx", 0, data);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return "Report saved successfully";
//    }
//
//    public void saveStockEntry(DrivingShoeStockEntry drivingShoeStockEntry) {
//        Object[] data = null;
//        if (drivingShoeStockEntry.getEntryType().equals("OUTWARD")) {
//            data = new Object[]{new SimpleDateFormat("yyyyMMdH").format(new Date()).toString(), drivingShoeStockEntry.getSku(),
//                    drivingShoeStockEntry.getLeather(),
//                    drivingShoeStockEntry.getBrand(),
//                    "-" + drivingShoeStockEntry.getSize_40_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_41_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_42_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_43_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_44_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_45_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_46_quantity(),
//                    "-" + drivingShoeStockEntry.getSize_47_quantity(),
//                    "-" + drivingShoeStockEntry.getTotalQuantity(),
//                    "-" + drivingShoeStockEntry.getCostPrice(),
//                    "-" + drivingShoeStockEntry.getTotalQuantity() * drivingShoeStockEntry.getCostPrice(),
//                    new SimpleDateFormat("d-MMM-yyyy").format(new Date()),
//                    drivingShoeStockEntry.getEntryType(),
//                    drivingShoeStockEntry.getTo(),
//                    drivingShoeStockEntry.getFrom(),
//                    drivingShoeStockEntry.getLevel_0_Category(),
//                    drivingShoeStockEntry.getLevel_1_Category(),
//                    drivingShoeStockEntry.getProductDescription(),
//
//            };
//        } else {
//            data = new Object[]{new SimpleDateFormat("yyyyMMdH").format(new Date()).toString(), drivingShoeStockEntry.getSku(),
//                    drivingShoeStockEntry.getLeather(),
//                    drivingShoeStockEntry.getBrand(),
//                    drivingShoeStockEntry.getSize_40_quantity(),
//                    drivingShoeStockEntry.getSize_41_quantity(),
//                    drivingShoeStockEntry.getSize_42_quantity(),
//                    drivingShoeStockEntry.getSize_43_quantity(),
//                    drivingShoeStockEntry.getSize_44_quantity(),
//                    drivingShoeStockEntry.getSize_45_quantity(),
//                    drivingShoeStockEntry.getSize_46_quantity(),
//                    drivingShoeStockEntry.getSize_47_quantity(),
//                    drivingShoeStockEntry.getTotalQuantity(),
//                    drivingShoeStockEntry.getCostPrice(),
//                    drivingShoeStockEntry.getTotalQuantity() * drivingShoeStockEntry.getCostPrice(),
//                    new SimpleDateFormat("d-MMM-yyyy").format(new Date()),
//                    drivingShoeStockEntry.getEntryType(),
//                    drivingShoeStockEntry.getTo(),
//                    drivingShoeStockEntry.getFrom(),
//                    drivingShoeStockEntry.getLevel_0_Category(),
//                    drivingShoeStockEntry.getLevel_1_Category(),
//                    drivingShoeStockEntry.getProductDescription(),
//
//            };
//        }
//        try {
//            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\STOCK_AUDIT_REPORTS_V2.xlsx", 0, data);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
