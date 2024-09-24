package com.TCC.repositories;

import com.TCC.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, String> {
}
