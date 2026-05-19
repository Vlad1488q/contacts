package com.example.contacts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ім'я не може бути порожнім")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Прізвище не може бути порожнім")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Телефон не може бути порожнім")
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Невірний формат телефону")
    @Column(nullable = false, unique = true)
    private String phone;

    @Email(message = "Невірний формат email")
    private String email;

    private String address;
    private String city;
}