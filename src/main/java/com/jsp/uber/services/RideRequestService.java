package com.jsp.uber.services;

import com.jsp.uber.entites.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
