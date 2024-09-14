package com.jsp.uber.strategies.impl;

import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.services.DistanceService;
import com.jsp.uber.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private final static double SURGE_FACTOR = 2.0;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance= distanceService.calculateDistance( rideRequest.getPickUpLocation(),
                rideRequest.getDropOffLocation());

        return distance * RIDE_FARE_MULTIPLIER;
    }
}
