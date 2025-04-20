package br.com.fiap.msclients.infrastructure.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;

@SpringBootTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Deve encontrar endereço pelo zipCode (case insensitive)")
    void shouldFindAddressByZipCodeIgnoreCase() {
        // Arrange
        AddressEntity address = new AddressEntity();
        address.setStreet("Rua Dois de São Paulo");
        address.setNumber("314");
        address.setZipCode("05894-440");
        address.setComplement("N/A");

        addressRepository.save(address);

        // Act
        AddressEntity found = addressRepository.findByZipCode("05894-440");

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getStreet()).isEqualTo("Rua Dois de São Paulo");
        assertThat(found.getZipCode()).isEqualTo("05894-440");
    }

    @Test
    @DisplayName("Deve retornar null se não encontrar zipCode")
    void shouldReturnNullWhenZipCodeNotFound() {
        AddressEntity result = addressRepository.findByZipCode("00000-000");
        assertThat(result).isNull();
    }
}
