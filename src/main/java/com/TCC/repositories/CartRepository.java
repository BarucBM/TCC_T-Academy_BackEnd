package com.TCC.repositories;

import com.TCC.domain.cart.Cart;
import com.TCC.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByCustomer(Customer customer);
}
