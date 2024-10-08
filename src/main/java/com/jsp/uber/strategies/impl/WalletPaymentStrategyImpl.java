package com.jsp.uber.strategies.impl;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.enums.PaymentStatus;
import com.jsp.uber.entites.enums.TransactionMethod;
import com.jsp.uber.repositories.PaymentRepository;
import com.jsp.uber.services.PaymentService;
import com.jsp.uber.services.WalletService;
import com.jsp.uber.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//Rider had 300, Driver had 500
//Ride cost = 100 commission = 30
//Rider 300-100 = 200
// Driver 500 + (100-30) = 570

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategyImpl implements PaymentStrategy {

    private final WalletService walletService;
//    private final PaymentService paymentService;
private final PaymentRepository paymentRepository;


    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null,
                payment.getRide(), TransactionMethod.RIDE);

        Double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION );

        walletService.addMoneyToWallet(driver.getUser(), driversCut,
                null,payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFORMED);
        paymentRepository.save(payment);
    }
}
