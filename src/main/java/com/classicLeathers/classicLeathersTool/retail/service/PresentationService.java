package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.ClassicLeathersToolApplication;
import com.classicLeathers.classicLeathersTool.retail.model.DailySalesDto;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Map<String, List<RetailSalesEntryDto>> updateSalesData(Integer year, Integer monthNumber) {
        Map<String, List<RetailSalesEntryDto>> retailSalesEntryDtos = new HashMap<>();
        if (monthNumber == 0) {
            for (int i = 1; i < 13; i++) {
                if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date()))))))
                    retailSalesEntryDtos.put(String.valueOf(i), retailSalesReportService.getSalesDataByMonth(year, i - 1));
            }
        } else {
            retailSalesEntryDtos.put(monthNumber.toString(), retailSalesReportService.getSalesDataByMonth(year, monthNumber - 1));
        }
        return retailSalesEntryDtos;
    }

    public List<DailySalesDto> getDailySales(Integer year, Integer monthNumber) {
        return getDailySalesDto(retailSalesReportService.getSalesDataByMonth(year, monthNumber - 1));
    }

    private List<DailySalesDto> getDailySalesDto(List<RetailSalesEntryDto> retailSalesEntryDtolist) {
        Map<Integer, DailySalesDto> dailySalesDtoMap = getDefaultDailySalesDto();

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

    private Map<Integer, DailySalesDto> getDefaultDailySalesDto() {
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

    public List<String> getTotalSales(String str) {
        String filterString = str.replace("_All", "").replace("_undefined", "").replace("All", "");
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(0, getTotalSalesDataList("Brand", filterString));
        map.put(1, getTotalSalesDataList("Category", filterString));
        map.put(2, getTotalSalesDataList("SubCategory", filterString));
        map.put(3, getTotalSalesDataList("product", filterString));
        return map.get(filterString.isEmpty() ? 0 : filterString.split("_").length);
    }

    public List<String> getTotalSalesDataList(String level, String filterString) {
        Map<String, Integer> totalSalesDataMap = new TreeMap<>();
        List<String> totalSalesDataList = new ArrayList<>();
        stockService.getAvailabilityMap().keySet().forEach(key -> {
            if (level.equals("Brand")) {
                if (key.split("_").length == 1) {
                    totalSalesDataMap.put(key, Integer.valueOf("0"));
                }
            }
            if (level.equals("Category")) {
                if (key.split("_").length == 2) {
                    totalSalesDataMap.put(key, Integer.valueOf("0"));
                }
            }
            if (level.equals("SubCategory")) {
                if (key.split("_").length == 3) {
                    totalSalesDataMap.put(key, Integer.valueOf("0"));
                }
            }
            if (level.equals("product")) {
                if (key.split("_").length == 3) {
                    totalSalesDataMap.put(key, Integer.valueOf("0"));
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Map<String, Integer> tempMap = null;
        for (int i = 0; i <= calendar.get(Calendar.MONTH); i++) {
            List<String> header = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            tempMap = new HashMap<>();
            tempMap.putAll(totalSalesDataMap);
            if (level.equals("Brand")) {
                updateTotalSalesDataByBrandMap(i + 1, tempMap).forEach((s, integer) -> {
                    if (filterString.length() == 0) {
                        header.add(s);
                        values.add(integer);
                    } else {
                        if (s.contains(filterString)) {
                            header.add(s);
                            values.add(integer);
                        }
                    }
                });
            }
            if (level.equals("Category")) {
                updateTotalSalesDataByCategoryMap(i + 1, tempMap).forEach((s, integer) -> {
                    if (s.contains(filterString)) {
                        header.add(s);
                        values.add(integer);
                    }
                });
            }
            if (level.equals("SubCategory")) {
                updateTotalSalesDataBysubCategoryMap(i + 1, tempMap).forEach((s, integer) -> {
                    if (s.contains(filterString)) {
                        header.add(s);
                        values.add(integer);
                    }
                });
            }
            if (level.equals("product")) {
                updateTotalSalesDataByProductMap(i + 1, tempMap).forEach((s, integer) -> {
                    if (s.contains(filterString)) {
                        header.add(s);
                        values.add(integer);
                    }
                });
            }
            if (i == 0) {
                totalSalesDataList.add("MONTH," + header.toString().replace("[", "").replace("]", ""));
            }
            totalSalesDataList.add((i + 1) + "," + values.toString().replace("[", "").replace("]", ""));
        }
        return totalSalesDataList;
    }

    private Map<String, Integer> updateTotalSalesDataByBrandMap(int monthNumber, Map<String, Integer> tempMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            tempMap.remove("brand");
            if (tempMap.containsKey(retailSalesEntryDto.getBrand())) {
                tempMap.put(retailSalesEntryDto.getBrand(),
                        tempMap.get(retailSalesEntryDto.getBrand()) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
            }
        });
        return tempMap;
    }

    private Map<String, Integer> updateTotalSalesDataByCategoryMap(int monthNumber, Map<String, Integer> totalSalesDataMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            totalSalesDataMap.remove("brand");
            String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
            if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1)) {
                totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1,
                        totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
            }
        });
        return totalSalesDataMap;
    }

    private Map<String, Integer> updateTotalSalesDataBysubCategoryMap(int monthNumber, Map<String, Integer> totalSalesDataMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            totalSalesDataMap.remove("brand");
            String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
            String c2 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[1];
            if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2)) {
                totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2,
                        totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
            }
        });
        return totalSalesDataMap;
    }

    private Map<String, Integer> updateTotalSalesDataByProductMap(int monthNumber, Map<String, Integer> totalSalesDataMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            totalSalesDataMap.remove("brand");
            String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
            String c2 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[1];
            if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2)) {
                totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2,
                        totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2) + Integer.valueOf(retailSalesEntryDto.getSalePrice()));
            }
        });
        return totalSalesDataMap;
    }


    public List<String> getProfitPercent(String str) {
        String filterString = str.replace("_All", "").replace("_undefined", "").replace("All", "");
        Map<Integer, List<String>> map = new HashMap<>();
        map.put(0, getProfitPercentList("Brand", filterString));
        map.put(1, getProfitPercentList("Category", filterString));
        map.put(2, getProfitPercentList("SubCategory", filterString));
        map.put(3, getProfitPercentList("product", filterString));
        return map.get(filterString.isEmpty() ? 0 : filterString.split("_").length);
    }

    public List<String> getProfitPercentList(String level, String filterString) {
        Map<String, String> profitPercentDataMap = new TreeMap<>();
        List<String> profitPercentDataList = new ArrayList<>();
        Map<String, Set<String>> availabilityMap = new HashMap<>();
        availabilityMap.putAll(stockService.getAvailabilityMap());
        availabilityMap.keySet().forEach(key -> {
            if (level.equals("Brand")) {
                if (key.split("_").length == 1) {
                    profitPercentDataMap.put(key, "0!0");
                }
            }
            if (level.equals("Category")) {
                if (key.split("_").length == 2) {
                    profitPercentDataMap.put(key, "0!0");
                }
            }
            if (level.equals("SubCategory")) {
                if (key.split("_").length == 3) {
                    profitPercentDataMap.put(key, "0!0");
                }
            }
            if (level.equals("product")) {
                if (key.split("_").length == 3) {
                    profitPercentDataMap.put(key, "0!0");
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Map<String, String> tempMap = null;
        for (int i = 0; i <= calendar.get(Calendar.MONTH); i++) {
            List<String> header = new ArrayList<>();
            List<Integer> values = new ArrayList<>();
            tempMap = new HashMap<>();
            tempMap.putAll(profitPercentDataMap);
            if (level.equals("Brand")) {
                updateProfitPercentDataByBrandMap(i + 1, tempMap, availabilityMap).forEach((s, s2) -> {
                    if (filterString.length() == 0) {
                        header.add(s);
                        values.add((int) (((Double.parseDouble(s2.split("!")[1]) - Double.parseDouble(s2.split("!")[0])) / Double.parseDouble(s2.split("!")[0])) * 100));
                    } else {
                        if (s.contains(filterString)) {
                            header.add(s);
                            values.add((int) (((Double.parseDouble(s2.split("!")[1]) - Double.parseDouble(s2.split("!")[0])) / Double.parseDouble(s2.split("!")[0])) * 100));
                        }
                    }
                });
            }
            if (level.equals("Category")) {
                updateProfitPercentDataByCategoryMap(i + 1, tempMap, availabilityMap).forEach((s, s2) -> {
                    if (s.contains(filterString)) {
                        header.add(s);
                        values.add((int) (((Double.parseDouble(s2.split("!")[1]) - Double.parseDouble(s2.split("!")[0])) / Double.parseDouble(s2.split("!")[0])) * 100));
                    }
                });
            }
            if (level.equals("SubCategory")) {
                updateProfitPercentDataBysubCategoryMap(i + 1, tempMap, availabilityMap).forEach((s, s2) -> {
                    if (s.contains(filterString)) {
                        header.add(s);
                        values.add((int) (((Double.parseDouble(s2.split("!")[1]) - Double.parseDouble(s2.split("!")[0])) / Double.parseDouble(s2.split("!")[0])) * 100));
                    }
                });
            }
            if (level.equals("product")) {
                updateProfitPercentDataByProductMap(i + 1, tempMap, availabilityMap).forEach((s, s2) -> {
                    if (s.contains(filterString)) {
                        header.add(s);
                        values.add((int) (((Double.parseDouble(s2.split("!")[1]) - Double.parseDouble(s2.split("!")[0])) / Double.parseDouble(s2.split("!")[0])) * 100));
                    }
                });
            }
            if (i == 0) {
                profitPercentDataList.add("MONTH," + header.toString().replace("[", "").replace("]", ""));
            }
            profitPercentDataList.add((i + 1) + "," + values.toString().replace("[", "").replace("]", ""));
        }
        return profitPercentDataList;
    }

    private Map<String, String> updateProfitPercentDataByBrandMap(int monthNumber, Map<String, String> tempMap, Map<String, Set<String>> availabilityMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            String key = retailSalesEntryDto.getProductDetails() + "_" + retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory() + "_" + retailSalesEntryDto.getLeather();
            tempMap.remove("brand");
            if (tempMap.containsKey(retailSalesEntryDto.getBrand())) {
                tempMap.put(retailSalesEntryDto.getBrand(),
                        (Double.valueOf(tempMap.get(retailSalesEntryDto.getBrand()).split("!")[0]) + (Integer.valueOf(availabilityMap.get(key.replace("_NA", "")).toArray()[0].toString()) * Integer.valueOf(retailSalesEntryDto.getQuantity()))) + "!" +
                                (Double.valueOf(tempMap.get(retailSalesEntryDto.getBrand()).split("!")[1]) + Integer.valueOf(retailSalesEntryDto.getSalePrice())));
            }
        });
        return tempMap;
    }


    private Map<String, String> updateProfitPercentDataByCategoryMap(int monthNumber, Map<String, String> totalSalesDataMap, Map<String, Set<String>> availabilityMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            totalSalesDataMap.remove("brand");
            String key = retailSalesEntryDto.getProductDetails() + "_" + retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory() + "_" + retailSalesEntryDto.getLeather();
            String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
            if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1)) {
                totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1,
                        (Double.valueOf(totalSalesDataMap.get(retailSalesEntryDto.getBrand()+ "_" + c1).split("!")[0]) + (Integer.valueOf(availabilityMap.get(key.replace("_NA", "")).toArray()[0].toString()) * Integer.valueOf(retailSalesEntryDto.getQuantity()))) + "!" +
                                (Double.valueOf(totalSalesDataMap.get(retailSalesEntryDto.getBrand()+ "_" + c1).split("!")[1]) + Integer.valueOf(retailSalesEntryDto.getSalePrice())));
            }
        });
        return totalSalesDataMap;
    }

    private Map<String, String> updateProfitPercentDataBysubCategoryMap(int monthNumber, Map<String, String> totalSalesDataMap, Map<String, Set<String>> availabilityMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            totalSalesDataMap.remove("brand");
            String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
            String c2 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[1];
            if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2)) {
                totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2,
                        (Double.valueOf(totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2).split("!")[0]) + (Integer.valueOf(retailSalesEntryDto.getSalePrice()) * .30)) + "!" +
                                (Double.valueOf(totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2).split("!")[1]) + Integer.valueOf(retailSalesEntryDto.getSalePrice())));
            }
        });
        return totalSalesDataMap;
    }

    private Map<String, String> updateProfitPercentDataByProductMap(int monthNumber, Map<String, String> totalSalesDataMap, Map<String, Set<String>> availabilityMap) {
        ClassicLeathersToolApplication.salesData.get(String.valueOf(monthNumber)).forEach(retailSalesEntryDto -> {
            totalSalesDataMap.remove("brand");
            String c1 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[0];
            String c2 = ClassicLeathersToolApplication.categoryMap.get(retailSalesEntryDto.getBrand() + "_" + retailSalesEntryDto.getCategory()).replace("|", "__").split("__")[1];
            if (totalSalesDataMap.containsKey(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2)) {
                totalSalesDataMap.put(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2,

                        (Double.valueOf(totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2).split("!")[0]) + (Integer.valueOf(retailSalesEntryDto.getSalePrice()) * .30)) + "!" +
                                (Double.valueOf(totalSalesDataMap.get(retailSalesEntryDto.getBrand() + "_" + c1 + "_" + c2).split("!")[1]) + Integer.valueOf(retailSalesEntryDto.getSalePrice())));
            }
        });
        return totalSalesDataMap;
    }

}
