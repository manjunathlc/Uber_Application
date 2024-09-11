package com.jsp.uber.strategies.impl;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;


@Service
public class RideFareCalculationStrategyImpl implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequestDto rideRequestDto) {
        return 0;
    }
}
