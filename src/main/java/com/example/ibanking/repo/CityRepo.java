package com.example.ibanking.repo;

import com.example.ibanking.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Long> {
    City findByCityName(String cityname);
}
