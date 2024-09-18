package com.jsp.uber.controllers;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.OnBoardNewDriverDto;
import com.jsp.uber.dto.SignupDto;
import com.jsp.uber.dto.UserDto;
import com.jsp.uber.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId,
                                               @RequestBody OnBoardNewDriverDto onBoardNewDriverDto){
        return new ResponseEntity<>(authService.onBoardNewDriver(userId,
                onBoardNewDriverDto.getVehicleId()), HttpStatus.CREATED);
    }
}
