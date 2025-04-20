package br.com.fiap.msclients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "client")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Client {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotNull(message = "Name cannot be null.")
    @Size(min = 10, max = 50, message = "The name must be between 10 and 50 characters long.")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Email cannot be null.")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "The CPF cannot be empty.")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "Birth cannot be null")
    @Past
    private LocalDate birth;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
