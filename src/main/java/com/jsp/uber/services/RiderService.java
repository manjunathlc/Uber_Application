package com.jsp.uber.services;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RideDto;
import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.User;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto  rideRequestDto);     // return with fare as it is not calculated by the rider

    RideDto cancelRide(Long rideId);   // DriverId is automatically taken through spring security context holder



    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();

    Rider createNewRider(User user);
}
