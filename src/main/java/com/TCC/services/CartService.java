package com.TCC.services;

import com.TCC.domain.cart.Cart;
import com.TCC.domain.cart.CartItem;
import com.TCC.domain.cart.CartItemDTO;
import com.TCC.domain.customer.Customer;
import com.TCC.domain.user.User;
import com.TCC.repositories.CartItemRepository;
import com.TCC.repositories.CartRepository;
import com.TCC.repositories.CustomerRepository;
import com.TCC.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart getCartByUser (String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found!"));

        Customer customer = customerRepository.findByUserId(user.getId());

        return cartRepository.findByCustomer(customer)
                .orElseGet(()-> createCustomerCart(customer));
    }

    public Cart getCartById(String id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found!"));
    }

    public Cart addCartItem(String userId, CartItemDTO cartItemDTO) {
        boolean flag = false;

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found!"));

        Customer customer = customerRepository.findByUserId(user.getId());

        customer.getCart().getCartEvents().stream().filter(item -> item.getEventId().equals(cartItemDTO.eventId()))
                    .findFirst()
                    .ifPresentOrElse(
                        item ->{
                            item.setQuantity(item.getQuantity() + cartItemDTO.quantity());
                        },
                        () -> {
                            customer.getCart().getCartEvents()
                                .add(cartItemRepository.save(new CartItem(customer.getCart(), cartItemDTO.eventId(), cartItemDTO.quantity())));
                        }
                    );

        return cartRepository.save(customer.getCart());
    }

    public void deleteCartItem (Long id, String userId){
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found!"));
        cartItemRepository.delete(cartItem);
        Customer customer = customerRepository.findByUserId(userId);
        Cart cart = cartRepository
                .findByCustomer(customer)
                .orElseThrow(() -> new RuntimeException("Cart not found!"));

        cart.getCartEvents().removeIf(event -> Objects.equals(event.getId(), id));
    }

    private Cart createCustomerCart(Customer customer){
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartEvents(new ArrayList<>());
        customer.setCart(cart);
        return cartRepository.save(cart);
    }
}
