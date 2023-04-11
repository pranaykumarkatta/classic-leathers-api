package com.classicLeathers.classicLeathersTool;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

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
                        fileData = fileData + cell.getNumericCellValue() + ",";
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        fileData = fileData + cell.getStringCellValue() + ",";
                        break;
                    default:
                }
            }
            fileData = fileData + "\n";
        }

        return fileData;
    }

    public void WriteData(String filePath, Integer sheetIndex, Object[] data) throws Exception {
        File file = new File(filePath);

        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetIndex);

        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
        int cellid = 0;

        for (Object obj : data) {
            Cell cell = row.createCell(cellid++);
            cell.setCellValue((String) obj);
        }
        inputStream.close();
        FileOutputStream out = new FileOutputStream(
                new File(filePath));

        xssfWorkbook.write(out);
        out.close();
    }
}
