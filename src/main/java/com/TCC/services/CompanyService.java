package com.TCC.services;

import com.TCC.domain.company.Company;
import com.TCC.domain.company.CompanyDTO;
import com.TCC.domain.company.CompanyResponseDTO;
import com.TCC.domain.company.CompanyUserDTO;
import com.TCC.domain.customer.Customer;
import com.TCC.domain.user.User;
import com.TCC.domain.user.UserResponseDTO;
import com.TCC.domain.user.UserRole;
import com.TCC.repositories.CompanyRepository;
import com.TCC.specifications.CompanySpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    public List<Company> getAllCompanies(String name, String address, String phone, String email) {
        Specification<Company> spec = Specification
                .where(CompanySpecification.emailContains(email))
                .and(CompanySpecification.phoneContains(phone))
                .and(CompanySpecification.nameContains(name))
                .and(CompanySpecification.adrressContains(address));
        return companyRepository.findAll(spec);
    }


    public CompanyResponseDTO findCompanyById(String id) {
        Company company = this.companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + id));

        return this.getCompanyResponseDTO(company);
    }

    @Transactional
    public CompanyResponseDTO createCompanyWithUser(CompanyUserDTO data) {
        User user = new User();
        BeanUtils.copyProperties(data.user(), user);
        user.setRole(UserRole.CUSTOMER);

        Company company = new Company();
        BeanUtils.copyProperties(data.company(), company);

        company.setUser(userService.createUser(user));

        return this.getCompanyResponseDTO(companyRepository.save(company));
    }

    @Transactional
    public void updateCompany(String id, CompanyDTO companyDTO) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + id));

        BeanUtils.copyProperties(companyDTO, existingCompany);

        companyRepository.save(existingCompany);
    }

    private CompanyResponseDTO getCompanyResponseDTO(Company company) {
        return new CompanyResponseDTO(
                company.getId(),
                company.getName(),
                company.getAddress(),
                company.getPhone(),
                company.getDuns(),
                new UserResponseDTO(company.getUser().getId(), company.getUser().getEmail(), company.getUser().getGoogleApiToken())
        );
    }
}
