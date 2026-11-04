package com.dhivya.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByNameIgnoreCase(String name);
}
