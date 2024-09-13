package com.jsp.uber.configs;

import com.jsp.uber.dto.PointDto;
import com.jsp.uber.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfigs {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(PointDto.class, Point.class).setConverter(context ->{
            PointDto pointDto = context.getSource();
            return GeometryUtil.createPoint(pointDto);
        });

        mapper.typeMap(Point.class, PointDto.class).setConverter(context -> {
            Point point = context.getSource();
            Double coordinate[] = {
                    point.getX(),
                    point.getY()
            };
            return new PointDto(coordinate);
        });
        return mapper;
    }
}
