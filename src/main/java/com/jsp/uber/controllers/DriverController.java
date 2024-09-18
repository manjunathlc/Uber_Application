package com.jsp.uber.controllers;

import com.jsp.uber.dto.*;
import com.jsp.uber.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));
        // int rideDto we returning otp which is wrong
        // we can create two rideDte one for driver and other for rider

        // other way is to create another api(with only otp) and is called only by rider with rideId
        // by doing this way we can remove the Otp from rideDto

    }

    @PostMapping("/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                              @RequestBody RideStartDto rideStartDto){
        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDto.getOtp()));
    }

    @PostMapping("endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){

        return ResponseEntity.ok(driverService.endRide(rideId));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("/rateRider")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RateDto rateDto){
        return ResponseEntity.ok((driverService.rateRider(rateDto.getRideId(),rateDto.getRating())));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getAllMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                       @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        return ResponseEntity.ok(driverService
                .getAllMyRides(
                        PageRequest.of(pageOffSet, pageSize, Sort.by(Sort.Direction.DESC, "createdTime","id"))));
    }

    @PostMapping("/rateRider/{rideId}/{rating}")
    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId, @PathVariable Integer rating) {
        return ResponseEntity.ok(driverService.rateRider(rideId, rating));
    }

}
