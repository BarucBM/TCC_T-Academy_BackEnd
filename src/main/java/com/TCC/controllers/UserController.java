package com.TCC.controllers;

import com.TCC.infra.security.TokenService;
import com.TCC.services.CompanyService;
import com.TCC.services.CustomerService;
import com.TCC.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final TokenService tokenService;

    public UserController(UserService userService, CompanyService companyService, CustomerService customerService, TokenService tokenService) {
        this.userService = userService;
        this.companyService = companyService;
        this.customerService = customerService;
        this.tokenService = tokenService;
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getUserProfile(HttpServletRequest request) {
        try {
            String token = tokenService.extractTokenFromRequest(request);

            return ResponseEntity.ok(userService.getUserProfile(tokenService.getUserIdFromToken(token)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/company")
    public ResponseEntity<Object> getUserCompany(HttpServletRequest request) {
        try {
            String userId = tokenService.getUserIdFromToken(tokenService.extractTokenFromRequest(request));

            return ResponseEntity.ok(companyService.findCompanyByUserId(userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/customer")
    public ResponseEntity<Object> getUserCustomer(HttpServletRequest request) {
        try {
            String userId = tokenService.getUserIdFromToken(tokenService.extractTokenFromRequest(request));

            return ResponseEntity.ok(customerService.findCustomerByUserId(userId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<String> uploadPhoto(@PathVariable() String id, @RequestPart("file") MultipartFile file) {
        try {
            userService.uploadPhoto(id, file);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/remove-photo")
    public ResponseEntity<String> removePhoto(@PathVariable() String id) {
        try {
            userService.removePhoto(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
