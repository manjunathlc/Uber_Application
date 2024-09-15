package com.jsp.uber.services;

import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.Ride;

public interface PaymentService {
    void processPayment(Payment payment);

    Payment createNewPayment(Ride ride);
}
