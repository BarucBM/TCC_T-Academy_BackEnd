package com.TCC.services;


import com.TCC.domain.weather.Weather;
import com.TCC.domain.weather.WeatherDTO;
import com.TCC.repositories.WeatherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;


    public List<Weather> getAllWeathers(){
        return weatherRepository.findAll();
    }

    public Weather getWeatherById (String id){
        return weatherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Weather not found!"));
    }

    public String deleteWeather (String id){
        Weather weather = weatherRepository.findById(id).orElseThrow(() -> new RuntimeException("Weather not found!"));
        weatherRepository.delete(weather);
        return "Weather deleted!";
    }

    public Weather createWeather (Weather weather){
        return weatherRepository.save(weather);
    }

    public Weather updateWeather (String id, WeatherDTO weatherDTO){
        Weather weather = weatherRepository.findById(id).orElseThrow(() -> new RuntimeException("Weather not found!"));
        BeanUtils.copyProperties(weatherDTO, weather);
        return weatherRepository.save(weather);
    }
}
