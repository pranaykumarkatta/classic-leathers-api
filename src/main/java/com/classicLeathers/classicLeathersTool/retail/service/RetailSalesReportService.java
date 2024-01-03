package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RetailSalesReportService {

    private static int sheetNO = ((Integer.parseInt(new SimpleDateFormat("MM").format(new Date())))) - 1;
    public List<RetailSalesEntryDto> getSalesDataByMonth(Integer sheetNumber) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024_SALES_REPORT.xlsx", sheetNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            List<RetailSalesEntryDto> retailSalesEntryDtoList= new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                RetailSalesEntryDto retailSalesEntryDto = new RetailSalesEntryDto();
                retailSalesEntryDto.setSaleDate(new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date((cellData[0]))));
                retailSalesEntryDto.setCustomerName(cellData[1]);
                retailSalesEntryDto.setGender(cellData[2]);
                retailSalesEntryDto.setMobileNumber(cellData[3]);
                retailSalesEntryDto.setBrand(cellData[4].toUpperCase());
                retailSalesEntryDto.setCategory(cellData[5].toUpperCase());
                retailSalesEntryDto.setLeather(cellData[6].toUpperCase());
                retailSalesEntryDto.setProductDetails(cellData[7].toUpperCase());
                retailSalesEntryDto.setQuantity(cellData[8]);
                retailSalesEntryDto.setSize(cellData[9]);
                retailSalesEntryDto.setMrp(cellData[10]);
                retailSalesEntryDto.setDiscount(cellData[11]);
                retailSalesEntryDto.setSalePrice(cellData[12]);
                retailSalesEntryDto.setModeOfPayment(cellData[13]);
                retailSalesEntryDto.setCashPayment(cellData[14]);
                retailSalesEntryDto.setgPayPayment(cellData[15]);
                retailSalesEntryDto.setSwipePayment(cellData[16]);
                retailSalesEntryDto.setUpdatedBy(cellData[17]);
                retailSalesEntryDto.setStepInType(cellData[18]);
                retailSalesEntryDto.setInvoiceNumber(cellData[19]);
                retailSalesEntryDto.setIsTodaySale(""+(new SimpleDateFormat("MMM-d-yyyy").format(new Date((cellData[0])))
                        .equals((new SimpleDateFormat("MMM-d-yyyy").format(new Date())))));
                retailSalesEntryDtoList.add(retailSalesEntryDto);
            }

            return retailSalesEntryDtoList.stream()
                    .sorted(Collections.reverseOrder())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public void addRetailSalesEntry(RetailSalesEntryDto retailSalesEntryDto,String invoiceNumber) {
        Object[] data = new Object[]{retailSalesEntryDto.getSaleDate(),retailSalesEntryDto.getCustomerName(),
                retailSalesEntryDto.getGender(),
                retailSalesEntryDto.getMobileNumber(),
                retailSalesEntryDto.getBrand(),
                retailSalesEntryDto.getCategory(),
                retailSalesEntryDto.getLeather(),
                retailSalesEntryDto.getProductDetails(),
                retailSalesEntryDto.getQuantity(),
                retailSalesEntryDto.getSize(),
                retailSalesEntryDto.getMrp(),
                retailSalesEntryDto.getDiscount(),
                retailSalesEntryDto.getSalePrice(),
                retailSalesEntryDto.getModeOfPayment(),
                retailSalesEntryDto.getCashPayment(),
                retailSalesEntryDto.getgPayPayment(),
                retailSalesEntryDto.getSwipePayment(),
                retailSalesEntryDto.getUpdatedBy(),
                retailSalesEntryDto.getStepInType(),
                invoiceNumber,
                Math.round(Integer.parseInt(retailSalesEntryDto.getSalePrice())*.1)+""

        };
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024_SALES_REPORT.xlsx",sheetNO,data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
