package com.TCC.controllers;

import com.TCC.domain.cart.Cart;
import com.TCC.domain.cart.CartItemDTO;
import com.TCC.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/customer/{id}")
    public ResponseEntity<Cart> getCartByUserId (@PathVariable("id")String id ){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCartByUser(id));
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Cart> addCartItem(@RequestBody CartItemDTO cartItemDTO, @PathVariable Long id ){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.addCartItem(id, cartItemDTO));
    }

}