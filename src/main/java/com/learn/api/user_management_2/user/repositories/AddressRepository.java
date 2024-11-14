package com.learn.api.user_management_2.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.api.user_management_2.user.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    
}
