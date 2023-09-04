package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.retail.model.ProductSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailCustomerSalesHistoryDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RetailCustomerSalesHistoryService {
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public List<RetailCustomerSalesHistoryDto> getRetailCustomerSalesHistory(Long mobileNumber) {
        List<RetailCustomerSalesHistoryDto> retailSalesEntryDtoList = buildRetailCustomerSalesHistoryDto(getSalesData(-1), mobileNumber);
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

    public List<ProductSalesHistoryDto> getProductSalesHistoryDto(String productSearchString, Integer monthNumber) {
        List<ProductSalesHistoryDto> productSalesHistoryDtoList = buildProductSalesHistoryDto(getSalesData(monthNumber - 4), productSearchString);
        return productSalesHistoryDtoList;
    }

    private List<ProductSalesHistoryDto> buildProductSalesHistoryDto(List<RetailSalesEntryDto> salesData, String productSearchString) {
        List<ProductSalesHistoryDto> productSalesHistoryDtoList = new ArrayList<>();
        salesData.forEach(retailSalesEntryDto -> {
            if ((retailSalesEntryDto.getBrand() + " " + retailSalesEntryDto.getCategory() + " " + retailSalesEntryDto.getLeather()).
                    contains(productSearchString)) {
                ProductSalesHistoryDto productSalesHistoryDto = new ProductSalesHistoryDto();
                productSalesHistoryDto.setProduct(retailSalesEntryDto.getBrand() + " " + retailSalesEntryDto.getCategory() + " " + retailSalesEntryDto.getLeather());
                productSalesHistoryDto.setBrand(retailSalesEntryDto.getBrand());
                productSalesHistoryDto.setCategory(retailSalesEntryDto.getCategory());
                productSalesHistoryDto.setLeather(retailSalesEntryDto.getLeather());
                if (retailSalesEntryDto.getSize().equals("40")) {
                    productSalesHistoryDto.setSize_40_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("41")) {
                    productSalesHistoryDto.setSize_41_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("42")) {
                    productSalesHistoryDto.setSize_42_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("43")) {
                    productSalesHistoryDto.setSize_43_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("44")) {
                    productSalesHistoryDto.setSize_44_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("45")) {
                    productSalesHistoryDto.setSize_45_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("46")) {
                    productSalesHistoryDto.setSize_46_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("47")) {
                    productSalesHistoryDto.setSize_47_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                } else if (retailSalesEntryDto.getSize().equals("NA")) {
                    productSalesHistoryDto.setSize_40_quantity(Integer.parseInt(retailSalesEntryDto.getQuantity()));
                }
                productSalesHistoryDtoList.add(productSalesHistoryDto);
            }
        });
        return mergeProductSalesHistoryDto(productSalesHistoryDtoList);
    }


    private List<ProductSalesHistoryDto> mergeProductSalesHistoryDto(List<ProductSalesHistoryDto> productSalesHistoryDtoList) {
        Map<String, ProductSalesHistoryDto> productSalesHistoryDtoMap = new HashMap();
        productSalesHistoryDtoList.forEach(obj -> {
            String key = obj.getProduct();
            if (productSalesHistoryDtoMap.keySet().contains(key)) {
                ProductSalesHistoryDto dto = productSalesHistoryDtoMap.get(key);
                dto.setSize_40_quantity(dto.getSize_40_quantity() + obj.getSize_40_quantity());
                dto.setSize_41_quantity(dto.getSize_41_quantity() + obj.getSize_41_quantity());
                dto.setSize_42_quantity(dto.getSize_42_quantity() + obj.getSize_42_quantity());
                dto.setSize_43_quantity(dto.getSize_43_quantity() + obj.getSize_43_quantity());
                dto.setSize_44_quantity(dto.getSize_44_quantity() + obj.getSize_44_quantity());
                dto.setSize_45_quantity(dto.getSize_45_quantity() + obj.getSize_45_quantity());
                dto.setSize_46_quantity(dto.getSize_46_quantity() + obj.getSize_46_quantity());
                dto.setSize_47_quantity(dto.getSize_47_quantity() + obj.getSize_47_quantity());
            } else {
                productSalesHistoryDtoMap.put(key, obj);
            }
        });
        return new ArrayList<>(productSalesHistoryDtoMap.values());
    }

    private List<RetailSalesEntryDto> getSalesData(int sheetNumber) {
        List<RetailSalesEntryDto> retailSalesEntryDtos = new ArrayList<>();
        //update i=0 from next year
        if (sheetNumber < 0) {
            for (int i = 5; i < 12; i++) {
                if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date())))) - 4) % 12)
                    retailSalesEntryDtos.addAll(retailSalesReportService.getSalesDataByMonth(i));
            }
        } else {
            retailSalesEntryDtos.addAll(retailSalesReportService.getSalesDataByMonth(sheetNumber));
        }
        return retailSalesEntryDtos;
    }
}
