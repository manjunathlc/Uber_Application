package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.RideDto;
import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.dto.RiderDto;
import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.User;
import com.jsp.uber.entites.enums.RideRequestStatus;
import com.jsp.uber.repositories.RideRequestRepository;
import com.jsp.uber.repositories.RiderRepository;
import com.jsp.uber.services.RiderService;
import com.jsp.uber.strategies.DriverMatchingStrategy;
import com.jsp.uber.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Double fare= rideFareCalculationStrategy.calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        driverMatchingStrategy.findMathDrivers( rideRequest);

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
}
