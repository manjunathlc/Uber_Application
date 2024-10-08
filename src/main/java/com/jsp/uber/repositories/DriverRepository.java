package com.jsp.uber.repositories;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

//    Geo-spatial has methods to calculate distance like
//    ST_Distance(point1,point2)
//    ST_DWithin(point1, 1000)
//
//    (like seq_database has MAX AVG etc.)
    // However, please note that the usage of ST_DISTANCE requires a spatial database.


    @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickUpLocation) AS distance " +
            "FROM driver d " +
            "WHERE d.available=TRUE AND ST_DWithin(d.current_location, :pickUpLocation, 10000) " +
            "ORDER BY Distance " +
            "LIMIT 10", nativeQuery = true )
    List<Driver> findTenNearestDrivers(Point pickUpLocation);

    @Query(value = "SELECT d.* " +
            "FROM driver d " +
            "WHERE d.available = True AND ST_DWithin(d.current_location, :pickUpLocation, 15000) " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10 ", nativeQuery = true)
    List<Driver> findTenNearestTopRatedDrivers(Point pickUpLocation);

    Optional<Driver> findByUser(User user);
}
