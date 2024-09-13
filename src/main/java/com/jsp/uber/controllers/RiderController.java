package com.jsp.uber.controllers;

import com.jsp.uber.dto.RideRequestDto;
import com.jsp.uber.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService rideService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto requestDto) {
        System.out.println(requestDto.getPaymentMethod());
        return ResponseEntity.ok(rideService.requestRide(requestDto));
    }

}
