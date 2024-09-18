package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RideDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.entites.enums.RideRequestStatus;
import com.jsp.uber.entites.enums.RideStatus;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.repositories.DriverRepository;
import com.jsp.uber.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);
        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride request cannot be accepted, as status is " +rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept ride due to unavailability ");
        }

        currentDriver.setAvailable(false);
        Driver savedDriver = updateDriverAvailability(currentDriver,false);
        Ride ride =  rideService.createNewRide(rideRequest,savedDriver);

//        rideRequest.setRideRequestStatus(RideRequestStatus.CONFORMED);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!ride.getDriver().equals(driver)){
            throw new RuntimeException("Driver cannot start ride as he has not accepted it earlier");
        }   // change to UnauthorizedException

        if(!ride.getRideStatus().equals(RideStatus.CONFORMED)){
            throw new RuntimeException("Ride cannot be cancelled, status is " +ride.getRideStatus());
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
//        driver.setAvailable(true);
//        driverRepository.save(driver);
        updateDriverAvailability(driver,true);

        return modelMapper.map(ride,RideDto.class);

    }

    @Override
    public RideDto startRide(Long rideId,String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!ride.getDriver().equals(driver)){
//            throw new RuntimeException("Ride is not assigned to current driver");
            throw new RuntimeException("Driver cannot start ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFORMED)){
            throw new RuntimeException("Ride Status is not CONFORMED hence cannot be started, status is " +ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("Otp is not valid, otp is "+otp);
        }

        ride.setStartAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);


        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!ride.getDriver().equals(driver)){
//            throw new RuntimeException("Ride is not assigned to current driver");
            throw new RuntimeException("Driver cannot start ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride Status is not ONGOING hence cannot be ended, status is " +ride.getRideStatus());
        }

        ride.setEndAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if(!ride.getDriver().equals(driver)){
            throw new RuntimeException("Driver cannot rate rider as he is not the owner of the ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride Status is not ENDED hence cannot be rated, status is " +ride.getRideStatus());
        }
//        ratingService.rateRider(ride, currentDriver, rating);
        return ratingService.rateRider(ride,rating);


    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver= getCurrentDriver();
        return modelMapper.map(currentDriver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver= getCurrentDriver();
//        Page<Ride> rides = rideService.getAllRidesOfRider(currentDriver.getId(), pageRequest);
//        return rides.map(ride -> modelMapper.map(ride, RideDto.class));

        return rideService.getAllRidesOfDriver(currentDriver, pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {  // implement spring security
        return driverRepository.findById(2L)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id " + 2));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean availability) {
        driver.setAvailable(availability);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
