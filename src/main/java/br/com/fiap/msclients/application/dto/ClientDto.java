package br.com.fiap.msclients.application.dto;

import java.time.LocalDate;

public record ClientDto(
    long id,
    String name,
    String email,
    String cpf,
    LocalDate birth,
    long addressId
) {}
