package com.softserve.service;

import com.softserve.dto.ContactDto;

import java.util.List;

public interface ContactService {

    public ContactDto addContact(ContactDto contactDto);

    public List<ContactDto> listContact();

    public void removeContact(Integer id);
}
