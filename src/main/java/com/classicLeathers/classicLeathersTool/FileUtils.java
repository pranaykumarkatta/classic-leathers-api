package com.classicLeathers.classicLeathersTool;

import javafx.print.Collation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FileUtils {

    public String getFileData(String filePath, Integer sheetIndex) throws Exception {
        File file = new File(filePath);

        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetIndex);

        Iterator<Row> itr = sheet.iterator();
        String fileData = "";
        while (itr.hasNext()) {
            Row row = itr.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        fileData = fileData + cell.getStringCellValue() + ",";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        fileData = fileData + Math.round(cell.getNumericCellValue()) + ",";
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        try {
                            fileData = fileData + cell.getStringCellValue() + ",";
                            break;
                        } catch (IllegalStateException e) {
                            fileData = fileData + Math.round(cell.getNumericCellValue()) + ",";
                            break;
                        }
                    default:
                }
            }
            fileData = fileData + "\n";
        }

        return fileData;
    }

    public void WriteData(String filePath, Integer sheetIndex, Object[] data) throws Exception {
        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetIndex);

        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
        int cellid = 0;

        for (Object obj : data) {
            Cell cell = row.createCell(cellid++);
            if (obj instanceof Long){
                cell.setCellValue(obj.toString());
            }else {
                cell.setCellValue((String) obj);
            }
        }
        inputStream.close();
        FileOutputStream out = new FileOutputStream(filePath);

        xssfWorkbook.write(out);
        out.close();
    }

    public List<String> getData(String filePath, Integer sheetIndex) {
        System.out.println("Fetching data from : " + filePath);
        File file = new File(filePath);

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found : " + filePath);
            return Collections.emptyList();
        }
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println("Error while reading data from : " + filePath);
            return Collections.emptyList();
        }

        XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetIndex);

        Iterator<Row> itr = sheet.iterator();
        String fileData = "";
        while (itr.hasNext()) {
            Row row = itr.next();

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        fileData = fileData + cell.getStringCellValue() + ",";
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        fileData = fileData + Math.round(cell.getNumericCellValue()) + ",";
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        try {
                            fileData = fileData + cell.getStringCellValue() + ",";
                            break;
                        } catch (IllegalStateException e) {
                            fileData = fileData + Math.round(cell.getNumericCellValue()) + ",";
                            break;
                        }
                    default:
                }
            }
            fileData = fileData + "\n";
        }
        return Arrays.asList(fileData.split("\n"));
    }

}
