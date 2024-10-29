package com.TCC.services;

import org.springframework.stereotype.Service;

@Service
public class GeolocationService {

    public double calcularDistancia(double latitude, double longitude) {
        return someCalculations(latitude, longitude);
    }

    private double someCalculations(double latitude, double longitude) {
        return Math.sqrt(Math.pow(latitude, 2) + Math.pow(longitude, 2));
    }
}

