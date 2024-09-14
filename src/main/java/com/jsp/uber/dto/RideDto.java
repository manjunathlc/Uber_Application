package com.jsp.uber.dto;

import com.jsp.uber.entites.enums.PaymentMethod;
import com.jsp.uber.entites.enums.RideStatus;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {

    private Long id;

    private PointDto pickUpLocation;

    private PointDto dropOffLocation;


    private LocalDateTime createdTime;

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
