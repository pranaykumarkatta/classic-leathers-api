package com.classicLeathers.classicLeathersTool.production.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.PdfUtils;
import com.classicLeathers.classicLeathersTool.production.InvalidCountException;
import com.classicLeathers.classicLeathersTool.production.model.*;
import com.classicLeathers.classicLeathersTool.retail.model.TimeSheetDto;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobCardService {
    public List<ArticleDto> getArticles(Integer sheetIndex) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\Articles\\ARTICLES.xlsx", sheetIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            List<ArticleDto> articleDtoList = new ArrayList<>();
            for (String row : rowData) {
                ArticleDto articleDto = new ArticleDto();
                String[] cellData = row.split(",");
                if (cellData.length > 1) {
                    articleDto.setSku(cellData[0]);
                    articleDto.setLeather(cellData[1]);
                    articleDto.setHandStitchingPattern(cellData[2]);
                    articleDto.setStyle(cellData[3]);
                    articleDto.setLining(cellData[4]);
                    articleDto.setSole(cellData[5]);
                    articleDtoList.add(articleDto);
                }
            }
            return articleDtoList;
        }
        return Collections.emptyList();
    }

    public List<String> getJobCardFiles() {
        File folder = new File("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards");
        File[] listOfFiles = folder.listFiles();
        List<String> fileNames = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getName());
            }
        }
        return fileNames;
    }

    public List<JobCard> getJobCardDetails(String fileName) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + fileName, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<JobCard> jobCardEntries = new ArrayList<>();
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        String[] clientData = rowData.get(0).split(",");
        rowData.remove(0);
        rowData.remove(0);

        for (String row : rowData) {
            String[] cellData = row.split(",");
            JobCard jobCardEntry = new JobCard();
            jobCardEntry.setSku(cellData[0]);
            jobCardEntry.setLeather(cellData[1]);
            jobCardEntry.setSize_40_quantity(cellData[2]);
            jobCardEntry.setSize_41_quantity(cellData[3]);
            jobCardEntry.setSize_42_quantity(cellData[4]);
            jobCardEntry.setSize_43_quantity(cellData[5]);
            jobCardEntry.setSize_44_quantity(cellData[6]);
            jobCardEntry.setSize_45_quantity(cellData[7]);
            jobCardEntry.setSize_46_quantity(cellData[8]);
            jobCardEntry.setSize_47_quantity(cellData[9]);
            jobCardEntry.setTotalQuantity(cellData[10]);
            jobCardEntry.setHandStitchingPattern(cellData[11]);
            jobCardEntry.setStyle(cellData[12]);
            jobCardEntry.setLining(cellData[13]);
            jobCardEntry.setSole(cellData[14]);
            jobCardEntry.setClient(clientData[0]);
            jobCardEntry.setBrand(clientData[1]);
            jobCardEntry.setPoDate(clientData[3]);
            jobCardEntries.add(jobCardEntry);
        }
        return jobCardEntries;
    }

    public Integer getNextJobCardNumber() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\Details\\JOB_CARD_DETAILS.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);
            List<Integer> jobCardIds = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                jobCardIds.add(Integer.valueOf(cellData[0]));
            }
            if (jobCardIds.size() > 0) {
                return jobCardIds
                        .stream()
                        .mapToInt(v -> v)
                        .max().orElseThrow(NoSuchElementException::new) + 1;
            } else {
                return 1;
            }
        }
        return 9999;
    }

    public List<String> getJobCardProgressSkus(String jobCardFileName) {

        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            rowData.remove(0);//Remove header data
            List<String> jobCardProgressSkus = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1]);
            }
            return jobCardProgressSkus;
        }
        return Collections.EMPTY_LIST;
    }

    public List<JobCardProgress> getJobCardProgressList(String jobCardFileName) {
        List<JobCardProgress> jobCardProgressDtos = new ArrayList<>();
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "CUTTING", 1));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "UPPER", 2));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "HS", 3));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "FINISHING", 4));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "PACKING", 5));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "DISPATCH", 6));
        return jobCardProgressDtos.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
    }

    private List<JobCardProgress> getJobCardProgress(String jobCardFileName, String productionStage, Integer
            sheetIndex) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, sheetIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<JobCardProgress> jobCardProgresses = new ArrayList<>();
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        rowData.remove(0);
        if (rowData.size() != 0 && !productionStage.equals("DISPATCH")) {
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData.length > 1) {
                    String count = "";
                    String size = "";
                    if (!cellData[2].equals("0")) {
                        count = cellData[2];
                        size = "40";
                    }
                    if (!cellData[3].equals("0")) {
                        count = cellData[3];
                        size = "41";
                    }
                    if (!cellData[4].equals("0")) {
                        count = cellData[4];
                        size = "42";
                    }
                    if (!cellData[5].equals("0")) {
                        count = cellData[5];
                        size = "43";
                    }
                    if (!cellData[6].equals("0")) {
                        count = cellData[6];
                        size = "44";
                    }
                    if (!cellData[7].equals("0")) {
                        count = cellData[7];
                        size = "45";
                    }
                    if (!cellData[8].equals("0")) {
                        count = cellData[8];
                        size = "46";
                    }
                    if (!cellData[9].equals("0")) {
                        count = cellData[9];
                        size = "47";
                    }
                    JobCardProgress jobCardProgressDto = new JobCardProgress();
                    jobCardProgressDto.setSku(cellData[0]);
                    jobCardProgressDto.setLeather(cellData[1]);
                    jobCardProgressDto.setSize(size);
                    jobCardProgressDto.setProductionStage(productionStage);
                    jobCardProgressDto.setCount(count);
                    jobCardProgressDto.setDate(cellData[10]);
                    jobCardProgressDto.setBatchNumber("NA");
                    jobCardProgressDto.setPackingBoxNumber("NA");
                    if (productionStage.equals("PACKING")) {
                        jobCardProgressDto.setBatchNumber(cellData[11]);
                        jobCardProgressDto.setPackingBoxNumber(cellData[12]);
                        jobCardProgressDto.setPrice(cellData[14]);
                    }
                    jobCardProgressDto.setCourierName("NA");
                    jobCardProgressDto.setTrackingNumber("NA");
                    if (!productionStage.equals("PACKING")) {
                        jobCardProgressDto.setVendor(cellData[13]);
                    }
                    jobCardProgressDto.setSearchString(jobCardProgressDto.getSearchString());
                    jobCardProgresses.add(jobCardProgressDto);
                }
            }
            return jobCardProgresses;
        } else {
            for (String row : rowData) {
                String[] cellData = row.split(",");
                JobCardProgress jobCardProgressDto = new JobCardProgress();
                jobCardProgressDto.setSku("NA");
                jobCardProgressDto.setLeather("NA");
                jobCardProgressDto.setSize("NA");
                jobCardProgressDto.setCount("NA");
                jobCardProgressDto.setBatchNumber("NA");
                jobCardProgressDto.setProductionStage(productionStage);
                jobCardProgressDto.setBatchNumber(cellData[0]);
                jobCardProgressDto.setCourierName(cellData[1]);
                jobCardProgressDto.setTrackingNumber(cellData[2]);
                jobCardProgressDto.setDate(cellData[3]);
                jobCardProgressDto.setSearchString(jobCardProgressDto.getSearchString());
                jobCardProgresses.add(jobCardProgressDto);
                return jobCardProgresses;
            }
        }
        return Collections.EMPTY_LIST;
    }

    public List<OverAllJobCardProgress> getJobCardOverAllProgressList(String jobCardFileName) {
        Map<String, ProductionStageProgressDto> orderedProgressMap = new HashMap<>();
        Map<String, ProductionStageProgressDto> cuttingProgressMap = getProductionProgressMap(jobCardFileName, 1);
        Map<String, ProductionStageProgressDto> upperProgressMap = getProductionProgressMap(jobCardFileName, 2);
        Map<String, ProductionStageProgressDto> hsProgressMap = getProductionProgressMap(jobCardFileName, 3);
        Map<String, ProductionStageProgressDto> finishingProgressMap = getProductionProgressMap(jobCardFileName, 4);
        Map<String, ProductionStageProgressDto> packingProgressMap = getProductionProgressMap(jobCardFileName, 5);
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Get data from ordered quantity
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        rowData.remove(0);
        rowData.remove(0);
        if (rowData.size() != 0) {

            for (String row : rowData) {
                ProductionStageProgressDto dto = new ProductionStageProgressDto();
                String[] cellData = row.split(",");
                dto.setSku(cellData[0]);
                dto.setLeather(cellData[1]);
                dto.setSize_40_quantity(cellData[2]);
                dto.setSize_41_quantity(cellData[3]);
                dto.setSize_42_quantity(cellData[4]);
                dto.setSize_43_quantity(cellData[5]);
                dto.setSize_44_quantity(cellData[6]);
                dto.setSize_45_quantity(cellData[7]);
                dto.setSize_46_quantity(cellData[8]);
                dto.setSize_47_quantity(cellData[9]);

                orderedProgressMap.put(dto.getSku() + "_" + dto.getLeather(), dto);
            }
        }

        List<OverAllJobCardProgress> overAllJobCardProgressList = new ArrayList<>();
        orderedProgressMap.keySet().forEach(sku -> {
            String[] skuStrings = sku.split("_");
            ProductionStageProgressDto orderDto = orderedProgressMap.get(sku) != null ? orderedProgressMap.get(sku) : new ProductionStageProgressDto();
            ProductionStageProgressDto cutDto = cuttingProgressMap.get(sku) != null ? cuttingProgressMap.get(sku) : new ProductionStageProgressDto();
            ProductionStageProgressDto upperDto = upperProgressMap.get(sku) != null ? upperProgressMap.get(sku) : new ProductionStageProgressDto();
            ProductionStageProgressDto hsDto = hsProgressMap.get(sku) != null ? hsProgressMap.get(sku) : new ProductionStageProgressDto();
            ProductionStageProgressDto finishingDto = finishingProgressMap.get(sku) != null ? finishingProgressMap.get(sku) : new ProductionStageProgressDto();
            ProductionStageProgressDto packedDto = packingProgressMap.get(sku) != null ? packingProgressMap.get(sku) : new ProductionStageProgressDto();


            OverAllJobCardProgress overAllJobCardProgress = new OverAllJobCardProgress();
            overAllJobCardProgress.setSku(skuStrings[0]);
            overAllJobCardProgress.setLeather(skuStrings[1]);
            overAllJobCardProgress.setSize_40_ordered_quantity(orderDto.getSize_40_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_40_quantity()));
            overAllJobCardProgress.setSize_40_cutting_quantity(cutDto.getSize_40_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_40_quantity()));
            overAllJobCardProgress.setSize_40_upperMaking_quantity(upperDto.getSize_40_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_40_quantity()));
            overAllJobCardProgress.setSize_40_hs_quantity(hsDto.getSize_40_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_40_quantity()));
            overAllJobCardProgress.setSize_40_finished_quantity(finishingDto.getSize_40_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_40_quantity()));
            overAllJobCardProgress.setSize_40_packed_quantity(packedDto.getSize_40_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_40_quantity()));

            overAllJobCardProgress.setSize_41_ordered_quantity(orderDto.getSize_41_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_41_quantity()));
            overAllJobCardProgress.setSize_41_cutting_quantity(cutDto.getSize_41_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_41_quantity()));
            overAllJobCardProgress.setSize_41_upperMaking_quantity(upperDto.getSize_41_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_41_quantity()));
            overAllJobCardProgress.setSize_41_hs_quantity(hsDto.getSize_41_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_41_quantity()));
            overAllJobCardProgress.setSize_41_finished_quantity(finishingDto.getSize_41_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_41_quantity()));
            overAllJobCardProgress.setSize_41_packed_quantity(packedDto.getSize_41_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_41_quantity()));

            overAllJobCardProgress.setSize_42_ordered_quantity(orderDto.getSize_42_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_42_quantity()));
            overAllJobCardProgress.setSize_42_cutting_quantity(cutDto.getSize_42_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_42_quantity()));
            overAllJobCardProgress.setSize_42_upperMaking_quantity(upperDto.getSize_42_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_42_quantity()));
            overAllJobCardProgress.setSize_42_hs_quantity(hsDto.getSize_42_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_42_quantity()));
            overAllJobCardProgress.setSize_42_finished_quantity(finishingDto.getSize_42_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_42_quantity()));
            overAllJobCardProgress.setSize_42_packed_quantity(packedDto.getSize_42_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_42_quantity()));

            overAllJobCardProgress.setSize_43_ordered_quantity(orderDto.getSize_43_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_43_quantity()));
            overAllJobCardProgress.setSize_43_cutting_quantity(cutDto.getSize_43_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_43_quantity()));
            overAllJobCardProgress.setSize_43_upperMaking_quantity(upperDto.getSize_43_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_43_quantity()));
            overAllJobCardProgress.setSize_43_hs_quantity(hsDto.getSize_43_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_43_quantity()));
            overAllJobCardProgress.setSize_43_finished_quantity(finishingDto.getSize_43_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_43_quantity()));
            overAllJobCardProgress.setSize_43_packed_quantity(packedDto.getSize_43_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_43_quantity()));


            overAllJobCardProgress.setSize_44_ordered_quantity(orderDto.getSize_44_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_44_quantity()));
            overAllJobCardProgress.setSize_44_cutting_quantity(cutDto.getSize_44_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_44_quantity()));
            overAllJobCardProgress.setSize_44_upperMaking_quantity(upperDto.getSize_44_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_44_quantity()));
            overAllJobCardProgress.setSize_44_hs_quantity(hsDto.getSize_44_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_44_quantity()));
            overAllJobCardProgress.setSize_44_finished_quantity(finishingDto.getSize_44_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_44_quantity()));
            overAllJobCardProgress.setSize_44_packed_quantity(packedDto.getSize_44_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_44_quantity()));

            overAllJobCardProgress.setSize_45_ordered_quantity(orderDto.getSize_45_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_45_quantity()));
            overAllJobCardProgress.setSize_45_cutting_quantity(cutDto.getSize_45_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_45_quantity()));
            overAllJobCardProgress.setSize_45_upperMaking_quantity(upperDto.getSize_45_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_45_quantity()));
            overAllJobCardProgress.setSize_45_hs_quantity(hsDto.getSize_45_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_45_quantity()));
            overAllJobCardProgress.setSize_45_finished_quantity(finishingDto.getSize_45_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_45_quantity()));
            overAllJobCardProgress.setSize_45_packed_quantity(packedDto.getSize_45_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_45_quantity()));

            overAllJobCardProgress.setSize_46_ordered_quantity(orderDto.getSize_46_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_46_quantity()));
            overAllJobCardProgress.setSize_46_cutting_quantity(cutDto.getSize_46_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_46_quantity()));
            overAllJobCardProgress.setSize_46_upperMaking_quantity(upperDto.getSize_46_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_46_quantity()));
            overAllJobCardProgress.setSize_46_hs_quantity(hsDto.getSize_46_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_46_quantity()));
            overAllJobCardProgress.setSize_46_finished_quantity(finishingDto.getSize_46_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_46_quantity()));
            overAllJobCardProgress.setSize_46_packed_quantity(packedDto.getSize_46_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_46_quantity()));

            overAllJobCardProgress.setSize_47_ordered_quantity(orderDto.getSize_47_quantity() == null ? 0 : Integer.parseInt(orderDto.getSize_47_quantity()));
            overAllJobCardProgress.setSize_47_cutting_quantity(cutDto.getSize_47_quantity() == null ? 0 : Integer.parseInt(cutDto.getSize_47_quantity()));
            overAllJobCardProgress.setSize_47_upperMaking_quantity(upperDto.getSize_47_quantity() == null ? 0 : Integer.parseInt(upperDto.getSize_47_quantity()));
            overAllJobCardProgress.setSize_47_hs_quantity(hsDto.getSize_47_quantity() == null ? 0 : Integer.parseInt(hsDto.getSize_47_quantity()));
            overAllJobCardProgress.setSize_47_finished_quantity(finishingDto.getSize_47_quantity() == null ? 0 : Integer.parseInt(finishingDto.getSize_47_quantity()));
            overAllJobCardProgress.setSize_47_packed_quantity(packedDto.getSize_47_quantity() == null ? 0 : Integer.parseInt(packedDto.getSize_47_quantity()));

            overAllJobCardProgress.setSearchString(overAllJobCardProgress.getSearchString());
            overAllJobCardProgressList.add(overAllJobCardProgress);

        });
        return overAllJobCardProgressList;

    }

    private Map<String, ProductionStageProgressDto> getProductionProgressMap(String jobCardFileName, int sheetIndex) {

        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, sheetIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        rowData.remove(0);
        if (rowData.size() != 0) {
            Map<String, ProductionStageProgressDto> map = new HashMap<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                String[] info = jobCardFileName.split("_");
                if (sheetIndex != 5 || (sheetIndex == 5 &&
                        ((cellData[11].contains("PO") && cellData[11].contains(info[4])) || (!cellData[11].contains("PO"))))) {
                    if (!map.keySet().contains(cellData[0] + "_" + cellData[1])) {
                        ProductionStageProgressDto productionStageProgressDto = new ProductionStageProgressDto();
                        productionStageProgressDto.setSku(cellData[0]);
                        productionStageProgressDto.setLeather(cellData[1]);
                        productionStageProgressDto.setSize_40_quantity(cellData[2]);
                        productionStageProgressDto.setSize_41_quantity(cellData[3]);
                        productionStageProgressDto.setSize_42_quantity(cellData[4]);
                        productionStageProgressDto.setSize_43_quantity(cellData[5]);
                        productionStageProgressDto.setSize_44_quantity(cellData[6]);
                        productionStageProgressDto.setSize_45_quantity(cellData[7]);
                        productionStageProgressDto.setSize_46_quantity(cellData[8]);
                        productionStageProgressDto.setSize_47_quantity(cellData[9]);
                        map.put(cellData[0] + "_" + cellData[1], productionStageProgressDto);
                    } else {
                        ProductionStageProgressDto productionStageProgressDto = map.get(cellData[0] + "_" + cellData[1]);
                        productionStageProgressDto.setSku(cellData[0]);
                        productionStageProgressDto.setLeather(cellData[1]);
                        productionStageProgressDto.setSize_40_quantity("" + ((Integer.parseInt(cellData[2])) + (Integer.parseInt(productionStageProgressDto.getSize_40_quantity()))));
                        productionStageProgressDto.setSize_41_quantity("" + ((Integer.parseInt(cellData[3])) + (Integer.parseInt(productionStageProgressDto.getSize_41_quantity()))));
                        productionStageProgressDto.setSize_42_quantity("" + ((Integer.parseInt(cellData[4])) + (Integer.parseInt(productionStageProgressDto.getSize_42_quantity()))));
                        productionStageProgressDto.setSize_43_quantity("" + ((Integer.parseInt(cellData[5])) + (Integer.parseInt(productionStageProgressDto.getSize_43_quantity()))));
                        productionStageProgressDto.setSize_44_quantity("" + ((Integer.parseInt(cellData[6])) + (Integer.parseInt(productionStageProgressDto.getSize_44_quantity()))));
                        productionStageProgressDto.setSize_45_quantity("" + ((Integer.parseInt(cellData[7])) + (Integer.parseInt(productionStageProgressDto.getSize_45_quantity()))));
                        productionStageProgressDto.setSize_46_quantity("" + ((Integer.parseInt(cellData[8])) + (Integer.parseInt(productionStageProgressDto.getSize_46_quantity()))));
                        productionStageProgressDto.setSize_47_quantity("" + ((Integer.parseInt(cellData[9])) + (Integer.parseInt(productionStageProgressDto.getSize_47_quantity()))));
                        map.put(cellData[0] + "_" + cellData[1], productionStageProgressDto);
                    }
                }
            }
            return map;
        }
        return Collections.EMPTY_MAP;
    }

    public void saveJobCard(List<JobCard> jobCardList, String jobCardNumber,
                            String customer, String brand,
                            String poNumber, String jobWorkVendor,
                            String poDate) {
        String fileName = "JOBCARD_" + jobCardNumber + "_" + customer + "_" + brand + "_" + poNumber + "_" + jobWorkVendor;
        try {
            createJobCard("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + fileName + ".xlsx",
                    customer, brand, poNumber, jobWorkVendor, poDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Object[] jobCardData = new Object[]{jobCardNumber,
                customer,
                poNumber,
                poDate
        };
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\Details\\JOB_CARD_DETAILS.xlsx", 0, jobCardData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (JobCard jobCard : jobCardList
        ) {
            Object[] data = new Object[]{jobCard.getSku(),
                    jobCard.getLeather(),
                    jobCard.getSize_40_quantity(),
                    jobCard.getSize_41_quantity(),
                    jobCard.getSize_42_quantity(),
                    jobCard.getSize_43_quantity(),
                    jobCard.getSize_44_quantity(),
                    jobCard.getSize_45_quantity(),
                    jobCard.getSize_46_quantity(),
                    jobCard.getSize_47_quantity(),
                    jobCard.getTotalQuantity(),
                    jobCard.getHandStitchingPattern(),
                    jobCard.getStyle(),
                    jobCard.getLining(),
                    jobCard.getSole()

            };
            try {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + fileName + ".xlsx", 0, data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createJobCard(String filePath, String customer, String brand,
                               String poNumber, String jobWorkVendor, String poDate) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet1 = wb.createSheet("JOB_CARD");
            createProductionProgressSheets(wb, "CUTTING");
            createProductionProgressSheets(wb, "JOB_WORK");
            createProductionProgressSheets(wb, "HS");
            createProductionProgressSheets(wb, "FINISHING");
            createProductionProgressSheets(wb, "PACKING");
            createProductionProgressSheets(wb, "DISPATCH");
            FileOutputStream fileOut = new FileOutputStream(filePath);
            wb.write(fileOut);
            fileOut.close();
        }
        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 10));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 13));
        XSSFRow row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Client : " + customer);
        Cell cell1 = row.createCell(4);
        cell1.setCellValue("Brand : " + brand);
        Cell cell2 = row.createCell(6);
        cell2.setCellValue("Po Number : " + poNumber);
        Cell cell3 = row.createCell(8);
        cell3.setCellValue("Po Date : " + poDate);
        Cell cell4 = row.createCell(11);
        cell4.setCellValue("Job Work : " + jobWorkVendor);

        inputStream.close();
        FileOutputStream out = new FileOutputStream(filePath);

        xssfWorkbook.write(out);
        out.close();
    }

    public void closeJobCard(String fileName) {
        try {
            Path temp = Files.move
                    (Paths.get("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + fileName),
                            Paths.get("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\Closed\\" + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void createProductionProgressSheets(XSSFWorkbook wb, String sheetName) {
        Sheet sheet = wb.createSheet(sheetName);
        XSSFRow row = (XSSFRow) sheet.createRow(0);
        if (sheetName.equals("DISPATCH")) {
            Cell cell0 = row.createCell(0);
            cell0.setCellValue("BATCH NUMBER");
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("COURIER NAME");
            Cell cell2 = row.createCell(2);
            cell2.setCellValue("TRACKING NUMBER");
            Cell cell3 = row.createCell(3);
            cell3.setCellValue("DATE");
        } else {
            Cell cell = row.createCell(0);
            cell.setCellValue("SKU");
            Cell cell1 = row.createCell(1);
            cell1.setCellValue("LEATHER");
            Cell cell2 = row.createCell(2);
            cell2.setCellValue("40");
            Cell cell3 = row.createCell(3);
            cell3.setCellValue("41");
            Cell cell4 = row.createCell(4);
            cell4.setCellValue("42");
            Cell cell5 = row.createCell(5);
            cell5.setCellValue("43");
            Cell cell6 = row.createCell(6);
            cell6.setCellValue("44");
            Cell cell7 = row.createCell(7);
            cell7.setCellValue("45");
            Cell cell8 = row.createCell(8);
            cell8.setCellValue("46");
            Cell cell9 = row.createCell(9);
            cell9.setCellValue("47");
            Cell cell10 = row.createCell(10);
            cell10.setCellValue("DATE");
            Cell cell11 = row.createCell(11);
            cell11.setCellValue("BATCH_NUMBER");
            Cell cell12 = row.createCell(12);
            cell12.setCellValue("BOX_NUMBER");
            Cell cell13 = row.createCell(13);
            cell13.setCellValue("VENDOR");
            Cell cell14 = row.createCell(14);
            cell14.setCellValue("PRICE");
        }
    }

    public void saveJobCardProgress(List<JobCardProgress> jobCardProgressList, String jobCardFileName) throws
            InvalidCountException {
        for (int i = 0; i < jobCardProgressList.size(); i++) {
            JobCardProgress jobCardProgress = jobCardProgressList.get(i);
            if (Integer.parseInt(jobCardProgress.getCount()) > 0) {
                if (jobCardProgress.getProductionStage().equals("PACKING"))
                    jobCardProgress = updatePrice(jobCardProgress, jobCardFileName);

                Object[] data;
                if (!jobCardProgress.getProductionStage().equals("DISPATCH")) {
                    if (jobCardFileName.contains("Self")) {
                        validateSelfJobCardProgressEntry(jobCardProgress, jobCardFileName);
                    } else {
                        validateJobCardProgressEntry(jobCardProgress, jobCardFileName);
                    }
                    if (jobCardProgress.getProductionStage().equals("PACKING"))
                        validatePackingDetails(jobCardProgress, jobCardFileName);
                    String vendor = jobCardProgress.getVendor() != null ? jobCardProgress.getVendor() : "NA";
                    if (jobCardProgress.getSize().equals("40")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                jobCardProgress.getCount(), "0", "0", "0", "0", "0", "0", "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else if (jobCardProgress.getSize().equals("41")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", jobCardProgress.getCount(), "0", "0", "0", "0", "0", "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else if (jobCardProgress.getSize().equals("42")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", "0", jobCardProgress.getCount(), "0", "0", "0", "0", "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else if (jobCardProgress.getSize().equals("43")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", "0", "0", jobCardProgress.getCount(), "0", "0", "0", "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else if (jobCardProgress.getSize().equals("44")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", "0", "0", "0", jobCardProgress.getCount(), "0", "0", "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else if (jobCardProgress.getSize().equals("45")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", "0", "0", "0", "0", jobCardProgress.getCount(), "0", "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else if (jobCardProgress.getSize().equals("46")) {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", "0", "0", "0", "0", "0", jobCardProgress.getCount(), "0",
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    } else {
                        data = new Object[]{jobCardProgress.getSku(),
                                jobCardProgress.getLeather(),
                                "0", "0", "0", "0", "0", "0", "0", jobCardProgress.getCount(),
                                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                                "", "", vendor, jobCardProgress.getPrice()
                        };
                    }
                } else {
                    validateDispatchEntry(jobCardProgress.getBatchNumber(), jobCardFileName);
                    data = new Object[]{jobCardProgress.getBatchNumber(),
                            jobCardProgress.getCourierName(),
                            jobCardProgress.getTrackingNumber(),
                            new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
                    };
                }

                try {
                    if (jobCardProgress.getProductionStage().equals("CUTTING")) {
                        new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 1, data);
                    }
                    if (jobCardProgress.getProductionStage().equals("UPPER")) {
                        new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 2, data);
                    }
                    if (jobCardProgress.getProductionStage().equals("HS")) {
                        new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 3, data);
                    }
                    if (jobCardProgress.getProductionStage().equals("FINISHING")) {
                        new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 4, data);
                    }
                    if (jobCardProgress.getProductionStage().equals("PACKING")) {
                        data[11] = jobCardProgress.getBatchNumber();
                        data[12] = jobCardProgress.getPackingBoxNumber();
                        new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 5, data);
                    }
                    if (jobCardProgress.getProductionStage().equals("DISPATCH")) {
                        new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 6, data);
                    }
                } catch (
                        Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private JobCardProgress updatePrice(JobCardProgress jobCardProgress, String jobCardFileName) {
        String fileData = "";
        Map<String, String> priceMap = new HashMap<>();

        if (jobCardFileName.contains("TeakWood")) {
            try {
                fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\Articles\\ARTICLES.xlsx", 0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            List<String> rowData = new ArrayList<>();
            rowData.addAll(Arrays.asList(fileData.split("\n")));
            if (rowData.size() != 0) {
                rowData.remove(0);//Remove header data
                priceMap = new HashMap<>();
                for (String row : rowData) {
                    String[] cellData = row.split(",");
                    if (cellData.length > 1)
                        priceMap.put(cellData[0] + "_" + cellData[1], cellData[6]);
                }
            }
            jobCardProgress.setPrice(priceMap.get(jobCardProgress.getSku() + "_" + jobCardProgress.getLeather()));
        }
        return jobCardProgress;
    }

    private void validateDispatchEntry(String batchNumber, String jobCardFileName) throws InvalidCountException {
        List<JobCardProgress> packingJobCardProgressList = getJobCardProgress(jobCardFileName, "PACKING", 5);
        Map<String, JobCardProgress> packingJobCardProgressMap = new HashMap<>();
        packingJobCardProgressList.forEach(jobCardProgress -> {
            String jobCardKey = jobCardProgress.getBatchNumber() + jobCardProgress.getPackingBoxNumber();
            if (packingJobCardProgressMap.get(jobCardKey) != null) {
                JobCardProgress existingEntry = packingJobCardProgressMap.get(jobCardKey);
                existingEntry.setCount("" + ((Integer.parseInt(existingEntry.getCount())) + Integer.parseInt(jobCardProgress.getCount())));

            } else {
                packingJobCardProgressMap.put(jobCardKey, jobCardProgress);
            }
        });
        List<String> invalidBoxDetailsList = new ArrayList<>();
        packingJobCardProgressMap.values().forEach(jobCardProgress -> {
            if (Integer.parseInt(jobCardProgress.getCount()) < 15 && batchNumber.equals(jobCardProgress.getBatchNumber())) {
                invalidBoxDetailsList.add(jobCardProgress.getPackingBoxNumber() + " : contains " + jobCardProgress.getCount() + " Pairs");
            }
        });
        if (invalidBoxDetailsList.size() > 0)
            throw new InvalidCountException("Boxes has < 15 Pairs : \n" + invalidBoxDetailsList.toString());
    }

    private void validateSelfJobCardProgressEntry(JobCardProgress jobCardProgress, String jobCardFileName) throws
            InvalidCountException {

        for (OverAllJobCardProgress overAllJobCardProgress : getJobCardOverAllProgressList(jobCardFileName)) {
            if (jobCardProgress.getSku().equals(overAllJobCardProgress.getSku()) &&
                    jobCardProgress.getLeather().equals(overAllJobCardProgress.getLeather())) {
                switch (jobCardProgress.getProductionStage()) {
                    case "CUTTING":
                        switch (jobCardProgress.getSize()) {
                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_40_ordered_quantity()
                                                - overAllJobCardProgress.getSize_40_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_ordered_quantity()
                                            - overAllJobCardProgress.getSize_40_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_41_ordered_quantity()
                                                - overAllJobCardProgress.getSize_41_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_ordered_quantity()
                                            - overAllJobCardProgress.getSize_41_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_42_ordered_quantity()
                                                - overAllJobCardProgress.getSize_42_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_ordered_quantity()
                                            - overAllJobCardProgress.getSize_42_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_43_ordered_quantity()
                                                - overAllJobCardProgress.getSize_43_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_ordered_quantity()
                                            - overAllJobCardProgress.getSize_43_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_44_ordered_quantity()
                                                - overAllJobCardProgress.getSize_44_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_ordered_quantity()
                                            - overAllJobCardProgress.getSize_44_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_45_ordered_quantity()
                                                - overAllJobCardProgress.getSize_45_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_ordered_quantity()
                                            - overAllJobCardProgress.getSize_45_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_46_ordered_quantity()
                                                - overAllJobCardProgress.getSize_46_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_ordered_quantity()
                                            - overAllJobCardProgress.getSize_46_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_47_ordered_quantity()
                                                - overAllJobCardProgress.getSize_47_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_ordered_quantity()
                                            - overAllJobCardProgress.getSize_47_cutting_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                    case "UPPER":
                        switch (jobCardProgress.getSize()) {

                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_40_cutting_quantity()
                                                - overAllJobCardProgress.getSize_40_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_cutting_quantity()
                                            - overAllJobCardProgress.getSize_40_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_41_cutting_quantity()
                                                - overAllJobCardProgress.getSize_41_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_cutting_quantity()
                                            - overAllJobCardProgress.getSize_41_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_42_cutting_quantity()
                                                - overAllJobCardProgress.getSize_42_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_cutting_quantity()
                                            - overAllJobCardProgress.getSize_42_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_43_cutting_quantity()
                                                - overAllJobCardProgress.getSize_43_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_cutting_quantity()
                                            - overAllJobCardProgress.getSize_43_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_44_cutting_quantity()
                                                - overAllJobCardProgress.getSize_44_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_cutting_quantity()
                                            - overAllJobCardProgress.getSize_44_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_45_cutting_quantity()
                                                - overAllJobCardProgress.getSize_45_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_cutting_quantity()
                                            - overAllJobCardProgress.getSize_45_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_46_cutting_quantity()
                                                - overAllJobCardProgress.getSize_46_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_cutting_quantity()
                                            - overAllJobCardProgress.getSize_46_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_47_cutting_quantity()
                                                - overAllJobCardProgress.getSize_47_upperMaking_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_cutting_quantity()
                                            - overAllJobCardProgress.getSize_47_upperMaking_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                    case "HS":
                        switch (jobCardProgress.getSize()) {

                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_40_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_40_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_40_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_41_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_41_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_41_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_42_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_42_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_42_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_43_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_43_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_43_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_44_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_44_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_44_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_45_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_45_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_45_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_46_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_46_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_46_hs_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_47_upperMaking_quantity()
                                                - overAllJobCardProgress.getSize_47_hs_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_upperMaking_quantity()
                                            - overAllJobCardProgress.getSize_47_hs_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                    case "FINISHING":
                        switch (jobCardProgress.getSize()) {

                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_40_hs_quantity()
                                                - overAllJobCardProgress.getSize_40_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_hs_quantity()
                                            - overAllJobCardProgress.getSize_40_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_41_hs_quantity()
                                                - overAllJobCardProgress.getSize_41_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_hs_quantity()
                                            - overAllJobCardProgress.getSize_41_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_42_hs_quantity()
                                                - overAllJobCardProgress.getSize_42_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_hs_quantity()
                                            - overAllJobCardProgress.getSize_42_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_43_hs_quantity()
                                                - overAllJobCardProgress.getSize_43_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_hs_quantity()
                                            - overAllJobCardProgress.getSize_43_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_44_hs_quantity()
                                                - overAllJobCardProgress.getSize_44_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_hs_quantity()
                                            - overAllJobCardProgress.getSize_44_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_45_hs_quantity()
                                                - overAllJobCardProgress.getSize_45_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_hs_quantity()
                                            - overAllJobCardProgress.getSize_45_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_46_hs_quantity()
                                                - overAllJobCardProgress.getSize_46_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_hs_quantity()
                                            - overAllJobCardProgress.getSize_46_finished_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_47_hs_quantity()
                                                - overAllJobCardProgress.getSize_47_finished_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_hs_quantity()
                                            - overAllJobCardProgress.getSize_47_finished_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                    case "PACKING":
                        switch (jobCardProgress.getSize()) {

                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_40_finished_quantity()
                                                - overAllJobCardProgress.getSize_40_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_finished_quantity()
                                            - overAllJobCardProgress.getSize_40_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_41_finished_quantity()
                                                - overAllJobCardProgress.getSize_41_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_finished_quantity()
                                            - overAllJobCardProgress.getSize_41_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_42_finished_quantity()
                                                - overAllJobCardProgress.getSize_42_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_finished_quantity()
                                            - overAllJobCardProgress.getSize_42_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_43_finished_quantity()
                                                - overAllJobCardProgress.getSize_43_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_finished_quantity()
                                            - overAllJobCardProgress.getSize_43_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_44_finished_quantity()
                                                - overAllJobCardProgress.getSize_44_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_finished_quantity()
                                            - overAllJobCardProgress.getSize_44_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_45_finished_quantity()
                                                - overAllJobCardProgress.getSize_45_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_finished_quantity()
                                            - overAllJobCardProgress.getSize_45_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_46_finished_quantity()
                                                - overAllJobCardProgress.getSize_46_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_finished_quantity()
                                            - overAllJobCardProgress.getSize_46_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_47_finished_quantity()
                                                - overAllJobCardProgress.getSize_47_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_finished_quantity()
                                            - overAllJobCardProgress.getSize_47_packed_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                }

            }
        }
    }

    private void validateJobCardProgressEntry(JobCardProgress jobCardProgress, String jobCardFileName) throws
            InvalidCountException {

        for (OverAllJobCardProgress overAllJobCardProgress : getJobCardOverAllProgressList(jobCardFileName)) {
            if (jobCardProgress.getSku().equals(overAllJobCardProgress.getSku()) &&
                    jobCardProgress.getLeather().equals(overAllJobCardProgress.getLeather())) {
                switch (jobCardProgress.getProductionStage()) {
                    case "CUTTING":
                        switch (jobCardProgress.getSize()) {
                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_40_ordered_quantity()
                                                - overAllJobCardProgress.getSize_40_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_ordered_quantity()
                                            - overAllJobCardProgress.getSize_40_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_41_ordered_quantity()
                                                - overAllJobCardProgress.getSize_41_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_ordered_quantity()
                                            - overAllJobCardProgress.getSize_41_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_42_ordered_quantity()
                                                - overAllJobCardProgress.getSize_42_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_ordered_quantity()
                                            - overAllJobCardProgress.getSize_42_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_43_ordered_quantity()
                                                - overAllJobCardProgress.getSize_43_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_ordered_quantity()
                                            - overAllJobCardProgress.getSize_43_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_44_ordered_quantity()
                                                - overAllJobCardProgress.getSize_44_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_ordered_quantity()
                                            - overAllJobCardProgress.getSize_44_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_45_ordered_quantity()
                                                - overAllJobCardProgress.getSize_45_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_ordered_quantity()
                                            - overAllJobCardProgress.getSize_45_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_46_ordered_quantity()
                                                - overAllJobCardProgress.getSize_46_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_ordered_quantity()
                                            - overAllJobCardProgress.getSize_46_cutting_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        (overAllJobCardProgress.getSize_47_ordered_quantity()
                                                - overAllJobCardProgress.getSize_47_cutting_quantity())) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_ordered_quantity()
                                            - overAllJobCardProgress.getSize_47_cutting_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                    case "PACKING":
                        switch (jobCardProgress.getSize()) {

                            case "40":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_40_cutting_quantity()
                                                - overAllJobCardProgress.getSize_40_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_40_cutting_quantity()
                                            - overAllJobCardProgress.getSize_40_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "41":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_41_cutting_quantity()
                                                - overAllJobCardProgress.getSize_41_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_41_cutting_quantity()
                                            - overAllJobCardProgress.getSize_41_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "42":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_42_cutting_quantity()
                                                - overAllJobCardProgress.getSize_42_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_42_cutting_quantity()
                                            - overAllJobCardProgress.getSize_42_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "43":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_43_cutting_quantity()
                                                - overAllJobCardProgress.getSize_43_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_43_cutting_quantity()
                                            - overAllJobCardProgress.getSize_43_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "44":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_44_cutting_quantity()
                                                - overAllJobCardProgress.getSize_44_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_44_cutting_quantity()
                                            - overAllJobCardProgress.getSize_44_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "45":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_45_cutting_quantity()
                                                - overAllJobCardProgress.getSize_45_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_45_cutting_quantity()
                                            - overAllJobCardProgress.getSize_45_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "46":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_46_cutting_quantity()
                                                - overAllJobCardProgress.getSize_46_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_46_cutting_quantity()
                                            - overAllJobCardProgress.getSize_46_packed_quantity()) + " for the selection");
                                }
                                break;
                            case "47":
                                if (Integer.parseInt(jobCardProgress.getCount()) >
                                        overAllJobCardProgress.getSize_47_cutting_quantity()
                                                - overAllJobCardProgress.getSize_47_packed_quantity()) {
                                    throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " + (overAllJobCardProgress.getSize_47_cutting_quantity()
                                            - overAllJobCardProgress.getSize_47_packed_quantity()) + " for the selection");
                                }
                                break;
                        }
                        break;
                }

            }
        }
    }

    private void validatePackingDetails(JobCardProgress jobCardProgress, String jobCardFileName) throws
            InvalidCountException {
        if (Integer.parseInt(jobCardProgress.getCount()) > 15) {
            throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= 15 for each box");
        } else {
            Map<String, Integer> boxVolumeCountMap = getPackingBoxDetails(jobCardFileName);
            String s = jobCardProgress.getBatchNumber() + "_" + jobCardProgress.getPackingBoxNumber();
            if (boxVolumeCountMap.keySet().contains(s)
                    && ((boxVolumeCountMap.get(s) + Integer.parseInt(jobCardProgress.getCount())) > 15)) {
                throw new InvalidCountException("Invalid Count for Size " + jobCardProgress.getSize() + ". Expected value <= " +
                        (15 - boxVolumeCountMap.get(s)) + " for the box " + jobCardProgress.getPackingBoxNumber() + " in batch " + jobCardProgress.getBatchNumber());
            }
        }
    }

    private Map<String, Integer> getPackingBoxDetails(String jobCardFileName) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        rowData.remove(0);
        Map<String, Integer> boxVolumeCountMap = new HashMap<>();
        if (rowData.size() != 0) {
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (!boxVolumeCountMap.keySet().contains(cellData[11] + "_" + cellData[12])) {
                    Integer unitBoxCount = 0;
                    unitBoxCount += Integer.parseInt(cellData[2]);
                    unitBoxCount += Integer.parseInt(cellData[3]);
                    unitBoxCount += Integer.parseInt(cellData[4]);
                    unitBoxCount += Integer.parseInt(cellData[5]);
                    unitBoxCount += Integer.parseInt(cellData[6]);
                    unitBoxCount += Integer.parseInt(cellData[7]);
                    unitBoxCount += Integer.parseInt(cellData[8]);
                    unitBoxCount += Integer.parseInt(cellData[9]);
                    boxVolumeCountMap.put(cellData[11] + "_" + cellData[12], unitBoxCount);
                } else {
                    Integer unitBoxCount = boxVolumeCountMap.get(cellData[11] + "_" + cellData[12]);
                    unitBoxCount += Integer.parseInt(cellData[2]);
                    unitBoxCount += Integer.parseInt(cellData[3]);
                    unitBoxCount += Integer.parseInt(cellData[4]);
                    unitBoxCount += Integer.parseInt(cellData[5]);
                    unitBoxCount += Integer.parseInt(cellData[6]);
                    unitBoxCount += Integer.parseInt(cellData[7]);
                    unitBoxCount += Integer.parseInt(cellData[8]);
                    unitBoxCount += Integer.parseInt(cellData[9]);

                    boxVolumeCountMap.put(cellData[11] + "_" + cellData[12], unitBoxCount);
                }
            }
        }
        return boxVolumeCountMap;
    }

    public List<PackingListEntry> getPackingList(String jobCardFileName) {

        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        Map<String, JobCardProgress> dispatchJobCardProgressMap = new HashMap<>();
        if (rowData.size() != 0) {
            rowData.remove(0);//Remove header data
            for (String row : rowData) {
                JobCardProgress jobCardProgress = new JobCardProgress();
                String[] cellData = row.split(",");
                jobCardProgress.setBatchNumber(cellData[0]);
                jobCardProgress.setCourierName(cellData[1]);
                jobCardProgress.setTrackingNumber(cellData[2]);
                jobCardProgress.setDate(cellData[3]);
                dispatchJobCardProgressMap.put(jobCardProgress.getBatchNumber(), jobCardProgress);
            }
        }

        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        rowData = new ArrayList<>();
        List<PackingListEntry> packingListEntryList = new ArrayList<>();
        String[] info = jobCardFileName.split("_");
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        rowData.remove(0);
        for (String row : rowData) {
            String[] cellData = row.split(",");

            PackingListEntry packingListEntry = new PackingListEntry();
            packingListEntry.setSku(cellData[0]);
            packingListEntry.setLeather(cellData[1]);
            packingListEntry.setPrice(cellData[14]);
            packingListEntry.setSize_40_quantity(cellData[2]);
            packingListEntry.setSize_41_quantity(cellData[3]);
            packingListEntry.setSize_42_quantity(cellData[4]);
            packingListEntry.setSize_43_quantity(cellData[5]);
            packingListEntry.setSize_44_quantity(cellData[6]);
            packingListEntry.setSize_45_quantity(cellData[7]);
            packingListEntry.setSize_46_quantity(cellData[8]);
            packingListEntry.setSize_47_quantity(cellData[9]);
            packingListEntry.setTotal("" + ((Integer.parseInt(cellData[2]) + (Integer.parseInt(cellData[3])) +
                    (Integer.parseInt(cellData[4])) + (Integer.parseInt(cellData[5])) + (Integer.parseInt(cellData[6])) +
                    (Integer.parseInt(cellData[7])) + (Integer.parseInt(cellData[8])) + (Integer.parseInt(cellData[9])))));
            packingListEntry.setBatchNumber(cellData[11]);
            packingListEntry.setBoxNumber(cellData[12]);
            packingListEntry.setBrand(info[3]);
            packingListEntry.setPoNumber(info[4]);
            packingListEntryList.add(packingListEntry);
        }

        Map<String, PackingListEntry> stringPackingListEntryMap = new HashMap<>();
        packingListEntryList.forEach(packingListEntry -> {
            if (stringPackingListEntryMap.get(packingListEntry.toString()) != null) {
                PackingListEntry existingEntry = stringPackingListEntryMap.get(packingListEntry.toString());
                existingEntry.setSize_40_quantity("" + ((Integer.parseInt(existingEntry.getSize_40_quantity())) + Integer.parseInt(packingListEntry.getSize_40_quantity())));
                existingEntry.setSize_41_quantity("" + ((Integer.parseInt(existingEntry.getSize_41_quantity())) + Integer.parseInt(packingListEntry.getSize_41_quantity())));
                existingEntry.setSize_42_quantity("" + ((Integer.parseInt(existingEntry.getSize_42_quantity())) + Integer.parseInt(packingListEntry.getSize_42_quantity())));
                existingEntry.setSize_43_quantity("" + ((Integer.parseInt(existingEntry.getSize_43_quantity())) + Integer.parseInt(packingListEntry.getSize_43_quantity())));
                existingEntry.setSize_44_quantity("" + ((Integer.parseInt(existingEntry.getSize_44_quantity())) + Integer.parseInt(packingListEntry.getSize_44_quantity())));
                existingEntry.setSize_45_quantity("" + ((Integer.parseInt(existingEntry.getSize_45_quantity())) + Integer.parseInt(packingListEntry.getSize_45_quantity())));
                existingEntry.setSize_46_quantity("" + ((Integer.parseInt(existingEntry.getSize_46_quantity())) + Integer.parseInt(packingListEntry.getSize_46_quantity())));
                existingEntry.setSize_47_quantity("" + ((Integer.parseInt(existingEntry.getSize_47_quantity())) + Integer.parseInt(packingListEntry.getSize_47_quantity())));
                existingEntry.setTotal("" + ((Integer.parseInt(existingEntry.getTotal())) + Integer.parseInt(packingListEntry.getTotal())));

            } else {
                stringPackingListEntryMap.put(packingListEntry.toString(), packingListEntry);
            }
        });

        List<PackingListEntry> finalPackingListEntries = new ArrayList<>(stringPackingListEntryMap.values());
        finalPackingListEntries.forEach(packingListEntry -> {
            if (dispatchJobCardProgressMap.keySet().contains(packingListEntry.getBatchNumber())) {
                packingListEntry.setCourierName(dispatchJobCardProgressMap.get(packingListEntry.getBatchNumber()).getCourierName());
                packingListEntry.setTrackingNumber(dispatchJobCardProgressMap.get(packingListEntry.getBatchNumber()).getTrackingNumber());
                packingListEntry.setShippedDate(dispatchJobCardProgressMap.get(packingListEntry.getBatchNumber()).getDate());
            }
        });

        List<PackingListEntry> sortedPackingListEntries = stringPackingListEntryMap.values().stream().sorted((o1, o2) -> new Integer(o1.getBoxNumber()).
                        compareTo(new Integer(o2.getBoxNumber()))).
                collect(Collectors.toList());
        return sortedPackingListEntries;
    }

    public List<PackingListEntry> getDispatchDetails(String jobCardFileName) {
        Map<String, PackingListEntry> stringPackingListEntryMap = new HashMap<>();
        getPackingList(jobCardFileName).forEach(packingListEntry -> {
            if (stringPackingListEntryMap.get(packingListEntry.getDispatchString()) != null) {
                PackingListEntry existingEntry = stringPackingListEntryMap.get(packingListEntry.getDispatchString());
                existingEntry.setSize_40_quantity("" + ((Integer.parseInt(existingEntry.getSize_40_quantity())) + Integer.parseInt(packingListEntry.getSize_40_quantity())));
                existingEntry.setSize_41_quantity("" + ((Integer.parseInt(existingEntry.getSize_41_quantity())) + Integer.parseInt(packingListEntry.getSize_41_quantity())));
                existingEntry.setSize_42_quantity("" + ((Integer.parseInt(existingEntry.getSize_42_quantity())) + Integer.parseInt(packingListEntry.getSize_42_quantity())));
                existingEntry.setSize_43_quantity("" + ((Integer.parseInt(existingEntry.getSize_43_quantity())) + Integer.parseInt(packingListEntry.getSize_43_quantity())));
                existingEntry.setSize_44_quantity("" + ((Integer.parseInt(existingEntry.getSize_44_quantity())) + Integer.parseInt(packingListEntry.getSize_44_quantity())));
                existingEntry.setSize_45_quantity("" + ((Integer.parseInt(existingEntry.getSize_45_quantity())) + Integer.parseInt(packingListEntry.getSize_45_quantity())));
                existingEntry.setSize_46_quantity("" + ((Integer.parseInt(existingEntry.getSize_46_quantity())) + Integer.parseInt(packingListEntry.getSize_46_quantity())));
                existingEntry.setSize_47_quantity("" + ((Integer.parseInt(existingEntry.getSize_47_quantity())) + Integer.parseInt(packingListEntry.getSize_47_quantity())));
                existingEntry.setTotal("" + ((Integer.parseInt(existingEntry.getTotal())) + Integer.parseInt(packingListEntry.getTotal())));

            } else {
                stringPackingListEntryMap.put(packingListEntry.getDispatchString(), packingListEntry);
            }
        });

        return new ArrayList<>(stringPackingListEntryMap.values());
    }


    public String exportJobCard(String fileName) {
        Table table = new Table(15);

        List<JobCard> jobCardList = getJobCardDetails(fileName);
        com.itextpdf.layout.element.Cell fileNameCell = new com.itextpdf.layout.element.Cell(1, 15);
        fileNameCell.add(new Paragraph(fileName.substring(0, fileName.length() - 5)));
        fileNameCell.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(fileNameCell);

        com.itextpdf.layout.element.Cell clientInfoCell = new com.itextpdf.layout.element.Cell(1, 5);
        clientInfoCell.add(new Paragraph(jobCardList.get(0).getClient()));
        clientInfoCell.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(clientInfoCell);
        com.itextpdf.layout.element.Cell brandInfoCell = new com.itextpdf.layout.element.Cell(1, 7);
        brandInfoCell.add(new Paragraph(jobCardList.get(0).getBrand()));
        brandInfoCell.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(brandInfoCell);
        com.itextpdf.layout.element.Cell poInfoCell = new com.itextpdf.layout.element.Cell(1, 3);
        poInfoCell.add(new Paragraph(jobCardList.get(0).getPoDate()));
        poInfoCell.setTextAlignment(TextAlignment.CENTER);
        table.addHeaderCell(poInfoCell);

        table.addHeaderCell("SKU");
        table.addHeaderCell("Leather");
        table.addHeaderCell("40");
        table.addHeaderCell("41");
        table.addHeaderCell("42");
        table.addHeaderCell("43");
        table.addHeaderCell("44");
        table.addHeaderCell("45");
        table.addHeaderCell("46");
        table.addHeaderCell("47");
        table.addHeaderCell("Total");
        table.addHeaderCell("HS");
        table.addHeaderCell("Style");
        table.addHeaderCell("Lining");
        table.addHeaderCell("Sole");
        table.setTextAlignment(TextAlignment.CENTER);

        jobCardList.forEach(timeSheetDto -> {
            table.addCell(timeSheetDto.getSku());
            table.addCell(timeSheetDto.getLeather());
            table.addCell(timeSheetDto.getSize_40_quantity());
            table.addCell(timeSheetDto.getSize_41_quantity());
            table.addCell(timeSheetDto.getSize_42_quantity());
            table.addCell(timeSheetDto.getSize_43_quantity());
            table.addCell(timeSheetDto.getSize_44_quantity());
            table.addCell(timeSheetDto.getSize_45_quantity());
            table.addCell(timeSheetDto.getSize_46_quantity());
            table.addCell(timeSheetDto.getSize_47_quantity());
            table.addCell(timeSheetDto.getTotalQuantity());
            table.addCell(timeSheetDto.getHandStitchingPattern());
            table.addCell(timeSheetDto.getStyle());
            table.addCell(timeSheetDto.getLining());
            table.addCell(timeSheetDto.getSole());
        });


        return new PdfUtils("D:\\JobCards\\" + fileName.substring(0, fileName.length() - 5)).SaveAsPdf(table);

    }

    public String exportPackingList(String fileName) {
        Table table = new Table(18);

        List<PackingListEntry> packingListEntryList = getPackingList(fileName);
        table.addHeaderCell("Box Number");
        table.addHeaderCell("Brand");
        table.addHeaderCell("PO Number");
        table.addHeaderCell("SKU");
        table.addHeaderCell("Leather");
        table.addHeaderCell("Price");
        table.addHeaderCell("40");
        table.addHeaderCell("41");
        table.addHeaderCell("42");
        table.addHeaderCell("43");
        table.addHeaderCell("44");
        table.addHeaderCell("45");
        table.addHeaderCell("46");
        table.addHeaderCell("47");
        table.addHeaderCell("Total");
        table.addHeaderCell("Courier Name");
        table.addHeaderCell("Tracking Number");
        table.addHeaderCell("Dispatch Date");
        table.setTextAlignment(TextAlignment.CENTER);

        String courierName = "";
        String trackingNumber = "";
        String dispatchDate = "";
        for (int i = 0; i < packingListEntryList.size(); i++) {
            PackingListEntry p = packingListEntryList.get(i);
            if ((!p.getBatchNumber().contains("PO")) && (
                    courierName.equals("") || trackingNumber.equals("") || dispatchDate.equals(""))) {
                courierName = p.getCourierName();
                trackingNumber = p.getTrackingNumber();
                dispatchDate = p.getShippedDate();
            }
        }
        for (int i = 0; i < packingListEntryList.size(); i++) {
            PackingListEntry packingListEntry = packingListEntryList.get(i);
            if (!(packingListEntry.getBatchNumber().contains("PO") && fileName.contains(packingListEntry.getBatchNumber().split("_")[1]))) {
                table.addCell(packingListEntry.getBoxNumber());
                table.addCell(packingListEntry.getBrand());
                if (!packingListEntry.getBatchNumber().contains("PO")) {
                    table.addCell(packingListEntry.getPoNumber());
                } else {
                    table.addCell(packingListEntry.getBatchNumber().split("_")[1]);
                }
                table.addCell(packingListEntry.getSku());
                table.addCell(packingListEntry.getLeather());
                table.addCell(packingListEntry.getPrice());
                table.addCell(packingListEntry.getSize_40_quantity());
                table.addCell(packingListEntry.getSize_41_quantity());
                table.addCell(packingListEntry.getSize_42_quantity());
                table.addCell(packingListEntry.getSize_43_quantity());
                table.addCell(packingListEntry.getSize_44_quantity());
                table.addCell(packingListEntry.getSize_45_quantity());
                table.addCell(packingListEntry.getSize_46_quantity());
                table.addCell(packingListEntry.getSize_47_quantity());
                table.addCell(packingListEntry.getTotal());
                if (!packingListEntry.getBatchNumber().contains("PO")) {
                    table.addCell(packingListEntry.getCourierName() != null ? packingListEntry.getCourierName() : "");
                    table.addCell(packingListEntry.getTrackingNumber() != null ? packingListEntry.getTrackingNumber() : "");
                    table.addCell(packingListEntry.getShippedDate() != null ? packingListEntry.getShippedDate() : "");
                } else {
                    table.addCell(courierName);
                    table.addCell(trackingNumber);
                    table.addCell(dispatchDate);
                }
            }
        }

        return new PdfUtils("D:\\PackingList\\" + new SimpleDateFormat("ddMMMyyhhmma").format(new Date()) + "_" + fileName.substring(0, fileName.length() - 5)).SaveAsPdf(table);

    }


}
