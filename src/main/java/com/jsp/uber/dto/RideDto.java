package com.jsp.uber.dto;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.enums.PaymentMethod;
import com.jsp.uber.entites.enums.RideStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public class RideDto {

    private Long id;

    private Point pickUpLocation;

    private Point dropOffLocation;


    private LocalDateTime createdTime;

    private RiderDto riderDto;

    private DriverDto driverDto;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
