package com.jsp.uber.services;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.SignupDto;
import com.jsp.uber.dto.UserDto;

public interface AuthService {

    String login(String email, String password);        // returns tokens

    UserDto signup(SignupDto signup);

    DriverDto onBoardNewDriver(String userId);
}
