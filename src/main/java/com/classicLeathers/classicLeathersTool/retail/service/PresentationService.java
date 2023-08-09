package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.retail.model.Presentation;
import com.classicLeathers.classicLeathersTool.retail.model.RetailSalesEntryDto;
import com.classicLeathers.classicLeathersTool.retail.model.TotalSalesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PresentationService {
    @Autowired
    private RetailSalesReportService retailSalesReportService;

    public List<TotalSalesDto> getData(Boolean showDriving, Boolean showKora, Boolean showWaves,
                                       Boolean ShowASeries, Boolean showMirat, Boolean showHandBag,
                                       Boolean showBeltAndWallet, Boolean showNA) {
        return getTotalSalesData(buildSalesPresentationList(getSalesData()), showDriving, showKora, showWaves,
                ShowASeries, showMirat, showHandBag, showBeltAndWallet, showNA);
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

    private List<Presentation> buildSalesPresentationList(Map<String, List<RetailSalesEntryDto>> retailSalesEntryDtos) {
        List<Presentation> presentations = new ArrayList<>();
        retailSalesEntryDtos.keySet().forEach(month -> {
            Presentation drivingPresentation = new Presentation();
            drivingPresentation.setMonth(month);
            drivingPresentation.setCategory("Driving");
            Presentation koraPresentation = new Presentation();
            koraPresentation.setMonth(month);
            koraPresentation.setCategory("Kora");
            Presentation wavesPresentation = new Presentation();
            wavesPresentation.setMonth(month);
            wavesPresentation.setCategory("Waves");
            Presentation aSeriesPresentation = new Presentation();
            aSeriesPresentation.setMonth(month);
            aSeriesPresentation.setCategory("A-Series");
            Presentation miratPresentation = new Presentation();
            miratPresentation.setMonth(month);
            miratPresentation.setCategory("Mirat");
            Presentation bagsPresentation = new Presentation();
            bagsPresentation.setMonth(month);
            bagsPresentation.setCategory("Hand Bags");
            Presentation beltsWalletsPresentation = new Presentation();
            beltsWalletsPresentation.setMonth(month);
            beltsWalletsPresentation.setCategory("Belts And Wallets");
            Presentation naPresentation = new Presentation();
            naPresentation.setMonth(month);
            naPresentation.setCategory("NA");
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

    private List<TotalSalesDto> getTotalSalesData(List<Presentation> presentations,
                                                  Boolean showDriving, Boolean showKora, Boolean showWaves,
                                                  Boolean showASeries, Boolean showMirat, Boolean showHandBag,
                                                  Boolean showBeltAndWallet, Boolean showNA) {
        Map<String, TotalSalesDto> totalSalesMap = new HashMap<>();

        presentations.forEach(presentation -> {
            if (totalSalesMap.keySet().contains(presentation.getMonth())) {
                if (presentation.getCategory().contains("Driving")) {
                    totalSalesMap.get(presentation.getMonth()).setDrivingSales(showDriving ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Kora")) {
                    totalSalesMap.get(presentation.getMonth()).setKoraSales(showKora ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Waves")) {
                    totalSalesMap.get(presentation.getMonth()).setWavesSales(showWaves ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("A-Series")) {
                    totalSalesMap.get(presentation.getMonth()).setaSeriesSales(showASeries ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Mirat")) {
                    totalSalesMap.get(presentation.getMonth()).setMiratSales(showMirat ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Hand Bags")) {
                    totalSalesMap.get(presentation.getMonth()).setHandBagSales(showHandBag ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Belts And Wallets")) {
                    totalSalesMap.get(presentation.getMonth()).setBeltsAndWalletsSale(showBeltAndWallet ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("NA")) {
                    totalSalesMap.get(presentation.getMonth()).setNaSalesSale(showNA ? presentation.getTotalSales() : 0);
                }
            } else {
                TotalSalesDto totalSalesDto = new TotalSalesDto();
                totalSalesDto.setMonth(presentation.getMonth());
                if (presentation.getCategory().contains("Driving")) {
                    totalSalesDto.setDrivingSales(showDriving ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Kora")) {
                    totalSalesDto.setKoraSales(showKora ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Waves")) {
                    totalSalesDto.setWavesSales(showWaves ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("A-Series")) {
                    totalSalesDto.setaSeriesSales(showASeries ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Mirat")) {
                    totalSalesDto.setMiratSales(showMirat ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Hand Bags")) {
                    totalSalesDto.setHandBagSales(showHandBag ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("Belts And Wallets")) {
                    totalSalesDto.setBeltsAndWalletsSale(showBeltAndWallet ? presentation.getTotalSales() : 0);
                } else if (presentation.getCategory().contains("NA")) {
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
}
