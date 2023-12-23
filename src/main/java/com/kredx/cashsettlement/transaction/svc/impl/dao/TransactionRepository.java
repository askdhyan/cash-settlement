package com.kredx.cashsettlement.transaction.svc.impl.dao;

import com.kredx.cashsettlement.transaction.svc.impl.dao.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByBorrowerOrLender(String username, String username1, PageRequest pageRequest);

    @Query(nativeQuery = true, value = """ 
                                 WITH Combined AS (
                                    SELECT borrower AS username, -amount AS balance FROM demo.transaction WHERE lender = 'dhyann'
                                        UNION ALL
                                    SELECT lender AS username, amount AS balance FROM demo.transaction WHERE borrower = 'dhyann'
                                 )
                                SELECT username, SUM(balance) AS balance FROM Combined GROUP BY username;
                    """
    )
    Iterable<Object[]> getTotalBalanceReportForUser(String username);
}
