package com.jsp.uber.services;

import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
