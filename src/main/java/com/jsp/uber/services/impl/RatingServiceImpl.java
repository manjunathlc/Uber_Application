package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Rating;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.exceptions.RuntimeConflictException;
import com.jsp.uber.repositories.DriverRepository;
import com.jsp.uber.repositories.RatingRepository;
import com.jsp.uber.repositories.RiderRepository;
import com.jsp.uber.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;


    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()-> new ResourceNotFoundException("Rating not found with id: " +ride.getId()));

        if(ratingObj.getDriverRating()!= null)
            throw new RuntimeConflictException("Driver is already been rated, cannot rate it again");

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream().mapToDouble(rating1->rating1.getDriverRating())
                .average()
                .orElse(0.0);

        driver.setRating(newRating);

        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Could not find rating with id " +ride.getId()));

        if(ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider is already been rated, cannot rate it again");

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream().mapToDouble(rating1->rating1.getRiderRating())
                .average()
                .orElse(0.0);
        rider.setRating(newRating);

        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
