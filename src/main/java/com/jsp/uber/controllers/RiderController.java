package com.jsp.uber.controllers;

import com.jsp.uber.dto.*;
import com.jsp.uber.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
@Secured("ROLE_RIDER")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto requestDto) {
        System.out.println(requestDto.getPaymentMethod());
        return ResponseEntity.ok(riderService.requestRide(requestDto));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RateDto rateDto){
        return ResponseEntity.ok((riderService.rateDriver(rateDto.getRideId(),rateDto.getRating())));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getMyProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                       @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        return ResponseEntity.ok(riderService.
                getAllMyRides(PageRequest.of
                        (pageOffSet, pageSize, Sort.by(Sort.Direction.DESC, "createdTime", "id"))));
    }


}
