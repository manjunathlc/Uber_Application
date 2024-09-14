package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RideDto;
import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.User;
import com.jsp.uber.entites.enums.RideRequestStatus;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.repositories.RideRequestRepository;
import com.jsp.uber.repositories.RiderRepository;
import com.jsp.uber.services.RiderService;
import com.jsp.uber.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
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
//TODO  : implement Spring Security
        // fow now return dummy rider
        return riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException(
                "Rider not found with id " + 1));
    }
}
