package com.jsp.uber.services.impl;

import com.jsp.uber.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_URL =
            "https://router.project-osrm.org/route/v1/driving/";
    @Override
    public double calculateDistance(Point src, Point dst) {

        try{
            String myUri = src.getX() + "," + src.getY() + ";" + dst.getX() + "," + dst.getY();
            OSRMResponseDto responseDto = RestClient.builder()
                    .baseUrl(OSRM_API_URL)
                    .build()
                    .get()
//                    .uri("{},{};{},{}" + src.getX(), src.getY(), dst.getX(), dst.getY())
                    .uri(myUri)
                    .retrieve()
                    .body(OSRMResponseDto.class);

                return responseDto.getRoutes().get(0).getDistance() / 1000.0;   //get(0) to get the first route
        } catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM "+ e.getMessage());
        }
    }
}

@Data
class OSRMResponseDto{
    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute{
    private Double distance;    // distance variable name should be same as OSRM distance variable
}
