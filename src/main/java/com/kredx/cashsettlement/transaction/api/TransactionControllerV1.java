package com.kredx.cashsettlement.transaction.api;

import com.kredx.cashsettlement.transaction.api.dto.LendBorrowRequest;
import com.kredx.cashsettlement.transaction.api.dto.TransactionReportResponse;
import com.kredx.cashsettlement.transaction.svc.TransactionService;
import com.kredx.cashsettlement.transaction.svc.impl.dao.entity.Transaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/transaction")
public class TransactionControllerV1 {
    @Autowired
    TransactionService transactionService;

    @PostMapping(path="/lend")
    public ResponseEntity<Transaction> createLendTransaction(@Valid @RequestBody LendBorrowRequest lendBorrowRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createLendTransaction(lendBorrowRequest));
    }

    @PostMapping(path="/borrow")
    public ResponseEntity<Transaction> createBorrowTransaction(@Valid @RequestBody LendBorrowRequest lendBorrowRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createBorrowTransaction(lendBorrowRequest));
    }

    @GetMapping
    public ResponseEntity<Page<Transaction>> getTransactionsByUser(@RequestParam(defaultValue = "id") String sortParameter,
                                                   @RequestParam(defaultValue = "asc") String sortOrder,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "15") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionsByUser(sortParameter,sortOrder,page,size));
    }

    @GetMapping(path="/report")
    public ResponseEntity<List<TransactionReportResponse>> getTotalBalanceReportForUser() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTotalBalanceReportForUser());
    }


}
