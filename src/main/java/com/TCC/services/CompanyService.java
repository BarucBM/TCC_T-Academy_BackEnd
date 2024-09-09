package com.TCC.services;

import com.TCC.domain.company.Company;
import com.TCC.domain.company.CompanyDTO;
import com.TCC.repositories.CompanyRepository;
import com.TCC.specifications.CompanySpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies(String name, String address, String phone, String email){
        Specification<Company> spec = Specification
                .where(CompanySpecification.emailContains(email))
                .and(CompanySpecification.phoneContains(phone))
                .and(CompanySpecification.nameContains(name))
                .and(CompanySpecification.adrressContains(address));
        return companyRepository.findAll(spec);
    }

    public Company getCompanyById (Long id){
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found!"));
    }

    public String deleteCompany (Long id){
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found!"));
        companyRepository.delete(company);
        return company.getName() + " company deleted!";
    }

    public Company createCompany (Company company){
        return companyRepository.save(company);
    }

    public Company updateCompany (Long id, CompanyDTO companyDTO){
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found!"));
        BeanUtils.copyProperties(companyDTO, company);
        return companyRepository.save(company);
    }
}
