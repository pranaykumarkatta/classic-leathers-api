package com.classicLeathers.classicLeathersTool.retail.service;

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
        Map<String, Sku> availableStoreMap = new HashMap<>();
        try {
            List<String> rowData = new LinkedList<>(Arrays.asList(((new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\SKU.xlsx", 0)).split("\n"))));
            if (rowData.size() != 0) {

                rowData.remove(0);
                for (String row : rowData) {
                    String[] cellData = row.split(",");
                    Sku sku = new Sku();
                    sku.setId(cellData[0]);
                    sku.setSku(cellData[1]);
                    sku.setBrand(cellData[2]);
                    sku.setCategory(cellData[3]);
                    sku.setDescription(cellData[4]);
                    sku.setPurchaseCost(cellData[5]);
                    sku.setRetailMrp(cellData[6]);
                    availableStoreMap.put(cellData[0], sku);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return availableStoreMap;
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
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024\\STOCK_AUDIT_REPORT_2024.xlsx", 0, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }
}
