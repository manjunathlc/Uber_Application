package com.jsp.uber.dto;

import com.jsp.uber.entites.Rider;
import com.jsp.uber.entites.enums.PaymentMethod;
import com.jsp.uber.entites.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    private Long id;

    private PointDto pickUpLocation;

    private PointDto dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto riderDto;

    private PaymentMethod paymentMethod;

    private RideRequestStatus rideRequestStatus;
}
