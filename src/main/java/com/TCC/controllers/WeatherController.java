package com.TCC.controllers;

import com.TCC.domain.weather.Weather;
import com.TCC.domain.weather.WeatherDTO;
import com.TCC.services.WeatherService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;


    @GetMapping("/forecast")
    public ResponseEntity<String> getWeatherForecast(@RequestParam String city) {
        try {
            String forecast = weatherService.getWeatherForecast(city);
            return ResponseEntity.status(HttpStatus.OK).body(forecast);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar a previsão do tempo: " + e.getMessage());
        }
    }
}
