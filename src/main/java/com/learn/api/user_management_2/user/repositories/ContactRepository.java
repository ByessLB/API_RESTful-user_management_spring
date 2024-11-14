package com.learn.api.user_management_2.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.api.user_management_2.user.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    
}
