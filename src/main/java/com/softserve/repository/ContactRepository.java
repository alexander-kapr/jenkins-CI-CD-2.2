package com.softserve.repository;

import com.softserve.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Contact findByFirstname(String firstname);
}
