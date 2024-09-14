package com.jsp.uber.dto;

import com.jsp.uber.entites.enums.PaymentMethod;
import com.jsp.uber.entites.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long id;

    private PointDto pickUpLocation;

    private PointDto dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto rider;
    private Double fare;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
