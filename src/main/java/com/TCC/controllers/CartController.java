package com.TCC.controllers;

import com.TCC.domain.cart.Cart;
import com.TCC.domain.cart.CartItemDTO;
import com.TCC.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Cart> getCartByUserId (@PathVariable("id")String id ){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUser(id));
    }

    @PutMapping("/item/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Cart> addCartItem(@RequestBody @Valid CartItemDTO cartItemDTO, @PathVariable("id") String id ){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addCartItem(id, cartItemDTO));
    }

    @DeleteMapping("/item/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity deleteCartItem (@PathVariable("id") Long id, @RequestParam String customerId ){
        cartService.deleteCartItem(id, customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
