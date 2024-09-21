package com.TCC.repositories;

import com.TCC.domain.event.Event;
import com.TCC.domain.weather.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, String> {
}
