package com.TCC.services;

import com.TCC.domain.customer.Customer;
import com.TCC.domain.customer.CustomerDTO;
import com.TCC.domain.customer.CustomerResponseDTO;
import com.TCC.domain.customer.CustomerUserDTO;
import com.TCC.domain.user.User;
import com.TCC.domain.user.UserResponseDTO;
import com.TCC.domain.user.UserRole;
import com.TCC.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PreferenceService preferenceService;

    public CustomerService(CustomerRepository customerRepository, UserService userService, PreferenceService preferenceService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.preferenceService = preferenceService;
    }

    public CustomerResponseDTO findCustomerById(String id) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        return this.getCustomerResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO createCustomerWithUser(CustomerUserDTO data) {
        if(userService.existsUserByEmail(data.user().email())) {
            return null;
        }

        User user = new User();
        BeanUtils.copyProperties(data.user(), user);
        user.setRole(UserRole.CUSTOMER);

        Customer customer = new Customer();
        BeanUtils.copyProperties(data.customer(), customer);

        customer.setUser(userService.createUser(user));
        preferenceService.newUserPreferences(customer.getUser().getId());
        return this.getCustomerResponseDTO(customerRepository.save(customer));
    }

    @Transactional
    public void updateCustomer(String id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        BeanUtils.copyProperties(customerDTO, existingCustomer);

        customerRepository.save(existingCustomer);
    }

    private CustomerResponseDTO getCustomerResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                new UserResponseDTO(customer.getUser().getId(), customer.getUser().getEmail(), customer.getUser().getGoogleApiToken())
        );
    }
}