package com.jsp.uber.strategies.impl;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.Wallet;
import com.jsp.uber.services.WalletService;
import com.jsp.uber.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CODPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        Wallet driverWallet = walletService.findByUser(driver.getUser());
        Double platformCommission= payment.getAmount()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission);
    }
}
