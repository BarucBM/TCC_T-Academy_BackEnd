package com.TCC.controllers;

import com.TCC.domain.company.CompanyUserDTO;
import com.TCC.domain.customer.CustomerUserDTO;
import com.TCC.infra.security.TokenService;
import com.TCC.services.CompanyService;
import com.TCC.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService, CompanyService companyService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.companyService = companyService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register/customer")
    public ResponseEntity<Object> registerCustomer(@RequestBody @Valid CustomerUserDTO data) {
        try {
            return ResponseEntity.ok(customerService.createCustomerWithUser(data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/company")
    public ResponseEntity<Object> registerCompany(@RequestBody @Valid CompanyUserDTO data) {
        try {
            return ResponseEntity.ok(companyService.createCompanyWithUser(data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
