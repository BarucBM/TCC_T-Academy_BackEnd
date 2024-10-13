package com.TCC.domain.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fullAddress;

    private String postalCode;

    private String streetName;

    private String streetNumber;

    private String addressComplement;

    private String neighborhood;

    private String city;

    private String state;

    private String country;

    private String latitude;

    private String longitude;
}
