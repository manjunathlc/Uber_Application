package com.jsp.uber.services.impl;

import com.jsp.uber.entites.Payment;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.repositories.PaymentRepository;
import com.jsp.uber.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {

    }

    @Override
    public Payment createNewPayment(Ride ride) {
        return null;
    }
}
