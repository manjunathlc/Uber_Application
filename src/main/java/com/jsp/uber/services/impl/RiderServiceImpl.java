package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RideDto;
import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.*;
import com.jsp.uber.entites.enums.RideRequestStatus;
import com.jsp.uber.entites.enums.RideStatus;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.repositories.RideRequestRepository;
import com.jsp.uber.repositories.RiderRepository;
import com.jsp.uber.services.DriverService;
import com.jsp.uber.services.RatingService;
import com.jsp.uber.services.RideService;
import com.jsp.uber.services.RiderService;
import com.jsp.uber.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        Rider rider = getCurrentRider();
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);


        Double fare= rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
//        rideRequest.setRequestedTime(LocalDateTime.now());

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers =
                rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDrivers( rideRequest);
//        TODO : Send notification to all drivers about this ride request (accept method)

        return modelMapper.map(savedRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);
        if (!ride.getRider().equals(rider)) {
            throw new ResourceNotFoundException("Ride not found or not owned by this rider with id " +rideId);
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFORMED)){
            throw new RuntimeException("Ride cannot be cancelled, status is " + ride.getRideStatus());
        }
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.CANCELLED);

        driverService.updateDriverAvailability(ride.getDriver(),true);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();
        if(!ride.getRider().equals(rider)){
            throw new RuntimeException("Rider cannot rate Driver as he is not the owner of the ride");
        }
        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride Status is not ENDED hence cannot be rated, status is " +ride.getRideStatus());
        }
//        ratingService.rateRider(ride, currentDriver, rating);
        return ratingService.rateDriver(ride,rating);

    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
//        Page<Ride> rides = rideService.getAllRidesOfRider(currentRider.getId(), pageRequest);
//        return rides.map(ride -> modelMapper.map(ride, RideDto.class));
        return rideService.getAllRidesOfRider(currentRider,pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {

// implement Spring Security
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // fow now return dummy rider
        return riderRepository.findByUser(user)
                .orElseThrow(()-> new ResourceNotFoundException("Rider not associated with User with id " + user.getId()));
    }
}
