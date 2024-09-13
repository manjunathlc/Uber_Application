package com.jsp.uber.strategies.impl;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.repositories.DriverRepository;
import com.jsp.uber.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMathDrivers(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickUpLocation());
    }
}
