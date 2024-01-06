package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.ClassicLeathersToolApplication;
import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.Sku;
import com.classicLeathers.classicLeathersTool.retail.model.StockEntry;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockService {

    public Map<String, String> getAvailableStores() {
        Map<String, String> availableStoreMap = new HashMap<>();
        try {
            List<String> rowData = new LinkedList<>(Arrays.asList(((new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\STORES.xlsx", 0)).split("\n"))));
            if (rowData.size() != 0) {

                rowData.remove(0);
                for (String row : rowData) {
                    String[] cellData = row.split(",");
                    availableStoreMap.put(cellData[0], cellData[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return availableStoreMap;
    }

    public Map<String, String> getAvailableBrands() {
        Map<String, String> availableBrandsMap = new HashMap<>();
        try {
            List<String> rowData = new LinkedList<>(Arrays.asList(((new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\BRANDS.xlsx", 0)).split("\n"))));
            if (rowData.size() != 0) {

                rowData.remove(0);
                for (String row : rowData) {
                    String[] cellData = row.split(",");
                    availableBrandsMap.put(cellData[0], cellData[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return availableBrandsMap;
    }

    public Map<String, Sku> getAvailableSkus() {
        Map<String, Sku> availablSkuMap = new HashMap<>();
        try {
            List<String> rowData = new LinkedList<>(Arrays.asList(((new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\SKU.xlsx", 0)).split("\n"))));
            if (rowData.size() != 0) {

                rowData.remove(0);
                for (String row : rowData) {
                    String[] cellData = row.split(",");
                    Sku sku = new Sku();
                    sku.setId(cellData[0]);
                    sku.setSku(cellData[1].replace('|', '_'));
                    sku.setBrand(cellData[2]);
                    sku.setCategory(cellData[3]);
                    sku.setDescription(cellData[4]);
                    sku.setPurchaseCost(cellData[5]);
                    sku.setRetailMrp(cellData[6]);
                    availablSkuMap.put(cellData[0], sku);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return availablSkuMap;
    }

    public Map<String, Set<String>> getAvailabilityMap() {
        Map<String, Set<String>> map = new HashMap<>();
        ClassicLeathersToolApplication.availableSkuMap.values().forEach(sku -> {
            if (map.containsKey("brand")) {
                map.get("brand").add(sku.getBrand());
            } else {
                Set<String> set = new TreeSet<>();
                set.add(sku.getBrand());
                map.put("brand", set);

            }
            if (map.containsKey(sku.getBrand())) {
                map.get(sku.getBrand()).add(sku.getCategory().split("\\|")[0]);
            } else {
                Set<String> set = new TreeSet<>();
                set.add(sku.getCategory().split("\\|")[0]);
                map.put(sku.getBrand(), set);
            }
            if (map.containsKey(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0])) {
                map.get(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0])
                        .add(sku.getCategory().split("\\|")[1]);
            } else {
                Set<String> set = new TreeSet<>();
                set.add(sku.getCategory().split("\\|")[1]);
                map.put(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0], set);
            }
            if (map.containsKey(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0] + "_"
                    + sku.getCategory().split("\\|")[1])) {
                map.get(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0] + "_"
                        + sku.getCategory().split("\\|")[1]).add(sku.getSku().substring(0, sku.getSku().length() - 2));
            } else {
                Set<String> set = new TreeSet<>();
                set.add(sku.getSku().substring(0, sku.getSku().length() - 2));
                map.put(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0] + "_"
                        + sku.getCategory().split("\\|")[1], set);
            }
            if (map.containsKey(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0] + "_"
                    + sku.getCategory().split("\\|")[1] + "_" + sku.getSku().substring(0, sku.getSku().length() - 2))) {
                map.get(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0] + "_"
                        + sku.getCategory().split("\\|")[1] + "_" + sku.getSku().substring(0, sku.getSku().length() - 2))
                        .add(sku.getDescription()+"_"+sku.getRetailMrp());
            } else {
                Set<String> set = new TreeSet<>();
                set.add(sku.getDescription()+"_"+sku.getRetailMrp());
                map.put(sku.getBrand() + "_" + sku.getCategory().split("\\|")[0] + "_"
                        + sku.getCategory().split("\\|")[1] + "_" + sku.getSku().substring(0, sku.getSku().length() - 2), set);
            }
        });
        return map;
    }

    public void addStockEntry(List<StockEntry> stockDTOList) {

        stockDTOList.forEach(dto -> {
            Object[] data = new Object[]{
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                    dto.getBrand(),
                    dto.getFrom(),
                    dto.getTo(),
                    dto.getSku()
            };
            try {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\STOCK_AUDIT_REPORT.xlsx", 0, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

}
