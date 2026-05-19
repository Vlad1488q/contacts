package com.example.contacts;

import com.example.contacts.dto.ContactDTO;
import com.example.contacts.entity.Contact;
import com.example.contacts.repository.ContactRepository;
import com.example.contacts.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    void getAllContacts_ShouldReturnList() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("Іван");
        contact.setLastName("Петренко");
        contact.setPhone("+380991234567");

        when(contactRepository.findAll()).thenReturn(List.of(contact));

        List<ContactDTO> result = contactService.getAllContacts();

        assertEquals(1, result.size());
        assertEquals("Іван", result.get(0).getFirstName());
        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void getContactById_ShouldReturnContact() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("Іван");
        contact.setLastName("Петренко");
        contact.setPhone("+380991234567");

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        ContactDTO result = contactService.getContactById(1L);

        assertEquals("Іван", result.getFirstName());
        assertEquals("Петренко", result.getLastName());
    }

    @Test
    void getContactById_ShouldThrowException_WhenNotFound() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> contactService.getContactById(99L));

        assertEquals("Контакт з id 99 не знайдено", exception.getMessage());
    }

    @Test
    void createContact_ShouldReturnCreatedContact() {
        ContactDTO dto = new ContactDTO();
        dto.setFirstName("Іван");
        dto.setLastName("Петренко");
        dto.setPhone("+380991234567");

        Contact saved = new Contact();
        saved.setId(1L);
        saved.setFirstName("Іван");
        saved.setLastName("Петренко");
        saved.setPhone("+380991234567");

        when(contactRepository.save(any(Contact.class))).thenReturn(saved);

        ContactDTO result = contactService.createContact(dto);

        assertEquals(1L, result.getId());
        assertEquals("Іван", result.getFirstName());
    }

    @Test
    void deleteContact_ShouldDeleteSuccessfully() {
        Contact contact = new Contact();
        contact.setId(1L);

        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        contactService.deleteContact(1L);

        verify(contactRepository, times(1)).deleteById(1L);
    }
}