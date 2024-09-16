package com.jsp.uber.services.impl;

import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.enums.PaymentStatus;
import com.jsp.uber.repositories.PaymentRepository;
import com.jsp.uber.services.PaymentService;
import com.jsp.uber.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    @Override
    public void processPayment(Ride ride) {
//        PaymentStrategyManager.PaymentStrategy paymentStrategy = paymentStrategyManager.getPaymentStrategy(payment.getType());
//        paymentStrategy.processPayment(payment);
//        paymentRepository.save(payment);
//        // TODO: Send confirmation email to user

        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new RuntimeException("Payment not found for ride with id " +ride.getId()));

        paymentStrategyManager.getPaymentStrategy(payment.getPaymentMethod()).processPayment(payment);

    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);

    }
}
