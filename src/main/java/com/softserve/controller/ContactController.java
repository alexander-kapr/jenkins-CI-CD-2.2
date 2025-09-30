package com.softserve.controller;

import com.softserve.dto.ContactDto;
import com.softserve.dto.ResultDto;
import com.softserve.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //@GetMapping({"/contacts", "/index"})
    @GetMapping("/api/contacts")
    public List<ContactDto> listContacts() {
        return contactService.listContact();
    }

    @PostMapping(value = "/api/contact")
    public ContactDto addContact(
            @Valid
            @RequestBody
            ContactDto contactDto) {
        log.info("***contactDto = " + contactDto);
        return contactService.addContact(contactDto);
    }

    @DeleteMapping("/api/contact/{contactId}")
    public ResultDto deleteContact(
            @PathVariable("contactId")
            Integer contactId) {
        contactService.removeContact(contactId);
        return new ResultDto(true);
    }
}
