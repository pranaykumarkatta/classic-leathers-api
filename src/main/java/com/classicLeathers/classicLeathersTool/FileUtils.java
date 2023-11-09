package com.classicLeathers.classicLeathersTool;

import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import com.itextpdf.html2pdf.HtmlConverter;
import javafx.print.Collation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
            if (obj instanceof Long) {
                cell.setCellValue(obj.toString());
            } else if (obj instanceof Integer) {
                cell.setCellValue(obj.toString());
            } else {
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

    public void saveInvoice(List<RetailSalesEntryDto> retailSalesEntryDtoList, String invoiceNumber) throws FileNotFoundException {
        String htmlInvoiceString = "<html>\n" +
                "<head>\n" +
                "    <title>Classic Leathers</title> \n" +
                "\t<style>\n" +
                "\t\t.classicTheamColor {\n" +
                "\t\t\twidth:700px;\n" +
                "\t\t\tborder-radius: 10px;\n" +
                "\t\t}\n" +
                "\t\t.header_div {\t\t\t\n" +
                "\t\t\theight:60px;\n" +
                "\t\t\tpadding: 15px;\n" +
                "\t\t\tborder-radius: 10px;\n" +
                "\t\t\tborder-bottom: 2px solid #a7a8ab;\n" +
                "\t\t}\n" +
                "\t\t.header_div .logo{\n" +
                "\t\t\tfloat:left; \n" +
                "\t\t}\n" +
                "\t\t.header_div p{\n" +
                "\t\t\tfont-weight:600;\n" +
                "\t\t\twidth: 350px;\n" +
                "\t\t\tfloat: left;\n" +
                "\t\t\ttext-align: center; \n" +
                "\t\t}\n" +
                "\t\t.header_div img{\n" +
                "\t\t\twidth:150px;\n" +
                "\t\t}\n" +
                "\t\t.header_div .address{\n" +
                "\t\t\tfloat:right;\n" +
                "\t\t\tfont-size:11px;\n" +
                "\t\t}\n" +
                "\t\t.body_div{\n" +
                "\t\t\theight:250px\n" +
                "\t\t} \t\t\n" +
                "\t\t.body_div table{\n" +
                "\t\t\ttext-align:center;\n" +
                "\t\t\tfont-size:12px;\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t} \t\t\n" +
                "\t\t.body_div table th{\n" +
                "\t\t\tpadding:5px;\n" +
                "\t\t\ttext-align:left;\n" +
                "\t\t\tborder-collapse: collapse;\n" +
                "\t\t}  \t\t\n" +
                "\t\t.body_div td{\n" +
                "\t\t\tborder-bottom:1px solid black;\n" +
                "\t\t}   \t\t\n" +
                "\t\t.body_div p{\n" +
                "\t\t\tfont-size:12px; \n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tpadding-top: 40px;\n" +
                "\t\t}  \t\n" +
                "\t\t.small_label{\n" +
                "\t\t\tpadding:4px;\n" +
                "\t\t\twidth:75px;\n" +
                "\t\t}\t\t\n" +
                "\t\t.large_label{\n" +
                "\t\t\tpadding:4px;\n" +
                "\t\t\twidth:200px;\n" +
                "\t\t}\n" +
                "\t\t.footer_div p{ \n" +
                "\t\t\tfont-size:11px;\n" +
                "\t\t\tpadding: 15px;\n" +
                "\t\t\tborder-top: 2px solid #a7a8ab;\n" +
                "\t\t\ttext-align: center; \n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body class=\"classicTheamColor\">\n" +
                "\t<div class=\"header_div\">\n" +
                "\t\t<table class=\"logo\"> \n" +
                "\t\t\t<tr><td><img src=\"D:\\onedrive\\Tag_classicLeathers\\classic-leathers-ui\\resource\\classic_logo.png\"/></td></tr>\n" +
                "\t\t</table>\n" +
                "\t\t<p>TAX INVOICE</p>\n" +
                "\t\t<table class=\"address\"> \n" +
                "\t\t\t<tr><td>C/O <b>JANATHA GROUP INDIA</b></td></tr>\n" +
                "\t\t\t<tr><td>#143, TK Street, Tirupathi</td></tr>\n" +
                "\t\t\t<tr><td>Andhra Pradesh, 517501</td></tr>\n" +
                "\t\t\t<tr><td></td></tr>\n" +
                "\t\t\t<tr><td>GST: <b>37AAPFJ6622G1Z3</b></td></tr>\n" +
                "\t\t</table> \t\t\n" +
                "\t</div><br>\n" +
                "\t<div class=\"body_div\"> \n" +
                "\t\t<table>  \n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<th colspan=\"2\">Invioce No : " + invoiceNumber + "</th>\n" +
                "\t\t\t\t<th colspan=\"3\">Name : " + retailSalesEntryDtoList.get(0).getCustomerName() + "</th>\n" +
                "\t\t\t\t<th colspan=\"3\">Date & Time : " + new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()) + "</th>\n" +
                "\t\t\t</tr> \n" +
                "\t\t\t<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr>\n" +
                "\t\t\t<tr><th></th><th></th><th></th><th></th><th></th><th></th><th></th><th></th></tr>\n" +
                "\t\t\t<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>S.No</b></td>\n" +
                "\t\t\t\t<td class=\"large_label\"><b>Product</b></td>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>Quantity</b></td>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>Price</b></td>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>Discount</b></td>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>GST %</b></td>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>GST Amount</b></td>\n" +
                "\t\t\t\t<td class=\"small_label\"><b>Total</b></td>\n" +
                "\t\t\t</tr> \n";
        int i = 0;
        int totalGST = 0;
        int totalSales = 0;
        for (RetailSalesEntryDto retailSalesEntryDto : retailSalesEntryDtoList) {
            i++;
            int mrp = Integer.parseInt(retailSalesEntryDto.getMrp());
            int discount = Integer.parseInt(retailSalesEntryDto.getDiscount());
            int salePrice = (mrp - discount)*Integer.parseInt(retailSalesEntryDto.getQuantity());
            int gstAmount = (int) Math.round(salePrice * 0.12);
            int itemTotal = salePrice + gstAmount;
            totalGST = totalGST + gstAmount;
            totalSales = totalSales + itemTotal;
            String size = (retailSalesEntryDto.getSize() != "NA" ? " - " + retailSalesEntryDto.getSize() : "" );
            htmlInvoiceString = htmlInvoiceString + "\t\t\t<tr>\n" +
                    "\t\t\t\t<td class=\"small_label\">" + i + "</td>\n" +
                    "\t\t\t\t<td class=\"large_label\">" + retailSalesEntryDto.getProductDetails()+"" +size+ "</td>\n" +
                    "\t\t\t\t<td class=\"small_label\">" + retailSalesEntryDto.getQuantity() + "</td>\n" +
                    "\t\t\t\t<td class=\"small_label\">" + retailSalesEntryDto.getMrp() + "</td>\n" +
                    "\t\t\t\t<td class=\"small_label\">" + retailSalesEntryDto.getDiscount() + "</td>\n" +
                    "\t\t\t\t<td class=\"small_label\">12 %</td>\n" +
                    "\t\t\t\t<td class=\"small_label\">" + gstAmount + "</td>\n" +
                    "\t\t\t\t<td class=\"small_label\">" + itemTotal + "</td>\n" +
                    "\t\t\t</tr> \n";
        }

        htmlInvoiceString = htmlInvoiceString + "\t\t\t<tr>\n" +
                "\t\t\t\t<th></th><th></th><th></th><th></th><th></th>\n" +
                "\t\t\t\t<th colspan=\"2\"><b>Promotion discount : </b></th>\n" +
                "\t\t\t\t<td >- " + totalGST + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<th></th><th></th><th></th><th></th><th></th>\n" +
                "\t\t\t\t<th colspan=\"2\"><b>Net Amount : </b></th>\n" +
                "\t\t\t\t<td >" + (totalSales - totalGST) + "</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table> \n" +
                "\t\t<p>This is computer generated invoice no signature and stamp required</p>\n" +
                "\t</div>\n" +
                "\t<div class=\"footer_div\">\n" +
                "\t\t<p><b>&copy; Classic Leathers LLP</b></p>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>\n";
        HtmlConverter.convertToPdf(htmlInvoiceString, new FileOutputStream("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\Invoices\\" + invoiceNumber + ".pdf"));
    }

}
