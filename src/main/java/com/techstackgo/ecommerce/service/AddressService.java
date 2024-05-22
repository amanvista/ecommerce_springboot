package com.techstackgo.ecommerce.service;

import com.techstackgo.ecommerce.exception.AddressException;
import com.techstackgo.ecommerce.exception.UserException;
import com.techstackgo.ecommerce.model.Address;
import com.techstackgo.ecommerce.model.User;

public interface AddressService {
    public Address saveAddress(Address address) throws AddressException;
}
