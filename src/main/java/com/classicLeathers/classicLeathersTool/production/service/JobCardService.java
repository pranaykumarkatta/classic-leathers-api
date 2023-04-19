package com.classicLeathers.classicLeathersTool.production.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.production.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
                articleDto.setSku(cellData[0]);
                articleDto.setLeather(cellData[1]);
                articleDto.setHandStitchingPattern(cellData[2]);
                articleDto.setStyle(cellData[3]);
                articleDto.setLining(cellData[4]);
                articleDto.setSole(cellData[5]);
                articleDtoList.add(articleDto);
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

    public Integer getNextJobCardNumber() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\JOB_CARD_DETAILS.xlsx", 0);
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
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_40");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_41");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_42");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_43");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_44");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_45");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_46");
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1] + "_47");
            }
            return jobCardProgressSkus;
        }
        return Collections.EMPTY_LIST;
    }

    public List<JobCardProgressDto> getJobCardProgressList(String jobCardFileName) {

        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            List<JobCardProgressDto> jobCardProgresses = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData.length > 1) {
                    String count="";
                    String size="";
                    if (!cellData[2].equals("0")){
                        count=cellData[2];
                        size="40";
                    }if (!cellData[3].equals("0")){
                        count=cellData[3];
                        size="41";
                    }if (!cellData[4].equals("0")){
                        count=cellData[4];
                        size="42";
                    }if (!cellData[5].equals("0")){
                        count=cellData[5];
                        size="43";
                    }if (!cellData[6].equals("0")){
                        count=cellData[6];
                        size="44";
                    }if (!cellData[7].equals("0")){
                        count=cellData[7];
                        size="45";
                    }if (!cellData[8].equals("0")){
                        count=cellData[8];
                        size="46";
                    }if (!cellData[9].equals("0")){
                        count=cellData[9];
                        size="47";
                    }
                    JobCardProgressDto jobCardProgressDto = new JobCardProgressDto();
                    jobCardProgressDto.setSku(cellData[0]+"_"+cellData[1]+"_"+size);
                    jobCardProgressDto.setProductionStage(cellData[10]);
                    jobCardProgressDto.setCount(count);
                    jobCardProgressDto.setDate(cellData[11]);
                    jobCardProgresses.add(jobCardProgressDto);
                }
            }
            return jobCardProgresses;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ProductionProgressDto> getJobCardOverAllProgressList(String jobCardFileName) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<ProductionProgressDto> dtoList = new ArrayList<>();

        //Get data from ordered quantity
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        rowData.remove(0);
        rowData.remove(0);
        if (rowData.size() != 0) {
            JobCardSizes jobCardSizes = new JobCardSizes();
            for (String row : rowData) {
                ProductionProgressDto dto = new ProductionProgressDto();
                String[] cellData = row.split(",");
                dto.setSku(cellData[0]);
                dto.setLeather(cellData[1]);
                dto.setIsProductionProgressEntry("No");

                jobCardSizes.setSize_40_quantity(cellData[2]);
                jobCardSizes.setSize_41_quantity(cellData[3]);
                jobCardSizes.setSize_42_quantity(cellData[4]);
                jobCardSizes.setSize_43_quantity(cellData[5]);
                jobCardSizes.setSize_44_quantity(cellData[6]);
                jobCardSizes.setSize_45_quantity(cellData[7]);
                jobCardSizes.setSize_46_quantity(cellData[8]);
                jobCardSizes.setSize_47_quantity(cellData[9]);
                jobCardSizes.setTotalQuantity(cellData[10]);

                Map<String, JobCardSizes> map = new HashMap<>();
                map.put("CUTTING", jobCardSizes);
                map.put("JOB_WORK", jobCardSizes);
                map.put("HS", jobCardSizes);
                map.put("FINISHING", jobCardSizes);
                map.put("PACKING", jobCardSizes);
                map.put("DISPATCH", jobCardSizes);

                dto.setProductionStageSizeList(map);
                dtoList.add(dto);
            }
        }
        //TODO
        return dtoList;
    }

    public void saveJobCard(List<JobCard> jobCardList, String jobCardNumber,
                            String customer, String brand,
                            String poNumber, String jobWorkVendor,
                            String poDate) {
        String fileName = "JOBCARD_" + jobCardNumber + "_" + customer + "_" + poNumber;
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
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\JOB_CARD_DETAILS.xlsx", 0, jobCardData);
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
            Workbook wb = new XSSFWorkbook();
            Sheet sheet1 = wb.createSheet("JOB_CARD");
            Sheet sheet2 = wb.createSheet("JOB_CARD_PROGRESS");
            XSSFRow row = (XSSFRow) sheet2.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("SKU");
            Cell cell1 = row.createCell(1);
            cell.setCellValue("leather");
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
            cell10.setCellValue("PRODUCTION STAGE");
            Cell cell11 = row.createCell(11);
            cell11.setCellValue("DATE");
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

    public void saveJobCardProgress(JobCardProgress jobCardProgress, String jobCardFileName) {
        Object[] data;
        if (jobCardProgress.getSize().equals("40")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    jobCardProgress.getCount(), "0", "0", "0", "0", "0", "0", "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("41")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", jobCardProgress.getCount(), "0", "0", "0", "0", "0", "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("42")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", jobCardProgress.getCount(), "0", "0", "0", "0", "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("43")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", jobCardProgress.getCount(), "0", "0", "0", "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("44")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", jobCardProgress.getCount(), "0", "0", "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("45")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", "0", jobCardProgress.getCount(), "0", "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("46")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", "0", "0", jobCardProgress.getCount(), "0",
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", "0", "0", "0", jobCardProgress.getCount(),
                    jobCardProgress.getProductionStage(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        }

        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 1, data);
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
    }
}
