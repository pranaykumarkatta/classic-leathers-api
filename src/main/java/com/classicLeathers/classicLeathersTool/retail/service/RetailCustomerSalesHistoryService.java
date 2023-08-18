package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.retail.model.RetailCustomerSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RetailCustomerSalesHistoryService {
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public List<RetailCustomerSalesHistoryDto> getRetailCustomerSalesHistory(Long mobileNumber) {
        List<RetailCustomerSalesHistoryDto> retailSalesEntryDtoList = buildRetailCustomerSalesHistoryDto(getSalesData(), mobileNumber);
        return retailSalesEntryDtoList;
    }

    private List<RetailCustomerSalesHistoryDto> buildRetailCustomerSalesHistoryDto(List<RetailSalesEntryDto> salesData, Long mobileNumber) {
        List<RetailCustomerSalesHistoryDto> customerSalesHistoryDtos = new ArrayList<>();
        salesData.forEach(retailSalesEntryDto -> {
            if (retailSalesEntryDto.getMobileNumber().equals(mobileNumber.toString())) {
                RetailCustomerSalesHistoryDto retailCustomerSalesHistoryDto = new RetailCustomerSalesHistoryDto();
                retailCustomerSalesHistoryDto.setCustomerName(retailSalesEntryDto.getCustomerName());
                retailCustomerSalesHistoryDto.setGender(retailSalesEntryDto.getGender());
                retailCustomerSalesHistoryDto.setMobileNumber(Long.parseLong(retailSalesEntryDto.getMobileNumber()));
                retailCustomerSalesHistoryDto.setBrand(retailSalesEntryDto.getBrand());
                retailCustomerSalesHistoryDto.setCategory(retailSalesEntryDto.getCategory());
                retailCustomerSalesHistoryDto.setLeather(retailSalesEntryDto.getLeather());
                retailCustomerSalesHistoryDto.setSalePrice(retailSalesEntryDto.getSalePrice());
                retailCustomerSalesHistoryDto.setSaleDate(retailSalesEntryDto.getSaleDate());
                customerSalesHistoryDtos.add(retailCustomerSalesHistoryDto);
            }
        });
        return customerSalesHistoryDtos;
    }

    private List<RetailSalesEntryDto> getSalesData() {
        List<RetailSalesEntryDto> retailSalesEntryDtos = new ArrayList<>();
        //update i=0 from next year
        for (int i = 0; i < 12; i++) {
            if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date())))) - 4) % 12)
                retailSalesEntryDtos.addAll(retailSalesReportService.getSalesDataByMonth(i));
        }
        return retailSalesEntryDtos;
    }
}
