package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RetailSalesReportService {
    public List<RetailSalesEntryDto> getSalesDataByMonth() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2023_SALES_REPORT_V2.xlsx", 0);
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
                retailSalesEntryDto.setCategory(cellData[4]);
                retailSalesEntryDto.setProductDetails(cellData[5]);
                retailSalesEntryDto.setQuantity(cellData[6]);
                retailSalesEntryDto.setSize(cellData[7]);
                retailSalesEntryDto.setMrp(cellData[8]);
                retailSalesEntryDto.setDiscount(cellData[9]);
                retailSalesEntryDto.setSalePrice(cellData[10]);
                retailSalesEntryDto.setModeOfPayment(cellData[11]);
                retailSalesEntryDto.setCashPayment(cellData[12]);
                retailSalesEntryDto.setgPayPayment(cellData[13]);
                retailSalesEntryDto.setSwipePayment(cellData[14]);
                retailSalesEntryDto.setUpdatedBy(cellData[15]);
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

    public void addRetailSalesEntry(RetailSalesEntryDto retailSalesEntryDto) {
        Object[] data = new Object[]{retailSalesEntryDto.getSaleDate(),retailSalesEntryDto.getCustomerName(),
                retailSalesEntryDto.getGender(),
                retailSalesEntryDto.getMobileNumber(),
                retailSalesEntryDto.getCategory(),
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
                retailSalesEntryDto.getUpdatedBy()
        };
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2023_SALES_REPORT_V2.xlsx",0,data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
