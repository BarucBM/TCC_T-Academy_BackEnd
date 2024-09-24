package com.TCC.domain.company;

import com.TCC.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Event[] events;

    private String duns;

    private String name;

    private String address;

    private String phone;

    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();
}
