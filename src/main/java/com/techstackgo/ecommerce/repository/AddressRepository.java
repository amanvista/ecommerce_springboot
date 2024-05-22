package com.techstackgo.ecommerce.repository;

import com.techstackgo.ecommerce.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
