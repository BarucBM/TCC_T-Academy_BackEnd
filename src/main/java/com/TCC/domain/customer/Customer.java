package com.TCC.domain.customer;

import com.TCC.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "customers")
@Entity()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private User user;

    private String name;

    private String address;

    private String phone;
}
