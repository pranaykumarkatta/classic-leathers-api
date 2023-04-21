package com.classicLeathers.classicLeathersTool.retail;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import com.classicLeathers.classicLeathersTool.retail.model.Inventory;
import com.google.gson.Gson;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public static void main(String[] args) throws Exception {
        String s;
        Integer.parseInt(new Test().getS());
//        File file = new File(
//                "C:\\Users\\Dell\\OneDrive\\CLASSIC_DOCS\\RETAIL_DOCS\\STOCK AUDIT REPORTS_V1.xlsx");
//
//        FileInputStream inputStream = new FileInputStream(file);
//        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
//
//        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);     //creating a Sheet object to retrieve object
//
//        Iterator<Row> itr = sheet.iterator();    //iterating over excel file
//        String fileData = "";
//        while (itr.hasNext()) {
//            Row row = itr.next();
//            if (row.getRowNum() >= 3 && row.getRowNum() != sheet.getLastRowNum() - 1) {
//                Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_STRING:    //field that represents string cell type
//                            fileData = fileData + cell.getStringCellValue() + ",";
//                            break;
//                        case Cell.CELL_TYPE_NUMERIC:    //field that represents number cell type
//                            fileData = fileData + cell.getNumericCellValue() + ",";
//                            break;
//                        case Cell.CELL_TYPE_FORMULA:    //field that represents number cell type
//                            fileData = fileData + cell.getNumericCellValue() + ",";
//                            break;
//                        default:
//                    }
//                }
//                fileData = fileData + "\n";
//            }
//        }
//        buildInventory(fileData);
//    }
//
//    private static void buildInventory(String data) {
//        List<Inventory> inventoryList = new ArrayList<>();
//
//        Map<String, Integer> quantity = new LinkedHashMap<>();
//        String[] rowData = data.split("\n");
//        for (String row : rowData) {
//            Inventory inventory = new Inventory();
//            String[] cellData = row.split(",");
//            inventory.setArticle(cellData[0]);
//            inventory.setProduct(cellData[1]);
//            inventory.setBrand(cellData[2]);
//            quantity.put("SIZE_40", (int) Float.parseFloat(cellData[4]));
//            quantity.put("SIZE_41", (int) Float.parseFloat(cellData[5]));
//            quantity.put("SIZE_42", (int) Float.parseFloat(cellData[6]));
//            quantity.put("SIZE_43", (int) Float.parseFloat(cellData[7]));
//            quantity.put("SIZE_44", (int) Float.parseFloat(cellData[8]));
//            quantity.put("SIZE_45", (int) Float.parseFloat(cellData[9]));
//            quantity.put("SIZE_46", (int) Float.parseFloat(cellData[10]));
//            inventory.setQuantity(quantity);
//            inventory.setTotalQuantity((int) Float.parseFloat(cellData[11]));
//            inventoryList.add(inventory);
//        }
//        System.out.println(new Gson().toJson(inventoryList));
    }

}
