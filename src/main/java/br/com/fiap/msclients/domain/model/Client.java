package br.com.fiap.msclients.domain.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Client {

    private long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDate birth;
    private Address address;
       
}
