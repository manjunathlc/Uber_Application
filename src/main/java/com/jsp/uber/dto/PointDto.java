package com.jsp.uber.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {

    private Double[] coordinate;
    private String type = "Point";

    public PointDto(Double[] coordinate) {
        this.coordinate = coordinate;
    }
}
