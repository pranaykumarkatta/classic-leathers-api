//package com.classicLeathers.classicLeathersTool.v1.retail.service;
//
//import com.classicLeathers.classicLeathersTool.FileUtils;
//import com.classicLeathers.classicLeathersTool.v1.retail.model.ExpenseDto;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class ExpenseService {
//    public static String fileData = "";
//    public static final String FILE_PATH = "D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2024_EXPENSES.xlsx";
//
//    private List<String> loadFileData(Integer monthNumber) {
//
//        try {
//            fileData = new FileUtils().getFileData(FILE_PATH, monthNumber - 1);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        List<String> rowData = new ArrayList<>();
//        rowData.addAll(Arrays.asList(fileData.split("\n")));
//        return rowData;
//
//    }
//
//    public List<ExpenseDto> getExpensesListByMonth(Integer monthNumber) {
//        List<ExpenseDto> expenseDtoList = new ArrayList<>();
//        List<String> rowData = loadFileData(monthNumber);
//        if (rowData.size() != 0) {
//            rowData.remove(0);
//            for (String row : rowData) {
//                String[] cellData = row.split(",");
//                ExpenseDto expenseDto = new ExpenseDto();
//                expenseDto.setDate(cellData[0]);
//                expenseDto.setUpdatedBy(cellData[1]);
//                expenseDto.setExpenseType(cellData[2]);
//                expenseDto.setAmount(cellData[3]);
//                expenseDto.setPaidTo(cellData[4]);
//                expenseDto.setMop(cellData[5]);
//                expenseDto.setPaymentReference(cellData[6]);
//                expenseDtoList.add(expenseDto);
//            }
//        }
//        return expenseDtoList.stream()
//                .sorted(Collections.reverseOrder())
//                .collect(Collectors.toList());
//    }
//
//    public void saveExpense(ExpenseDto expenseDto, Integer monthNumber) {
//        Object[] expenseData = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
//                expenseDto.getUpdatedBy(),
//                expenseDto.getExpenseType(),
//                expenseDto.getExpenseType().equals("LOAD AMOUNT") ? "" + expenseDto.getAmount() : "-" + expenseDto.getAmount(),
//                expenseDto.getPaidTo(),
//                expenseDto.getMop(),
//                expenseDto.getPaymentReference()
//        };
//        try {
//            new FileUtils().WriteData(FILE_PATH, monthNumber - 1, expenseData);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Integer getAvailablePettyCash(Integer monthNumber) {
//        List<String> rowData = loadFileData(monthNumber - 1);
//        Integer availablePettyAmount = 0;
//        if (rowData.size() != 0) {
//            rowData.remove(0);
//            for (String row : rowData) {
//                String[] cellData = row.split(",");
//                if (cellData[5].equals("CASH")) {
//                    availablePettyAmount = availablePettyAmount + Integer.parseInt(cellData[3]);
//                }
//            }
//        }
//        return availablePettyAmount;
//    }
//
//    public Integer getTotalCashExpense() {
//        List<String> rowData = loadFileData(12);
//        Integer totalCashExpense = 0;
//        if (rowData.size() != 0) {
//            rowData.remove(0);
//            for (String row : rowData) {
//                String[] cellData = row.split(",");
//                if (cellData[2].equals("LOAD AMOUNT") && cellData[5].equals("CASH") && (cellData[0].contains(new SimpleDateFormat("MMM-").format(new Date())))) {
//                    totalCashExpense = totalCashExpense + Integer.parseInt(cellData[3]);
//                }
//            }
//        }
//        return totalCashExpense;
//    }
//}
