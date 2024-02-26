package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.ClassicLeathersToolApplication;
import com.classicLeathers.classicLeathersTool.retail.model.*;
import jdk.nashorn.internal.objects.NativeJava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class PresentationService {
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    @Autowired
    private RetailStockReportService retailStockReportService;
    @Autowired
    private StockService stockService;

    public List<DailySalesDto> getDailySales(Integer year, Integer monthNumber) {
        return getDailySalesDto(getSalesData(year, monthNumber).get(monthNumber.toString()), monthNumber);
    }

    public List<String> getTotalSalesData() {
        Map<String, Integer> totalSalesDataMap = new TreeMap<>();
        List<String> totalSalesDataList = new ArrayList<>();
        stockService.getAvailabilityMap().keySet().forEach(key -> {
            if (key.split("_").length == 2) {
                totalSalesDataMap.put(key, Integer.valueOf("0"));
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 0; i <= calendar.get(Calendar.MONTH); i++) {
            List<String> header = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            Map<String, Integer> tempMap = new HashMap<>();
            tempMap.putAll(totalSalesDataMap);
            updateTotalSalesDataByCategoryMap(i + 1, tempMap).forEach((s, integer) -> {
                header.add(s);
                values.add(integer);
            });
            if (i == 0) {
                totalSalesDataList.add("MONTH," + header.toString().replace("[","").replace("]",""));
            }
            totalSalesDataList.add((i+1)+ "," + values.toString().replace("[","").replace("]",""));
        }
        return totalSalesDataList;
    }

    private Map<String, Integer> updateTotalSalesDataByBrandMap(int monthNumber, Map<String, Integer> tempMap) {
        getSalesData(2024, monthNumber).forEach((s, retailSalesEntryDtoList) -> {
            tempMap.remove("brand");
            retailSalesEntryDtoList.forEach(retailSalesEntryDto -> {
                if (tempMap.containsKey(retailSalesEntryDto.getBrand())) {
                    tempMap.put(retailSalesEntryDto.getBrand(),
                            tempMap.get(retailSalesEntryDto.getBrand()) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
                }
            });
        });
        return tempMap;
    }

    private Map<String, Integer> updateTotalSalesDataByCategoryMap(int monthNumber, Map<String, Integer> totalSalesDataMap) {
        getSalesData(2024, monthNumber).forEach((s, retailSalesEntryDtoList) -> {
            totalSalesDataMap.remove("brand");
            retailSalesEntryDtoList.forEach(retailSalesEntryDto -> {
                String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
                if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1)) {
                    totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1,
                            totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
                }
            });
        });
        return totalSalesDataMap;
    }
    private Map<String, Integer> updateTotalSalesDataBysubCategoryMap(int monthNumber, Map<String, Integer> totalSalesDataMap) {
        getSalesData(2024, monthNumber).forEach((s, retailSalesEntryDtoList) -> {
            totalSalesDataMap.remove("brand");
            retailSalesEntryDtoList.forEach(retailSalesEntryDto -> {
                String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
                String c2 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[1];
                if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2)) {
                    totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2,
                            totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
                }
            });
        });
        return totalSalesDataMap;
    }

    private Map<String, List<RetailSalesEntryDto>> getSalesData(Integer monthNumber) {
        return getSalesData(2024, monthNumber);
    }

    private Map<String, List<RetailSalesEntryDto>> getSalesData(Integer year, Integer monthNumber) {
        Map<String, List<RetailSalesEntryDto>> retailSalesEntryDtos = new HashMap<>();
        if (monthNumber == 0) {
            for (int i = 1; i < 13; i++) {
                if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))))
                    retailSalesEntryDtos.put(String.format("%02d", i) + "_" + new DateFormatSymbols().getMonths()[i - 1], retailSalesReportService.getSalesDataByMonth(year, i - 1));
            }
        } else {
            retailSalesEntryDtos.put(monthNumber.toString(), retailSalesReportService.getSalesDataByMonth(year, monthNumber - 1));
        }
        return retailSalesEntryDtos;
    }

    private List<DailySalesDto> getDailySalesDto(List<RetailSalesEntryDto> retailSalesEntryDtolist, Integer monthNumber) {
        Map<Integer, DailySalesDto> dailySalesDtoMap = getDefaultDailySalesDto(monthNumber);

        retailSalesEntryDtolist.forEach(retailSalesEntryDto -> {
            Date salesDate;
            try {
                salesDate = (new SimpleDateFormat("MMM-d-yyyy h:mm a").parse(retailSalesEntryDto.getSaleDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Integer day = Integer.valueOf((new SimpleDateFormat("d").format(salesDate)));
            if (dailySalesDtoMap.keySet().contains(day)) {
                dailySalesDtoMap.get(day).setSales(dailySalesDtoMap.get(day).getSales() + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
            } else {
                DailySalesDto dailySalesDto = new DailySalesDto();
                dailySalesDto.setDay(day);
                dailySalesDto.setSales(Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                dailySalesDtoMap.put(day, dailySalesDto);
            }
        });
        List<DailySalesDto> dailySalesDtos = new ArrayList<>(dailySalesDtoMap.values());

        dailySalesDtos.sort((o1, o2)
                -> o1.getDay().compareTo(
                o2.getDay()));
        return dailySalesDtos;
    }

    private Map<Integer, DailySalesDto> getDefaultDailySalesDto(Integer monthNumber) {
        Map<Integer, DailySalesDto> dailySalesDtoMap = new HashMap<>();
        LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for (int i = 1; i < localDate.lengthOfMonth() + 1; i++) {
            DailySalesDto dailySalesDto = new DailySalesDto();
            dailySalesDto.setDay(i);
            dailySalesDto.setSales(0);
            dailySalesDtoMap.put(i, dailySalesDto);
        }
        return dailySalesDtoMap;
    }

}
