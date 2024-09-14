package com.jsp.uber.services.impl;

import com.jsp.uber.entites.RideRequest;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.repositories.RideRequestRepository;
import com.jsp.uber.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository;
    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id " + rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {

        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id " + rideRequest.getId()));
        rideRequestRepository.save(rideRequest);
    }
}
