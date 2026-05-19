package com.example.contacts.service;

import com.example.contacts.dto.ContactDTO;
import com.example.contacts.entity.Contact;
import com.example.contacts.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
    private final ContactRepository contactRepository;

    public List<ContactDTO> getAllContacts() {
        logger.info("Отримання всіх контактів");
        return contactRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContactDTO getContactById(Long id) {
        logger.info("Отримання контакту з id: {}", id);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Контакт з id " + id + " не знайдено"));
        return toDTO(contact);
    }

    @Transactional
    public ContactDTO createContact(ContactDTO dto) {
        logger.info("Створення нового контакту: {} {}", dto.getFirstName(), dto.getLastName());
        Contact contact = toEntity(dto);
        return toDTO(contactRepository.save(contact));
    }

    @Transactional
    public ContactDTO updateContact(Long id, ContactDTO dto) {
        logger.info("Оновлення контакту з id: {}", id);
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Контакт з id " + id + " не знайдено"));
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        contact.setAddress(dto.getAddress());
        contact.setCity(dto.getCity());
        return toDTO(contactRepository.save(contact));
    }

    @Transactional
    public void deleteContact(Long id) {
        logger.info("Видалення контакту з id: {}", id);
        contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Контакт з id " + id + " не знайдено"));
        contactRepository.deleteById(id);
    }

    private ContactDTO toDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        dto.setAddress(contact.getAddress());
        dto.setCity(contact.getCity());
        return dto;
    }

    private Contact toEntity(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        contact.setAddress(dto.getAddress());
        contact.setCity(dto.getCity());
        return contact;
    }
}