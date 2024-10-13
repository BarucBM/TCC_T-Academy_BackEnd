package com.TCC.domain.weather;

import com.TCC.domain.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Event event;

    private String location;

    private Float temperature;

    private Float humidity;

    private String condition;

    private Float windSpeed;

    private LocalDateTime dateTime;

    private String forecastType;
}
