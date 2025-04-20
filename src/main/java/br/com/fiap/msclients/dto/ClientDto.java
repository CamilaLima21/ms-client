package br.com.fiap.msclients.dto;

import br.com.fiap.msclients.model.Address;

import java.time.LocalDate;

public record ClientDto(
    long id,
    String name,
    String email,
    String cpf,
    LocalDate birth,
    Address address) {
}
