package com.jsp.uber.strategies.impl;

import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.services.DistanceService;
import com.jsp.uber.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance= distanceService.calculateDistance( rideRequest.getPickUpLocation(),
                rideRequest.getDropOffLocation());

        return distance * RIDE_FARE_MULTIPLIER;
    }
}
