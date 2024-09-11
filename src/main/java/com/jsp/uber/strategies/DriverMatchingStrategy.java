package com.jsp.uber.strategies;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.Driver;

import java.util.List;

public interface DriverMatchingStrategy {

     List<Driver> findMathDrivers(RideRequestDto rideRequestDto);
}
