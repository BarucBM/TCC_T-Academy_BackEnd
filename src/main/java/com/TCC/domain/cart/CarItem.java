package com.TCC.domain.cart;

import com.TCC.domain.event.Event;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Car_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Event event;
}
