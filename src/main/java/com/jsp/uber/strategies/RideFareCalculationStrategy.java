package com.jsp.uber.strategies;

import com.jsp.uber.dto.RideRequestDto;

public interface RideFareCalculationStrategy {
    double calculateFare(RideRequestDto rideRequestDto);


}
