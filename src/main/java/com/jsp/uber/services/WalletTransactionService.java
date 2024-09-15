package com.jsp.uber.services;


import com.jsp.uber.dto.WalletTransactionDto;

public interface WalletTransactionService {

    void createNewWalletTransaction(WalletTransactionDto walletTransactionDto);
}
