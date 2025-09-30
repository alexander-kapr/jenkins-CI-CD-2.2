package com.softserve.unit;

import com.softserve.dto.ContactDto;
import com.softserve.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactServiceTest {
    @Autowired
    private ContactService contactService;

    @DisplayName("Reading all contacts from the database")
    @Test
    @Order(1)
    public void checkContacts() {
        log.info("\t\t@Test checkContacts()");
        //
        List<ContactDto> expected = Arrays.asList(
                new ContactDto(1, "Ivan", "Ivanov", "ivan@gmail.com", "+380671234567"),
                new ContactDto(2, "Petro", "Petrov", "petro@gmail.com", "+380631234567")
        );
        List<ContactDto> actual = contactService.listContact();
        //
        Assertions.assertEquals(expected, actual, "checkContacts() Error, actual.size() = " + actual.size());
    }

    @DisplayName("Adding a new contact to the database")
    @Test
    @Order(2)
    public void checkAddContacts() {
        log.info("\t\t@Test checkAddContacts()");
        //
        ContactDto nextContactDto = new ContactDto(3, "John", "Doe", "john@gmail.com", "+380931234567");
        List<ContactDto> expected = Arrays.asList(
                new ContactDto(1, "Ivan", "Ivanov", "ivan@gmail.com", "+380671234567"),
                new ContactDto(2, "Petro", "Petrov", "petro@gmail.com", "+380631234567"),
                nextContactDto
        );
        contactService.addContact(nextContactDto);
        List<ContactDto> actual = contactService.listContact();
        //
        Assertions.assertEquals(expected, actual, "checkAddContacts() Error, actual.size() = " + actual.size());
    }

    @DisplayName("Deleting an existing record from the database")
    @Test
    @Order(3)
    public void checkRemoveContacts() {
        log.info("\t\t@Test checkAddContacts()");
        //
        ContactDto nextContactDto = new ContactDto(3, "John", "Doe", "john@gmail.com", "+380931234567");
        List<ContactDto> expected = Arrays.asList(
                new ContactDto(1, "Ivan", "Ivanov", "ivan@gmail.com", "+380671234567"),
                new ContactDto(2, "Petro", "Petrov", "petro@gmail.com", "+380631234567")
        );
        contactService.removeContact(3);
        List<ContactDto> actual = contactService.listContact();
        //
        Assertions.assertEquals(expected, actual, "checkRemoveContacts() Error, actual.size() = " + actual.size());
    }

    @DisplayName("Reading environment variables")
    @Test
    public void checkEnvironment() {
        // From Maven
        log.info("\t\t@Test ***surefire.java.version = " + System.getProperty("surefire.application.password"));
        // From OS
        log.info("\t\t@Test ***System.getenv(\"JAVA_HOME\") = " + System.getenv("JAVA_HOME"));
        log.info("\t\t@Test ***System.getenv(\"DEFAULT_PASS\") = " + System.getenv("DEFAULT_PASS"));
        // From Eclipse/Idea
        log.info("\t\t@Test ***System.getenv().MY_IDE = " + System.getenv().get("MY_IDE"));
    }
}
