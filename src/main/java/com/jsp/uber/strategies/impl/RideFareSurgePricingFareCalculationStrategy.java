package com.jsp.uber.strategies.impl;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.strategies.RideFareCalculationStrategy;

public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequestDto rideRequestDto) {
        return 0;
    }
}
