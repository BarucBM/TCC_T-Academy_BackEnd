package com.TCC.domain.cart;

import com.TCC.domain.event.Event;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Cart cart;

    private Integer quantity;

}
