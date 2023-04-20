package com.classicLeathers.classicLeathersTool.production.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import com.classicLeathers.classicLeathersTool.production.model.JobCard;
import com.classicLeathers.classicLeathersTool.production.model.JobCardProgress;
import com.classicLeathers.classicLeathersTool.production.model.ProductionProgressDto;
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
                jobCardProgressSkus.add(cellData[0] + "_" + cellData[1]);
            }
            return jobCardProgressSkus;
        }
        return Collections.EMPTY_LIST;
    }

    public List<JobCardProgress> getJobCardProgressList(String jobCardFileName) {

        List<JobCardProgress> jobCardProgressDtos = new ArrayList<>();
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "CUTTING", 1));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "JOB WORK", 2));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "HS", 3));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "FINISHING", 4));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "PACKING", 5));
        jobCardProgressDtos.addAll(getJobCardProgress(jobCardFileName, "DISPATCH", 6));
        return jobCardProgressDtos;
    }

    private List<JobCardProgress> getJobCardProgress(String jobCardFileName, String productionStage, Integer sheetIndex) {
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
        if (rowData.size() != 0) {
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
                    jobCardProgresses.add(jobCardProgressDto);
                }
            }
            return jobCardProgresses;
        }
        return Collections.EMPTY_LIST;
    }

    public Map<String, List<ProductionProgressDto>> getJobCardOverAllProgressList(String jobCardFileName) {
        Map<String, List<ProductionProgressDto>> productionProgressMap = new HashMap<>();
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
            List<ProductionProgressDto> dtoList = new ArrayList<>();
            for (String row : rowData) {
                ProductionProgressDto dto = new ProductionProgressDto();
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

                dtoList.add(dto);
            }
            productionProgressMap.put("ORDERED", dtoList);
            productionProgressMap.put("CUTTING", getProductionProgressMap(jobCardFileName,1));
            productionProgressMap.put("JOB WORK", getProductionProgressMap(jobCardFileName,2));
            productionProgressMap.put("HS", getProductionProgressMap(jobCardFileName,3));
            productionProgressMap.put("FINISHING", getProductionProgressMap(jobCardFileName,4));
            productionProgressMap.put("PACKED", getProductionProgressMap(jobCardFileName,5));
            productionProgressMap.put("DISPATCHED", getProductionProgressMap(jobCardFileName,6));
        }
        return productionProgressMap;
    }

    private List<ProductionProgressDto> getProductionProgressMap(String jobCardFileName, int sheetIndex) {

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
            Map<String,ProductionProgressDto> map = new HashMap<>();
            List<ProductionProgressDto> dtoList = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (!map.keySet().contains(cellData[0]+"_"+cellData[1])){
                    ProductionProgressDto productionProgressDto = new ProductionProgressDto();
                    productionProgressDto.setSku(cellData[0]);
                    productionProgressDto.setLeather(cellData[1]);
                    productionProgressDto.setSize_40_quantity(cellData[2]);
                    productionProgressDto.setSize_41_quantity(cellData[3]);
                    productionProgressDto.setSize_42_quantity(cellData[4]);
                    productionProgressDto.setSize_43_quantity(cellData[5]);
                    productionProgressDto.setSize_44_quantity(cellData[6]);
                    productionProgressDto.setSize_45_quantity(cellData[7]);
                    productionProgressDto.setSize_46_quantity(cellData[8]);
                    productionProgressDto.setSize_47_quantity(cellData[9]);
                    map.put(cellData[0]+"_"+cellData[1],productionProgressDto);
                }else{
                    ProductionProgressDto productionProgressDto = map.get(cellData[0]+"_"+cellData[1]);
                    productionProgressDto.setSku(cellData[0]);
                    productionProgressDto.setLeather(cellData[1]);
                    productionProgressDto.setSize_40_quantity(""+((Integer.parseInt(cellData[2]))+(Integer.parseInt(productionProgressDto.getSize_40_quantity()))));
                    productionProgressDto.setSize_41_quantity(""+((Integer.parseInt(cellData[3]))+(Integer.parseInt(productionProgressDto.getSize_41_quantity()))));
                    productionProgressDto.setSize_42_quantity(""+((Integer.parseInt(cellData[4]))+(Integer.parseInt(productionProgressDto.getSize_42_quantity()))));
                    productionProgressDto.setSize_43_quantity(""+((Integer.parseInt(cellData[5]))+(Integer.parseInt(productionProgressDto.getSize_43_quantity()))));
                    productionProgressDto.setSize_44_quantity(""+((Integer.parseInt(cellData[6]))+(Integer.parseInt(productionProgressDto.getSize_44_quantity()))));
                    productionProgressDto.setSize_45_quantity(""+((Integer.parseInt(cellData[7]))+(Integer.parseInt(productionProgressDto.getSize_45_quantity()))));
                    productionProgressDto.setSize_46_quantity(""+((Integer.parseInt(cellData[8]))+(Integer.parseInt(productionProgressDto.getSize_46_quantity()))));
                    productionProgressDto.setSize_47_quantity(""+((Integer.parseInt(cellData[9]))+(Integer.parseInt(productionProgressDto.getSize_47_quantity()))));
                    map.put(cellData[0]+"_"+cellData[1],productionProgressDto);
                }
            }

            map.forEach((s, productionProgressDto) -> {
                dtoList.add(productionProgressDto);
            });
            return dtoList;
        }
        return Collections.EMPTY_LIST;
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
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet sheet1 = wb.createSheet("JOB_CARD");
            createCuttingProductionProgressSheets(wb, "CUTTING");
            createCuttingProductionProgressSheets(wb, "JOB_WORK");
            createCuttingProductionProgressSheets(wb, "HS");
            createCuttingProductionProgressSheets(wb, "FINISHING");
            createCuttingProductionProgressSheets(wb, "PACKING");
            createCuttingProductionProgressSheets(wb, "DISPATCH");
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

    private void createCuttingProductionProgressSheets(XSSFWorkbook wb, String sheetName) {
        Sheet sheet = wb.createSheet(sheetName);
        XSSFRow row = (XSSFRow) sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("SKU");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue("leather");
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
        Cell cell11 = row.createCell(10);
        cell11.setCellValue("DATE");
    }

    public void saveJobCardProgress(JobCardProgress jobCardProgress, String jobCardFileName) {
        Object[] data;
        if (jobCardProgress.getSize().equals("40")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    jobCardProgress.getCount(), "0", "0", "0", "0", "0", "0", "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("41")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", jobCardProgress.getCount(), "0", "0", "0", "0", "0", "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("42")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", jobCardProgress.getCount(), "0", "0", "0", "0", "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("43")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", jobCardProgress.getCount(), "0", "0", "0", "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("44")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", jobCardProgress.getCount(), "0", "0", "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("45")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", "0", jobCardProgress.getCount(), "0", "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else if (jobCardProgress.getSize().equals("46")) {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", "0", "0", jobCardProgress.getCount(), "0",
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        } else {
            data = new Object[]{jobCardProgress.getSku(),
                    jobCardProgress.getLeather(),
                    "0", "0", "0", "0", "0", "0", "0", jobCardProgress.getCount(),
                    new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
            };
        }

        try {
            if (jobCardProgress.getProductionStage().equals("CUTTING")) {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 1, data);
            }
            if (jobCardProgress.getProductionStage().equals("JOB WORK")) {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 2, data);
            }
            if (jobCardProgress.getProductionStage().equals("HS")) {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 3, data);
            }
            if (jobCardProgress.getProductionStage().equals("FINISHING")) {
                new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 4, data);
            }
            if (jobCardProgress.getProductionStage().equals("PACKING")) {
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
