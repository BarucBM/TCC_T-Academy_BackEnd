package com.TCC.controllers;

import com.TCC.domain.customer.CustomerDTO;
import com.TCC.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Object> updateCustomer (@PathVariable() String id, @RequestBody @Valid CustomerDTO customerDTO){
        try {
            customerService.updateCustomer(id, customerDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
