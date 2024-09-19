package com.jsp.uber.controllers;

import com.jsp.uber.dto.*;
import com.jsp.uber.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto){
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId,
                                               @RequestBody OnBoardNewDriverDto onBoardNewDriverDto){
        return new ResponseEntity<>(authService.onBoardNewDriver(userId,
                onBoardNewDriverDto.getVehicleId()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                           HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String[] tokens = authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        Cookie cookie = new Cookie("token", tokens[1]);
//        cookie.setSecure(true); // for https requests
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(tokens[0])); // accessToken
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found inside the cookie"));

         String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));

//        if (refreshToken == null) {
//        Cookie cookie = request.getCookies().stream()
//               .filter(c -> c.getName().equals("token"))
//               .findFirst()
//               .orElse(null);
//
//        if (cookie == null) {
//            return ResponseEntity.badRequest().body(new LoginResponseDto("Invalid token"));
//        }
//
//        String[] tokens = authService.login(null, null); // null for email and password
//
//        cookie = new Cookie("token", tokens[1]);
    }
}
