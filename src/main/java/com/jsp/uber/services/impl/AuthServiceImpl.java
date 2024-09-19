package com.jsp.uber.services.impl;

import com.jsp.uber.configs.SecurityConfig;
import com.jsp.uber.dto.DriverDto;
import com.jsp.uber.dto.SignupDto;
import com.jsp.uber.dto.UserDto;
import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.User;
import com.jsp.uber.entites.enums.Role;
import com.jsp.uber.exceptions.ResourceNotFoundException;
import com.jsp.uber.exceptions.RuntimeConflictException;
import com.jsp.uber.repositories.UserRepository;
import com.jsp.uber.security.JWTService;
import com.jsp.uber.services.AuthService;
import com.jsp.uber.services.DriverService;
import com.jsp.uber.services.RiderService;
import com.jsp.uber.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {    // spring Security

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};
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
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
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

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for user " + userId));

//        user.getRoles().removeIf(role -> role == Role.ADMIN);  // remove admin role if exists;
//        userRepository.save(user);  // update user roles
//

        return jwtService.generateAccessToken(user);

    }
}
