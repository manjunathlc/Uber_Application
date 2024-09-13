package com.jsp.uber.strategies;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.RideRequest;
import org.springframework.stereotype.Service;


@Service
public interface RideFareCalculationStrategy {

   final double RIDE_FARE_MULTIPLIER = 10;

   double calculateFare(RideRequest rideRequest);


}
