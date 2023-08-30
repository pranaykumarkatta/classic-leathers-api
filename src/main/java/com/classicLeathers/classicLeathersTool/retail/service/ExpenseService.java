package com.classicLeathers.classicLeathersTool.retail.service;

import com.classicLeathers.classicLeathersTool.FileUtils;
import com.classicLeathers.classicLeathersTool.retail.model.ExpenseDto;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

    private List<String> loadFileData() {
        String fileData = "";
        try {
            fileData = new FileUtils().getFileData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2023 EXPENSES_V2.xlsx", 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<String> rowData = new ArrayList<>();
        rowData.addAll(Arrays.asList(fileData.split("\n")));
        return rowData;

    }

    public List<ExpenseDto> getExpensesListByMonth(Integer monthNumber) {
        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        List<String> rowData = loadFileData();
        if (rowData.size() != 0) {
            rowData.remove(0);
            for (String row : rowData) {
                String[] cellData = row.split(",");
                ExpenseDto expenseDto = new ExpenseDto();
                expenseDto.setDate(cellData[0]);
                expenseDto.setUpdatedBy(cellData[1]);
                expenseDto.setExpenseType(cellData[2]);
                expenseDto.setAmount(cellData[3]);
                expenseDto.setPaidTo(cellData[4]);
                expenseDto.setMop(cellData[5]);
                expenseDto.setPaymentReference(cellData[6]);
                expenseDtoList.add(expenseDto);
            }
        }
        return expenseDtoList;
    }

    public void saveExpense(ExpenseDto expenseDto) {
        Object[] expenseData = new Object[]{new SimpleDateFormat("MMM-d-yyyy h:mm a").format(new Date()),
                expenseDto.getUpdatedBy(),
                expenseDto.getExpenseType(),
                expenseDto.getExpenseType().equals("LOAD AMOUNT") ? "" + expenseDto.getAmount() : "-" + expenseDto.getAmount(),
                expenseDto.getPaidTo(),
                expenseDto.getMop(),
                expenseDto.getPaymentReference()
        };
        try {
            new FileUtils().WriteData("D:\\onedrive\\CLASSIC_DOCS\\RETAIL_DOCS\\2023 EXPENSES_V2.xlsx", 0, expenseData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getAvailablePettyCash() {
        List<String> rowData = loadFileData();
        Integer availablePettyAmount = 0;
        if (rowData.size() != 0) {
            rowData.remove(0);
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData[5].equals("CASH")) {
                    availablePettyAmount = availablePettyAmount + Integer.parseInt(cellData[3]);
                }
            }
        }
        return availablePettyAmount;
    }

    public Integer getTotalCashExpense() {
        List<String> rowData = loadFileData();
        Integer totalCashExpense = 0;
        if (rowData.size() != 0) {
            rowData.remove(0);
            for (String row : rowData) {
                String[] cellData = row.split(",");
                if (cellData[2].equals("LOAD AMOUNT") && cellData[5].equals("CASH")) {
                    totalCashExpense = totalCashExpense + Integer.parseInt(cellData[3]);
                }
            }
        }
        return totalCashExpense;
    }
}
