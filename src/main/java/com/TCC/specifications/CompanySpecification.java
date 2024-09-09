package com.TCC.specifications;

import com.TCC.domain.company.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecification {
    public static Specification<Company> nameContains(String name){
        return ((root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" +name.toLowerCase()+ "%"));
    }

    public static Specification<Company> adrressContains(String address){
        return ((root, query, criteriaBuilder) ->
                address == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("address")),"%" + address.toLowerCase() + "%"));
    }

    public static Specification<Company> phoneContains(String phone){
        return ((root, query, criteriaBuilder) ->
                phone == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")),"%" + phone.toLowerCase() + "%"));
    }

    public static Specification<Company> emailContains(String email){
        return ((root, query, criteriaBuilder) ->
                email == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),"%" + email.toLowerCase() + "%"));
    }

}
