package com.kredx.cashsettlement.transaction.svc.impl;

import com.kredx.cashsettlement.transaction.api.dto.LendBorrowRequest;
import com.kredx.cashsettlement.transaction.api.dto.TransactionReportResponse;
import com.kredx.cashsettlement.transaction.svc.TransactionService;
import com.kredx.cashsettlement.transaction.svc.impl.dao.TransactionRepository;
import com.kredx.cashsettlement.transaction.svc.impl.dao.entity.Transaction;
import com.kredx.cashsettlement.user.svc.impl.dao.UserRepository;
import com.kredx.cashsettlement.user.svc.impl.dao.entity.User;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Transaction createLendTransaction(LendBorrowRequest lendRequest) {
        //borrower
        Optional<User> borrower = userRepository.findByUsername(lendRequest.getUsername());
        if(borrower.isEmpty()){
            throw new BadRequestException(String.format("You can not land your money to %s , as it is not exist.", lendRequest.getUsername()));
        }

        //lender
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String lenderUserName = userDetails.getUsername();

        if(lenderUserName.equals(borrower.get().getUsername())){
            throw new BadRequestException("You can not land money from your self");
        }

        Transaction transaction = new Transaction()
                .setBorrower(borrower.get().getUsername())
                .setLender(lenderUserName)
                .setAmount(lendRequest.getAmount())
                .setDate(lendRequest.getDate());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction createBorrowTransaction(LendBorrowRequest borrowRequest) {
        //borrower
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String borrowerUserName = userDetails.getUsername();

        //lender
        Optional<User> lender = userRepository.findByUsername(borrowRequest.getUsername());
        if(lender.isEmpty()){
            throw new BadRequestException(String.format("You can not borrow money from %s , as it is not exist.", borrowRequest.getUsername()));
        }

        if(borrowerUserName.equals(lender.get().getUsername())){
            throw new BadRequestException("You can not borrow money from your self");
        }

        Transaction transaction = new Transaction()
                .setLender(lender.get().getUsername())
                .setBorrower(borrowerUserName)
                .setAmount(borrowRequest.getAmount())
                .setDate(borrowRequest.getDate());

        return transactionRepository.save(transaction);
    }

    @Override
    public Page<Transaction> getTransactionsByUser(String sortParameter, String sortOrder, int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortParameter);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return transactionRepository.findByBorrowerOrLender(username, username, pageRequest);
    }

    @Override
    public List<TransactionReportResponse> getTotalBalanceReportForUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Iterable<Object[]> iterableResult = transactionRepository.getTotalBalanceReportForUser(username);

        List<TransactionReportResponse> resultList = new ArrayList<>();
        for (Object[] result : iterableResult) {
            String person = (String) result[0];
            double totalBalance = (Double) result[1];
            TransactionReportResponse dto = new TransactionReportResponse(person, totalBalance);
            resultList.add(dto);
        }

        return resultList;

    }

}