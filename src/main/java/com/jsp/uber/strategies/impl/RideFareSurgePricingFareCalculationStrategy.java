package com.jsp.uber.strategies.impl;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequest rideRequest) {
        return 0;
    }
}
