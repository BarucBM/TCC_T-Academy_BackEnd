package com.TCC.controllers;

import com.TCC.services.GeolocationService; // Importe o GeolocationService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeolocationController {

    private final GeolocationService geolocationService;

    @Autowired
    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping("/calcularDistancia")
    public ResponseEntity<Double> calcularDistancia(@RequestParam String latitudeString, @RequestParam String longitudeString) {
        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);

        double distancia = geolocationService.calcularDistancia(latitude, longitude);
        return ResponseEntity.ok(distancia);
    }
}
