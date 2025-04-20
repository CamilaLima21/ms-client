package br.com.fiap.msclients.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Address {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NotNull(message = "Street name cannot be null.")
    @Size(min = 10, max = 50, message = "The street name must be between 10 and 50 characters long.")
    private String street;

    @Column(nullable = false)
    @NotNull(message = "Number cannot be null.")
    private String number;

    @Column(nullable = false)
    @NotNull(message = "The zip code cannot be null.")
    private String zipCode;

    @Column(nullable = false)
    private String complement;
}
