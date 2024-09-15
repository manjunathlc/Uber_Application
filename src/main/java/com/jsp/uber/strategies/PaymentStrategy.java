package com.jsp.uber.strategies;

import com.jsp.uber.entites.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);
}
