package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.SignupDto;
import com.jsp.uber.dto.UserDto;
import com.jsp.uber.services.AuthService;

public class AuthServiceImpl implements AuthService {
    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public UserDto signup(SignupDto signup) {
        return null;
    }

    @Override
    public DriverDto onBoardNewDriver(String userId) {
        return null;
    }
}
