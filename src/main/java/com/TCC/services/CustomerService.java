package com.TCC.services;

import com.TCC.domain.customer.Customer;
import com.TCC.domain.customer.CustomerDTO;
import com.TCC.domain.customer.CustomerUserDTO;
import com.TCC.domain.user.User;
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
    private final AddressService addressService;

    public CustomerService(CustomerRepository customerRepository, UserService userService, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.addressService = addressService;
    }

    public Customer findCustomerById(String id) {
        Customer customer = this.customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        return customer;
    }

    @Transactional
    public Customer createCustomerWithUser(CustomerUserDTO data) {
        User user = new User();
        BeanUtils.copyProperties(data.user(), user);
        user.setRole(UserRole.CUSTOMER);
        user.setHasGoogleAuth(true);

        Customer customer = new Customer();
        BeanUtils.copyProperties(data.customer(), customer);

        customer.setUser(userService.createUser(user));
        customer.setAddress(addressService.createAddress(data.customer().address()));

        return customerRepository.save(customer);
    }

    @Transactional
    public void updateCustomer(String id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        BeanUtils.copyProperties(customerDTO, existingCustomer);

        customerRepository.save(existingCustomer);
    }
}