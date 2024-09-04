package com.TCC.domain.event;


import com.TCC.domain.company.Company;
import com.TCC.domain.notification.Notification;
import com.TCC.domain.user.UserEvent;
import com.TCC.domain.weather.Weather;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "event")
    private List<Weather> weathers;

    @OneToMany(mappedBy = "event")
    private List<Notification> notification;

    @OneToMany(mappedBy = "event")
    private List<UserEvent> userEvent;

    @ManyToOne
    private Company company;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdAt;

    private String location;

    private String description;

    //TODO - Possível troca para um enum indicando qual impacto do clima
    private Boolean weatherImpact;



}
