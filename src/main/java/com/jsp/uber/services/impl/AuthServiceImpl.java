package com.jsp.uber.services.impl;

import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.SignupDto;
import com.jsp.uber.dto.UserDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.User;
import com.jsp.uber.entites.enums.Role;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.exceptions.RuntimeConflictException;
import com.jsp.uber.repositories.UserRepository;
import com.jsp.uber.services.AuthService;
import com.jsp.uber.services.DriverService;
import com.jsp.uber.services.RiderService;
import com.jsp.uber.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.jsp.uber.entites.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;

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

        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onBoardNewDriver(Long userId, String vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found for user " + userId));

        if(user.getRoles().contains(DRIVER)){
            throw new RuntimeConflictException("User with id " + userId+" is already a driver");
        }

//        if(user.getRoles().contains(Role.RIDER)) {
//            throw new RuntimeException("User is not a driver");
//        }

        Driver createDriver=Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(DRIVER);
        userRepository.save(user);  // update user roles
        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver,DriverDto.class);
    }
}
