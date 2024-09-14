package com.jsp.uber.strategies;

import com.jsp.uber.entites.RideRequest;


public interface RideFareCalculationStrategy {

   final double RIDE_FARE_MULTIPLIER = 10;

   double calculateFare(RideRequest rideRequest);


}
