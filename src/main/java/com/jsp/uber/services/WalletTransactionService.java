package com.jsp.uber.services;


import com.jsp.uber.dto.WalletTransactionDto;
import com.jsp.uber.entites.WalletTransaction;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
