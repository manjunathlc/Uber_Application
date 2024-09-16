package com.jsp.uber.services.impl;

import com.jsp.uber.dto.WalletTransactionDto;
import com.jsp.uber.entites.WalletTransaction;
import com.jsp.uber.repositories.WalletTransactionRepository;
import com.jsp.uber.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;
    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);

    }
}
