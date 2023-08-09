package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.retail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PresentationService {
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public List<TotalSalesDto> getTotalSalesData(Boolean showDriving, Boolean showKora, Boolean showWaves,
                                                 Boolean ShowASeries, Boolean showMirat, Boolean showHandBag,
                                                 Boolean showBeltAndWallet, Boolean showNA) {
        return getTotalSalesDTOData(buildTotalSalesPresentationList(getSalesData()), showDriving, showKora, showWaves,
                ShowASeries, showMirat, showHandBag, showBeltAndWallet, showNA);
    }

    public List<HourlySalesDto> getHourlySalesData(Boolean showHour8Data, Boolean showHour9Data, Boolean showHour10Data, Boolean showHour11Data,
                                                   Boolean showHour12Data, Boolean showHour13Data, Boolean showHour14Data, Boolean showHour15Data,
                                                   Boolean showHour16Data, Boolean showHour17Data, Boolean showHour18Data, Boolean showHour19Data,
                                                   Boolean showHour20Data, Boolean showHour21Data, Boolean showHour22Data, Boolean showHour00Data) {
        return getHourlySalesDTOData(buildHourlySalesPresentationList(getSalesData()), showHour8Data, showHour9Data, showHour10Data, showHour11Data,
                showHour12Data, showHour13Data, showHour14Data, showHour15Data, showHour16Data, showHour17Data, showHour18Data, showHour19Data,
                showHour20Data, showHour21Data, showHour22Data, showHour00Data);
    }

    public List<HourlyStepInDto> getHourlyStepInData(Boolean showHour8Data, Boolean showHour9Data, Boolean showHour10Data, Boolean showHour11Data,
                                                     Boolean showHour12Data, Boolean showHour13Data, Boolean showHour14Data, Boolean showHour15Data,
                                                     Boolean showHour16Data, Boolean showHour17Data, Boolean showHour18Data, Boolean showHour19Data,
                                                     Boolean showHour20Data, Boolean showHour21Data, Boolean showHour22Data, Boolean showHour00Data) {
        return getHourlyStepInDTOData(buildHourlySalesPresentationList(getSalesData()), showHour8Data, showHour9Data, showHour10Data, showHour11Data,
                showHour12Data, showHour13Data, showHour14Data, showHour15Data, showHour16Data, showHour17Data, showHour18Data, showHour19Data,
                showHour20Data, showHour21Data, showHour22Data, showHour00Data);
    }

    private Map<String, List<RetailSalesEntryDto>> getSalesData() {
        Map<String, List<RetailSalesEntryDto>> retailSalesEntryDtos = new HashMap<>();
        //update i=0 from next year
        for (int i = 0; i < 12; i++) {
            if (i <= (((Integer.parseInt(new SimpleDateFormat("MM").format(new Date())))) - 4) % 12)
                retailSalesEntryDtos.put((i + 4) + "_" + new DateFormatSymbols().getMonths()[i + 3], retailSalesReportService.getSalesDataByMonth(i));
        }
        return retailSalesEntryDtos;
    }

    private List<Presentation> buildTotalSalesPresentationList(Map<String, List<RetailSalesEntryDto>> retailSalesEntryDtos) {
        List<Presentation> presentations = new ArrayList<>();
        retailSalesEntryDtos.keySet().forEach(month -> {
            Presentation drivingPresentation = new Presentation();
            drivingPresentation.setMonth(month);
            drivingPresentation.setTotalSalesCategory("Driving");
            Presentation koraPresentation = new Presentation();
            koraPresentation.setMonth(month);
            koraPresentation.setTotalSalesCategory("Kora");
            Presentation wavesPresentation = new Presentation();
            wavesPresentation.setMonth(month);
            wavesPresentation.setTotalSalesCategory("Waves");
            Presentation aSeriesPresentation = new Presentation();
            aSeriesPresentation.setMonth(month);
            aSeriesPresentation.setTotalSalesCategory("A-Series");
            Presentation miratPresentation = new Presentation();
            miratPresentation.setMonth(month);
            miratPresentation.setTotalSalesCategory("Mirat");
            Presentation bagsPresentation = new Presentation();
            bagsPresentation.setMonth(month);
            bagsPresentation.setTotalSalesCategory("Hand Bags");
            Presentation beltsWalletsPresentation = new Presentation();
            beltsWalletsPresentation.setMonth(month);
            beltsWalletsPresentation.setTotalSalesCategory("Belts And Wallets");
            Presentation naPresentation = new Presentation();
            naPresentation.setMonth(month);
            naPresentation.setTotalSalesCategory("NA");
            retailSalesEntryDtos.get(month).forEach(retailSalesEntryDto -> {
                if (retailSalesEntryDto.getCategory().contains("LF")) {
                    drivingPresentation.setTotalSales(drivingPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else if (retailSalesEntryDto.getCategory().contains("KORA")) {
                    koraPresentation.setTotalSales(koraPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else if (retailSalesEntryDto.getCategory().contains("WAVES")) {
                    wavesPresentation.setTotalSales(wavesPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else if (retailSalesEntryDto.getCategory().contains("A_0")) {
                    aSeriesPresentation.setTotalSales(aSeriesPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else if (retailSalesEntryDto.getCategory().contains("MIRAT")) {
                    miratPresentation.setTotalSales(miratPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else if (retailSalesEntryDto.getCategory().contains("_BAG") || retailSalesEntryDto.getCategory().contains("LHB")) {
                    bagsPresentation.setTotalSales(bagsPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else if (retailSalesEntryDto.getCategory().contains("BLT") || retailSalesEntryDto.getCategory().contains("WLT")) {
                    beltsWalletsPresentation.setTotalSales(beltsWalletsPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                } else {
                    naPresentation.setTotalSales(naPresentation.getTotalSales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                }
            });
            presentations.add(drivingPresentation);
            presentations.add(koraPresentation);
            presentations.add(wavesPresentation);
            presentations.add(aSeriesPresentation);
            presentations.add(miratPresentation);
            presentations.add(bagsPresentation);
            presentations.add(bagsPresentation);
            presentations.add(beltsWalletsPresentation);
            presentations.add(naPresentation);
        });
        return presentations;
    }

    private List<TotalSalesDto> getTotalSalesDTOData(List<Presentation> presentations,
                                                     Boolean showDriving, Boolean showKora, Boolean showWaves,
                                                     Boolean showASeries, Boolean showMirat, Boolean showHandBag,
                                                     Boolean showBeltAndWallet, Boolean showNA) {
        Map<String, TotalSalesDto> totalSalesMap = new HashMap<>();

        presentations.forEach(presentation -> {
            if (totalSalesMap.keySet().contains(presentation.getMonth())) {
                if (presentation.getTotalSalesCategory().contains("Driving")) {
                    totalSalesMap.get(presentation.getMonth()).setDrivingSales(showDriving ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Kora")) {
                    totalSalesMap.get(presentation.getMonth()).setKoraSales(showKora ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Waves")) {
                    totalSalesMap.get(presentation.getMonth()).setWavesSales(showWaves ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("A-Series")) {
                    totalSalesMap.get(presentation.getMonth()).setaSeriesSales(showASeries ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Mirat")) {
                    totalSalesMap.get(presentation.getMonth()).setMiratSales(showMirat ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Hand Bags")) {
                    totalSalesMap.get(presentation.getMonth()).setHandBagSales(showHandBag ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Belts And Wallets")) {
                    totalSalesMap.get(presentation.getMonth()).setBeltsAndWalletsSale(showBeltAndWallet ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("NA")) {
                    totalSalesMap.get(presentation.getMonth()).setNaSalesSale(showNA ? presentation.getTotalSales() : 0);
                }
            } else {
                TotalSalesDto totalSalesDto = new TotalSalesDto();
                totalSalesDto.setMonth(presentation.getMonth());
                if (presentation.getTotalSalesCategory().contains("Driving")) {
                    totalSalesDto.setDrivingSales(showDriving ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Kora")) {
                    totalSalesDto.setKoraSales(showKora ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Waves")) {
                    totalSalesDto.setWavesSales(showWaves ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("A-Series")) {
                    totalSalesDto.setaSeriesSales(showASeries ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Mirat")) {
                    totalSalesDto.setMiratSales(showMirat ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Hand Bags")) {
                    totalSalesDto.setHandBagSales(showHandBag ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("Belts And Wallets")) {
                    totalSalesDto.setBeltsAndWalletsSale(showBeltAndWallet ? presentation.getTotalSales() : 0);
                } else if (presentation.getTotalSalesCategory().contains("NA")) {
                    totalSalesDto.setNaSalesSale(showNA ? presentation.getTotalSales() : 0);
                }
                totalSalesMap.put(presentation.getMonth(), totalSalesDto);
            }
        });
        List<TotalSalesDto> totalSalesList = new ArrayList<>(totalSalesMap.values());

        totalSalesList.sort((o1, o2)
                -> o1.getMonth().compareTo(
                o2.getMonth()));
        return totalSalesList;
    }

    private List<Presentation> buildHourlySalesPresentationList(Map<String, List<RetailSalesEntryDto>> retailSalesEntryDtos) {
        List<Presentation> presentations = new ArrayList<>();
        retailSalesEntryDtos.keySet().forEach(month -> {
            Presentation hour_8_presentation = new Presentation();
            hour_8_presentation.setMonth(month);
            hour_8_presentation.setHour(8);
            Presentation hour_9_presentation = new Presentation();
            hour_9_presentation.setMonth(month);
            hour_9_presentation.setHour(9);
            Presentation hour_10_presentation = new Presentation();
            hour_10_presentation.setMonth(month);
            hour_10_presentation.setHour(10);
            Presentation hour_11_presentation = new Presentation();
            hour_11_presentation.setMonth(month);
            hour_11_presentation.setHour(11);
            Presentation hour_12_presentation = new Presentation();
            hour_12_presentation.setMonth(month);
            hour_12_presentation.setHour(12);
            Presentation hour_13_presentation = new Presentation();
            hour_13_presentation.setMonth(month);
            hour_13_presentation.setHour(13);
            Presentation hour_14_presentation = new Presentation();
            hour_14_presentation.setMonth(month);
            hour_14_presentation.setHour(14);
            Presentation hour_15_presentation = new Presentation();
            hour_15_presentation.setMonth(month);
            hour_15_presentation.setHour(15);
            Presentation hour_16_presentation = new Presentation();
            hour_16_presentation.setMonth(month);
            hour_16_presentation.setHour(16);
            Presentation hour_17_presentation = new Presentation();
            hour_17_presentation.setMonth(month);
            hour_17_presentation.setHour(17);
            Presentation hour_18_presentation = new Presentation();
            hour_18_presentation.setMonth(month);
            hour_18_presentation.setHour(18);
            Presentation hour_19_presentation = new Presentation();
            hour_19_presentation.setMonth(month);
            hour_19_presentation.setHour(19);
            Presentation hour_20_presentation = new Presentation();
            hour_20_presentation.setMonth(month);
            hour_20_presentation.setHour(20);
            Presentation hour_21_presentation = new Presentation();
            hour_21_presentation.setMonth(month);
            hour_21_presentation.setHour(21);
            Presentation hour_22_presentation = new Presentation();
            hour_22_presentation.setMonth(month);
            hour_22_presentation.setHour(22);
            Presentation hour_0_presentation = new Presentation();
            hour_0_presentation.setMonth(month);
            hour_0_presentation.setHour(0);
            retailSalesEntryDtos.get(month).forEach(retailSalesEntryDto -> {
                Date salesDate;
                try {
                    salesDate = (new SimpleDateFormat("MMM-d-yyyy h:mm a").parse(retailSalesEntryDto.getSaleDate()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Integer hour = Integer.valueOf((new SimpleDateFormat("kk").format(salesDate)));
                if (hour == 8) {
                    hour_8_presentation.setTotalHourlySales(hour_8_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_8_presentation.setHourlyStepInCount(hour_8_presentation.getHourlyStepInCount()+1);
                } else if (hour == 9) {
                    hour_9_presentation.setTotalHourlySales(hour_9_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_9_presentation.setHourlyStepInCount(hour_9_presentation.getHourlyStepInCount()+1);
                } else if (hour == 10) {
                    hour_10_presentation.setTotalHourlySales(hour_10_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_10_presentation.setHourlyStepInCount(hour_10_presentation.getHourlyStepInCount()+1);
                } else if (hour == 11) {
                    hour_11_presentation.setTotalHourlySales(hour_11_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_11_presentation.setHourlyStepInCount(hour_11_presentation.getHourlyStepInCount()+1);
                } else if (hour == 12) {
                    hour_12_presentation.setTotalHourlySales(hour_12_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_12_presentation.setHourlyStepInCount(hour_12_presentation.getHourlyStepInCount()+1);
                } else if (hour == 13) {
                    hour_13_presentation.setTotalHourlySales(hour_13_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_13_presentation.setHourlyStepInCount(hour_13_presentation.getHourlyStepInCount()+1);
                } else if (hour == 14) {
                    hour_14_presentation.setTotalHourlySales(hour_14_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_14_presentation.setHourlyStepInCount(hour_14_presentation.getHourlyStepInCount()+1);
                } else if (hour == 15) {
                    hour_15_presentation.setTotalHourlySales(hour_15_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_15_presentation.setHourlyStepInCount(hour_15_presentation.getHourlyStepInCount()+1);
                } else if (hour == 16) {
                    hour_16_presentation.setTotalHourlySales(hour_16_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_16_presentation.setHourlyStepInCount(hour_16_presentation.getHourlyStepInCount()+1);
                } else if (hour == 17) {
                    hour_17_presentation.setTotalHourlySales(hour_17_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_17_presentation.setHourlyStepInCount(hour_17_presentation.getHourlyStepInCount()+1);
                } else if (hour == 18) {
                    hour_18_presentation.setTotalHourlySales(hour_18_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_18_presentation.setHourlyStepInCount(hour_18_presentation.getHourlyStepInCount()+1);
                } else if (hour == 19) {
                    hour_19_presentation.setTotalHourlySales(hour_19_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_19_presentation.setHourlyStepInCount(hour_19_presentation.getHourlyStepInCount()+1);
                } else if (hour == 20) {
                    hour_20_presentation.setTotalHourlySales(hour_20_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_20_presentation.setHourlyStepInCount(hour_20_presentation.getHourlyStepInCount()+1);
                } else if (hour == 21) {
                    hour_21_presentation.setTotalHourlySales(hour_21_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_21_presentation.setHourlyStepInCount(hour_21_presentation.getHourlyStepInCount()+1);
                } else if (hour == 22) {
                    hour_22_presentation.setTotalHourlySales(hour_22_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_22_presentation.setHourlyStepInCount(hour_22_presentation.getHourlyStepInCount()+1);
                } else {
                    hour_0_presentation.setTotalHourlySales(hour_0_presentation.getTotalHourlySales()
                            + Integer.parseInt(retailSalesEntryDto.getSalePrice()));
                    hour_0_presentation.setHourlyStepInCount(hour_0_presentation.getHourlyStepInCount()+1);                }
            });
            presentations.add(hour_8_presentation);
            presentations.add(hour_9_presentation);
            presentations.add(hour_10_presentation);
            presentations.add(hour_11_presentation);
            presentations.add(hour_12_presentation);
            presentations.add(hour_13_presentation);
            presentations.add(hour_14_presentation);
            presentations.add(hour_15_presentation);
            presentations.add(hour_16_presentation);
            presentations.add(hour_17_presentation);
            presentations.add(hour_18_presentation);
            presentations.add(hour_19_presentation);
            presentations.add(hour_20_presentation);
            presentations.add(hour_21_presentation);
            presentations.add(hour_22_presentation);
            presentations.add(hour_0_presentation);
        });
        return presentations;
    }

    private List<HourlySalesDto> getHourlySalesDTOData(List<Presentation> presentations, Boolean showHour8Data, Boolean showHour9Data, Boolean showHour10Data, Boolean showHour11Data,
                                                       Boolean showHour12Data, Boolean showHour13Data, Boolean showHour14Data, Boolean showHour15Data,
                                                       Boolean showHour16Data, Boolean showHour17Data, Boolean showHour18Data, Boolean showHour19Data,
                                                       Boolean showHour20Data, Boolean showHour21Data, Boolean showHour22Data, Boolean showHour00Data) {
        Map<String, HourlySalesDto> hourlySalesDtoMap = new HashMap<>();

        presentations.forEach(presentation -> {
            if (hourlySalesDtoMap.keySet().contains(presentation.getMonth())) {
                if (presentation.getHour().equals(8)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_8_Sales(showHour8Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(9)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_9_Sales(showHour9Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(10)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_10_Sales(showHour10Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(11)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_11_Sales(showHour11Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(12)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_12_Sales(showHour12Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(13)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_13_Sales(showHour13Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(14)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_14_Sales(showHour14Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(15)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_15_Sales(showHour15Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(16)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_16_Sales(showHour16Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(17)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_17_Sales(showHour17Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(18)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_18_Sales(showHour18Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(19)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_19_Sales(showHour19Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(20)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_20_Sales(showHour20Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(21)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_21_Sales(showHour21Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(22)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_22_Sales(showHour22Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(0)) {
                    hourlySalesDtoMap.get(presentation.getMonth()).setHour_0_Sales(showHour00Data?presentation.getTotalHourlySales():0);
                }
            } else {
                HourlySalesDto hourlySalesDto = new HourlySalesDto();
                hourlySalesDto.setMonth(presentation.getMonth());
                if (presentation.getHour().equals(8)) {
                    hourlySalesDto.setHour_8_Sales(showHour8Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(9)) {
                    hourlySalesDto.setHour_9_Sales(showHour9Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(10)) {
                    hourlySalesDto.setHour_10_Sales(showHour10Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(11)) {
                    hourlySalesDto.setHour_11_Sales(showHour11Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(12)) {
                    hourlySalesDto.setHour_12_Sales(showHour12Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(13)) {
                    hourlySalesDto.setHour_13_Sales(showHour13Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(14)) {
                    hourlySalesDto.setHour_14_Sales(showHour14Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(15)) {
                    hourlySalesDto.setHour_15_Sales(showHour15Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(16)) {
                    hourlySalesDto.setHour_16_Sales(showHour16Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(17)) {
                    hourlySalesDto.setHour_17_Sales(showHour17Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(18)) {
                    hourlySalesDto.setHour_18_Sales(showHour18Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(19)) {
                    hourlySalesDto.setHour_19_Sales(showHour19Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(20)) {
                    hourlySalesDto.setHour_20_Sales(showHour20Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(21)) {
                    hourlySalesDto.setHour_21_Sales(showHour21Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(22)) {
                    hourlySalesDto.setHour_22_Sales(showHour22Data?presentation.getTotalHourlySales():0);
                } else if (presentation.getHour().equals(0)) {
                    hourlySalesDto.setHour_0_Sales(showHour00Data?presentation.getTotalHourlySales():0);
                }
                hourlySalesDtoMap.put(presentation.getMonth(), hourlySalesDto);
            }
        });
        List<HourlySalesDto> hourlySalesList = new ArrayList<>(hourlySalesDtoMap.values());

        hourlySalesList.sort((o1, o2)
                -> o1.getMonth().compareTo(
                o2.getMonth()));
        return hourlySalesList;
    }
 private List<HourlyStepInDto> getHourlyStepInDTOData(List<Presentation> presentations, Boolean showHour8Data, Boolean showHour9Data, Boolean showHour10Data, Boolean showHour11Data,
                                                       Boolean showHour12Data, Boolean showHour13Data, Boolean showHour14Data, Boolean showHour15Data,
                                                       Boolean showHour16Data, Boolean showHour17Data, Boolean showHour18Data, Boolean showHour19Data,
                                                       Boolean showHour20Data, Boolean showHour21Data, Boolean showHour22Data, Boolean showHour00Data) {
        Map<String, HourlyStepInDto> hourlyStepInDtoHashMap = new HashMap<>();

        presentations.forEach(presentation -> {
            if (hourlyStepInDtoHashMap.keySet().contains(presentation.getMonth())) {
                if (presentation.getHour().equals(8)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_8_count(showHour8Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(9)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_9_count(showHour9Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(10)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_10_count(showHour10Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(11)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_11_count(showHour11Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(12)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_12_count(showHour12Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(13)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_13_count(showHour13Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(14)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_14_count(showHour14Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(15)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_15_count(showHour15Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(16)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_16_count(showHour16Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(17)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_17_count(showHour17Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(18)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_18_count(showHour18Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(19)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_19_count(showHour19Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(20)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_20_count(showHour20Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(21)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_21_count(showHour21Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(22)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_22_count(showHour22Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(0)) {
                    hourlyStepInDtoHashMap.get(presentation.getMonth()).setHour_0_count(showHour00Data?presentation.getHourlyStepInCount():0);
                }
            } else {
                HourlyStepInDto hourlyStepInDto = new HourlyStepInDto();
                hourlyStepInDto.setMonth(presentation.getMonth());
                if (presentation.getHour().equals(8)) {
                    hourlyStepInDto.setHour_8_count(showHour8Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(9)) {
                    hourlyStepInDto.setHour_9_count(showHour9Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(10)) {
                    hourlyStepInDto.setHour_10_count(showHour10Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(11)) {
                    hourlyStepInDto.setHour_11_count(showHour11Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(12)) {
                    hourlyStepInDto.setHour_12_count(showHour12Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(13)) {
                    hourlyStepInDto.setHour_13_count(showHour13Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(14)) {
                    hourlyStepInDto.setHour_14_count(showHour14Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(15)) {
                    hourlyStepInDto.setHour_15_count(showHour15Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(16)) {
                    hourlyStepInDto.setHour_16_count(showHour16Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(17)) {
                    hourlyStepInDto.setHour_17_count(showHour17Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(18)) {
                    hourlyStepInDto.setHour_18_count(showHour18Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(19)) {
                    hourlyStepInDto.setHour_19_count(showHour19Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(20)) {
                    hourlyStepInDto.setHour_20_count(showHour20Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(21)) {
                    hourlyStepInDto.setHour_21_count(showHour21Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(22)) {
                    hourlyStepInDto.setHour_22_count(showHour22Data?presentation.getHourlyStepInCount():0);
                } else if (presentation.getHour().equals(0)) {
                    hourlyStepInDto.setHour_0_count(showHour00Data?presentation.getHourlyStepInCount():0);
                }
                hourlyStepInDtoHashMap.put(presentation.getMonth(), hourlyStepInDto);
            }
        });
        List<HourlyStepInDto> hourlyStepInDtos = new ArrayList<>(hourlyStepInDtoHashMap.values());

     hourlyStepInDtos.sort((o1, o2)
                -> o1.getMonth().compareTo(
                o2.getMonth()));
        return hourlyStepInDtos;
    }

}
