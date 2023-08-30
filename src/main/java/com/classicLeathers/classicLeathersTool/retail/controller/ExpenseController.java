package com.classicLeathers.classicLeathersTool.retail.controller;

import com.classicLeathers.classicLeathersTool.retail.model.ExpenseDto;
import com.classicLeathers.classicLeathersTool.retail.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    private ResponseEntity<List<ExpenseDto>> getExpensesListByMonth(@RequestParam Integer monthNumber) {
        return ResponseEntity.ok(expenseService.getExpensesListByMonth(monthNumber));
    }

    @GetMapping(path = "/availablePettyCash")
    private ResponseEntity<Integer> getAvailablePettyCash() {
        return ResponseEntity.ok(expenseService.getAvailablePettyCash());
    }

    @GetMapping(path = "/totalCashExpense")
    private ResponseEntity<Integer> getTotalCashExpense() {
        return ResponseEntity.ok(expenseService.getTotalCashExpense());
    }

    @PostMapping(path = "/save", consumes = "application/json")
    public void saveExpense(@RequestBody ExpenseDto expenseDto) {
        expenseService.saveExpense(expenseDto);
    }
}
