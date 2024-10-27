package com.TCC.controllers;

import com.TCC.domain.company.Company;
import com.TCC.domain.company.CompanyDTO;
import com.TCC.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

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
    public ResponseEntity<Company> getCompanyById (@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findCompanyById(id));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Company> updateCompany (@PathVariable(value = "id") String id, @RequestBody @Valid CompanyDTO companyDTO){
        companyService.updateCompany(id, companyDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
