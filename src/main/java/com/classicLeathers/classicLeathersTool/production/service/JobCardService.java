package com.classicLeathers.classicLeathersTool.production.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.production.model.ArticleDto;
import com.classicLeathers.classicLeathersTool.production.model.JobCard;
import com.classicLeathers.classicLeathersTool.production.model.ProductionProgressDto;
import com.classicLeathers.classicLeathersTool.production.model.JobCardProgress;
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

    public List<JobCardProgress> getJobCardProgressList(String jobCardFileName) {

        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            List<JobCardProgress> jobCardProgresses = new ArrayList<>();
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData.length > 1) {
                    JobCardProgress jobCardProgress = new JobCardProgress();
                    jobCardProgress.setSku(cellData[0]);
                    jobCardProgress.setProductionStage(cellData[1]);
                    jobCardProgress.setCount(cellData[2]);
                    jobCardProgress.setDate(cellData[3]);
                    jobCardProgresses.add(jobCardProgress);
                }
            }
            return jobCardProgresses;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ProductionProgressDto> getJobCardOverAllProgressList(String jobCardFileName) {
        List<ProductionProgressDto> jobCardProgressDtoList = getJobCardProgressDtoList(jobCardFileName);

        return Collections.EMPTY_LIST;
    }

    private List<ProductionProgressDto> getJobCardProgressDtoList(String jobCardFileName) {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        if (rowData.size() != 0) {
            List<JobCardProgress> jobCardProgresses = new ArrayList<>();
            for (String row : rowData) {
                System.out.println(row);
            }
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
            Workbook wb = new XSSFWorkbook();
            Sheet sheet1 = wb.createSheet("JOB_CARD");
            Sheet sheet2 = wb.createSheet("JOB_CARD_PROGRESS");
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
        Object[] data = new Object[]{jobCardProgress.getSku(),
                jobCardProgress.getProductionStage(),
                jobCardProgress.getCount(),
                new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date())
        };
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\PRODUCTION_DOCS\\JobCards\\" + jobCardFileName, 1, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
