package com.jsp.uber.services.impl;

import com.jsp.uber.entites.User;
import com.jsp.uber.entites.Wallet;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.repositories.WalletRepository;
import com.jsp.uber.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    @Override
    public Wallet addMoneyToWallet(User user, Double amount) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() + amount);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance() - amount);
        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(()-> new ResourceNotFoundException("Wallet not found with id " + walletId));
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setId(user.getId());
        return walletRepository.save(wallet);

    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("Wallet not found with id " + user.getId()));
    }
}
