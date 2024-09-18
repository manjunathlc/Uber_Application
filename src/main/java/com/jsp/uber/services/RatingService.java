package com.jsp.uber.services;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.Rider;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);

//    Double calculateDriverAverageRating(Driver driver);

    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
