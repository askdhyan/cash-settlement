package com.kredx.cashsettlement.transaction.svc;

import com.kredx.cashsettlement.transaction.api.dto.LendBorrowRequest;
import com.kredx.cashsettlement.transaction.api.dto.TransactionReportResponse;
import com.kredx.cashsettlement.transaction.svc.impl.dao.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    public Transaction createLendTransaction(LendBorrowRequest lendRequest);

    public Transaction createBorrowTransaction(LendBorrowRequest borrowRequest);

    Page<Transaction> getTransactionsByUser(String sortParameter, String sortOrder, int page, int size);

    List<TransactionReportResponse> getTotalBalanceReportForUser();
}