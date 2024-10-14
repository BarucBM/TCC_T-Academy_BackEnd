package com.TCC.services;

import com.TCC.domain.address.Address;
import com.TCC.domain.address.AddressDTO;
import com.TCC.repositories.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address getAddressById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with ID: " + id));
    }

    public Address createAddress(AddressDTO addressDTO) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address);

        return addressRepository.save(address);
    }

    public Address updateAddress(String id, AddressDTO addressDTO) {
        Address address = this.getAddressById(id);
        BeanUtils.copyProperties(addressDTO, address);

        return addressRepository.save(address);
    }

    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }
}
