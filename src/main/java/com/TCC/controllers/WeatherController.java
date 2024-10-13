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

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<List<Weather>> getAllWeather(){
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.getAllWeathers());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Weather> getWeatherById (@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.getWeatherById(id));
    }

    @PostMapping
    public ResponseEntity<Weather> createWeather (@RequestBody @Valid WeatherDTO weatherDTO){
        Weather weather = new Weather();
        BeanUtils.copyProperties(weatherDTO, weather);
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.createWeather(weather));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWeather (@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.deleteWeather(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Weather> updateEvent (@PathVariable(value = "id") String id, @RequestBody @Valid WeatherDTO weatherDTO){
        return ResponseEntity.status(HttpStatus.OK).body(weatherService.updateWeather(id, weatherDTO));
    }
}
