package com.jsp.uber.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private Double[] coordinate;
    private String type = "Point";

    public PointDto(Double[] coordinate) {
        this.coordinate = coordinate;
    }
}
