package com.jsp.uber.services;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {
    Ride getRideById(Long rideId);


    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);




}
