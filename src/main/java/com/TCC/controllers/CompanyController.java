package com.TCC.controllers;

import com.TCC.domain.company.Company;
import com.TCC.domain.company.CompanyDTO;
import com.TCC.services.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies (
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email
    ){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies(name, address, phone, email));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Company> getCompanyById (@PathVariable() String id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findCompanyById(id));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Object> updateCompany (@PathVariable() String id, @RequestBody @Valid CompanyDTO companyDTO){
        try {
            companyService.updateCompany(id, companyDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
