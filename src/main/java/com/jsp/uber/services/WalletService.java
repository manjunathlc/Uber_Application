package com.jsp.uber.services;

import com.jsp.uber.entites.User;
import com.jsp.uber.entites.Wallet;

public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount);

    Wallet deductMoneyFromWallet(User user, Double amount);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);


}
