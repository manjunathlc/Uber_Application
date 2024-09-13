package com.jsp.uber.strategies.impl;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.strategies.DriverMatchingStrategy;

import java.util.List;

public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMathDrivers(RideRequest rideRequest) {
        return List.of();
    }
}
