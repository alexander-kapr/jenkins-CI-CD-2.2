package com.softserve.controller;

import com.softserve.dto.ContactDto;
import com.softserve.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class MainController {

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    private ContactService contactService;

    @Autowired
    public MainController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", message);
        return "index";
    }

    @RequestMapping(value = { "/contacts" }, method = RequestMethod.GET)
    public String listContact(Model model) {
        model.addAttribute("contacts", contactService.listContact());
        log.info("***@RequestMapping " + contactService.listContact());
        return "contacts";
    }

    @RequestMapping(value = { "/contact" }, method = RequestMethod.GET)
    public String addContactPage(Model model) {
        ContactDto contactDto = new ContactDto();
        contactDto.setFirstname("type data");
        contactDto.setLastname("type data");
        contactDto.setEmail("type data");
        contactDto.setTelephone("type data");
        model.addAttribute("contactDto", contactDto);
        return "contact";
    }

    @RequestMapping(value = { "/contact" }, method = RequestMethod.POST)
    public String saveContact(Model model,
                             @ModelAttribute("ContactDto")
                             ContactDto contactDto) {
        if (contactDto.getFirstname() != null && contactDto.getFirstname().length() > 1
                && contactDto.getLastname() != null && contactDto.getLastname().length() > 1) {
            contactService.addContact(contactDto);
            return "redirect:/contacts";
        }
        log.error("***ERROR, errorMessage = " + errorMessage);
        //
        model.addAttribute("contactDto", contactDto);
        model.addAttribute("errorMessage", errorMessage);
        return "contact";
    }

}
