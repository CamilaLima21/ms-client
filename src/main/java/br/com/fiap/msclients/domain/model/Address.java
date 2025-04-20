package br.com.fiap.msclients.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Address {
    private long id;
    private String street;
    private String number;
    private String zipCode;
    private String complement;
}

