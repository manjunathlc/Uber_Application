package com.jsp.uber.services.impl;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.entites.enums.RideRequestStatus;
import com.jsp.uber.entites.enums.RideStatus;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.repositories.RideRepository;
import com.jsp.uber.services.RideRequestService;
import com.jsp.uber.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with id " + rideId));
    }

    @Override
    public void matchWithDrivers(RideRequestDto rideRequestDto) {

    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFORMED);
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFORMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Long driverId, PageRequest pageRequest) {
        return null;
    }

    private String generateRandomOTP(){
        Random random = new Random();
        int otp = random.nextInt(10000);    //0 to 9999
        return String.format("%04d", otp);
    }
}
