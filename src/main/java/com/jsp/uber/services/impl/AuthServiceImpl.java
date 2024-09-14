package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.SignupDto;
import com.jsp.uber.dto.UserDto;
import com.jsp.uber.entites.User;
import com.jsp.uber.entites.enums.Role;
import com.jsp.uber.exceptions.RuntimeConflictException;
import com.jsp.uber.repositories.UserRepository;
import com.jsp.uber.services.AuthService;
import com.jsp.uber.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;

    @Override
    public String login(String email, String password) {    // spring Security
        return "";
    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {

       User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
       if(user != null) {
           throw new RuntimeConflictException("Email already exists with email " + signupDto.getEmail());
       }

        User mappedUser= modelMapper.map(signupDto,User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));  // initially set to rider
        User savedUser = userRepository.save(mappedUser);

        // create user related entities
        // add Rider , Wallet

        // TODO add Wallet related services here

        riderService.createNewRider(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onBoardNewDriver(String userId) {
        return null;
    }
}
