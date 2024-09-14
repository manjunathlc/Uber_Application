package com.jsp.uber.services;


import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RideDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.Driver;

import java.util.List;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long rideId);   // DriverId is automatically taken through spring security context holder

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    List<RideDto> getAllMyRides();

    Driver getCurrentDriver();

}
