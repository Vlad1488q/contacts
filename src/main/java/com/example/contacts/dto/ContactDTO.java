package com.example.contacts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ContactDTO {

    private Long id;

    @NotBlank(message = "Ім'я не може бути порожнім")
    private String firstName;

    @NotBlank(message = "Прізвище не може бути порожнім")
    private String lastName;

    @NotBlank(message = "Телефон не може бути порожнім")
    @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Невірний формат телефону")
    private String phone;

    @Email(message = "Невірний формат email")
    private String email;

    private String address;
    private String city;
}