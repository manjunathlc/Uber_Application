package com.jsp.uber.strategies;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

     List<Driver> findMatchingDrivers(RideRequest rideRequest);
}
