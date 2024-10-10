package com.TCC.services;

import com.TCC.domain.address.Address;
import com.TCC.domain.address.AddressDTO;
import com.TCC.repositories.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address);

        return addressRepository.save(address);
    }
}
