package com.TCC.services;

import com.TCC.domain.cart.Cart;
import com.TCC.domain.cart.CartItemDTO;
import com.TCC.domain.customer.Customer;
import com.TCC.repositories.CartRepository;
import com.TCC.repositories.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Cart getCartByUser (String userId){
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found!"));

        return cartRepository.findByCustomer(customer)
                .orElseGet(()-> createCustomerCart(customer));
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found!"));
    }

    public Cart addCartItem(Long id, CartItemDTO cartItemDTO) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found!"));
        BeanUtils.copyProperties(cartItemDTO, cart);
        return cartRepository.save(cart);
    }

    private Cart createCustomerCart(Customer customer){
        Cart cart = new Cart();
        cart.setCustomer(customer);
        return cartRepository.save(cart);
    }
}
