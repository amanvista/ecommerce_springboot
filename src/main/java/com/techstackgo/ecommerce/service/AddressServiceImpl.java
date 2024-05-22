package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.AddressException;
import com.techstackgo.ecommerce.model.Address;
import com.techstackgo.ecommerce.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressRepository addressRepository;
    @Override
    public Address saveAddress(Address address) throws AddressException {
        return addressRepository.save(address);
    }
}
