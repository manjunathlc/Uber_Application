package com.jsp.uber.strategies.impl;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.enums.PaymentStatus;
import com.jsp.uber.entites.enums.TransactionMethod;
import com.jsp.uber.repositories.PaymentRepository;
import com.jsp.uber.services.PaymentService;
import com.jsp.uber.services.WalletService;
import com.jsp.uber.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
//    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        Double platformCommission= payment.getAmount()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission,
                null,payment.getRide(), TransactionMethod.RIDE);

//        paymentService.updatePaymentStatus(payment, PaymentStatus.CONFORMED);
        payment.setPaymentStatus(PaymentStatus.CONFORMED);
        paymentRepository.save(payment);

    }
}
