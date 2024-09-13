package com.jsp.uber.strategies;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.RideRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DriverMatchingStrategy {

     List<Driver> findMathDrivers(RideRequest rideRequest);
}
