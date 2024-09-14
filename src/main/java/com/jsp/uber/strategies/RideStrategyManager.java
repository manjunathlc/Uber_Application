package com.jsp.uber.strategies;

import com.jsp.uber.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.jsp.uber.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.jsp.uber.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.jsp.uber.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    private final RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;

    //if rider rating > 4.8 the match with highest rated drivers
    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
        if(riderRating >= 4.8){
            return highRatedDriverStrategy;
        }else return nearestDriverStrategy;


    }
    public RideFareCalculationStrategy rideFareCalculationStrategy(){
        //Peak hour 6PM to 9PM
        LocalTime surgeStartTime= LocalTime.of(18,0);
        LocalTime surgeEndTime= LocalTime.of(21,0);
        LocalTime currentTime= LocalTime.now();
        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime){
            return surgePricingFareCalculationStrategy;
        } else {
            return defaultFareCalculationStrategy;
        }
    }
}
