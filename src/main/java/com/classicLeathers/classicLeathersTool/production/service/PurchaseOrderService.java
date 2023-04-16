package com.classicLeathers.classicLeathersTool.production.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.production.model.PurchaseOrder;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PurchaseOrderService {
    public List<ArticleDto> getArticles(Integer sheetIndex) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\Articles\\ARTICLES.xlsx", sheetIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            List<ArticleDto> articleDtoList = new ArrayList<>();
            for (String row : rowData) {
                ArticleDto articleDto = new ArticleDto();
                String[] cellData = row.split(",");
                articleDto.setSku(cellData[0]);
                articleDto.setLeather(cellData[1]);
                articleDto.setHandStitchingPattern(cellData[2]);
                articleDto.setStyle(cellData[3]);
                articleDto.setLining(cellData[4]);
                articleDto.setSole(cellData[5]);
                articleDtoList.add(articleDto);
            }
            return articleDtoList;
        }
        return Collections.emptyList();
    }

    public List<String> getPOFiles() {
        File folder = new File("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards");
        File[] listOfFiles = folder.listFiles();
        List<String> fileNames = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getName());
            }
        }
        return fileNames;
    }

    public void savePurchaseOrder(List<PurchaseOrder> purchaseOrderList, String fileName,
                                  String customer, String brand,
                                  String poNumber, String jobWorkVendor,
                                  String poDate) {
        try {
            createPurchaseOrder("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + fileName + ".xlsx",
                    customer, brand, poNumber, jobWorkVendor, poDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (PurchaseOrder purchaseOrder : purchaseOrderList
        ) {
            Object[] data = new Object[]{purchaseOrder.getSku(),
                    purchaseOrder.getLeather(),
                    purchaseOrder.getSize_40_quantity(),
                    purchaseOrder.getSize_41_quantity(),
                    purchaseOrder.getSize_42_quantity(),
                    purchaseOrder.getSize_43_quantity(),
                    purchaseOrder.getSize_44_quantity(),
                    purchaseOrder.getSize_45_quantity(),
                    purchaseOrder.getSize_46_quantity(),
                    purchaseOrder.getTotalQuantity(),
                    purchaseOrder.getHandStitchingPattern(),
                    purchaseOrder.getStyle(),
                    purchaseOrder.getLining(),
                    purchaseOrder.getSole()

            };
            try {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + fileName + ".xlsx", 0, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createPurchaseOrder(String filePath, String customer, String brand,
                                     String poNumber, String jobWorkVendor, String poDate) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet1 = wb.createSheet("Sheet1");
            FileOutputStream fileOut = new FileOutputStream(filePath);
            wb.write(fileOut);
            fileOut.close();
        }
        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        sheet.addMergedRegion(new CellRangeAddress(0,0,4,5));
        sheet.addMergedRegion(new CellRangeAddress(0,0,6,7));
        sheet.addMergedRegion(new CellRangeAddress(0,0,8,10));
        sheet.addMergedRegion(new CellRangeAddress(0,0,11,13));
        XSSFRow row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Client : " + customer);
        Cell cell1 = row.createCell(4);
        cell1.setCellValue("Brand : " + brand);
        Cell cell2 = row.createCell(6);
        cell2.setCellValue("Po Number : " + poNumber);
        Cell cell3 = row.createCell(8);
        cell3.setCellValue("Po Date : " + poDate);
        Cell cell4 = row.createCell(11);
        cell4.setCellValue("Job Work : " + jobWorkVendor);

        inputStream.close();
        FileOutputStream out = new FileOutputStream(filePath);

        xssfWorkbook.write(out);
        out.close();
    }

}
