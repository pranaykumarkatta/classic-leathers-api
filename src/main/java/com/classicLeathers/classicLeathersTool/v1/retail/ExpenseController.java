//package com.classicLeathers.classicLeathersTool.v1.retail;
//
//import com.classicLeathers.classicLeathersTool.v1.retail.model.ExpenseDto;
//import com.classicLeathers.classicLeathersTool.v1.retail.service.ExpenseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/v1/expense")
//@CrossOrigin(origins = "*")
//public class ExpenseController {
//
//    @Autowired
//    private ExpenseService expenseService;
//
//    @PostMapping(path = "/save", consumes = "application/json")
//    public void saveExpense(@RequestBody ExpenseDto expenseDto,@RequestParam Integer monthNumber) {
//        expenseService.saveExpense(expenseDto, monthNumber);
//    }
//
//    @GetMapping
//    private ResponseEntity<List<ExpenseDto>> getExpensesListByMonth(@RequestParam Integer monthNumber) {
//        return ResponseEntity.ok(expenseService.getExpensesListByMonth(monthNumber));
//    }
//
//    @GetMapping(path = "/availablePettyCash")
//    private ResponseEntity<Integer> getAvailablePettyCash(@RequestParam Integer monthNumber) {
//        return ResponseEntity.ok(expenseService.getAvailablePettyCash(monthNumber));
//    }
//
//    @GetMapping(path = "/totalCashExpense")
//    private ResponseEntity<Integer> getTotalCashExpense() {
//        return ResponseEntity.ok(expenseService.getTotalCashExpense());
//    }
//}
