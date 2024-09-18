package com.jsp.uber.repositories;

import com.jsp.uber.entites.Driver;
import com.jsp.uber.entites.Rating;
import com.jsp.uber.entites.Ride;
import com.jsp.uber.entites.Rider;
import org.hibernate.query.criteria.JpaDerivedRoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}
