package br.com.fiap.msclients.infrastructure.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve encontrar endereço pelo zipCode (case insensitive)")
    void shouldFindAddressByZipCodeIgnoreCase() {
        AddressEntity address = new AddressEntity();
        address.setStreet("Rua Dois de São Paulo");
        address.setNumber("314");
        address.setZipCode("05894-440");
        address.setComplement("N/A");

        addressRepository.save(address);

        AddressEntity found = addressRepository.findByZipCode("05894-440".toLowerCase());

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

    @Test
    @DisplayName("Deve encontrar endereço mesmo com zipCode em maiúsculas")
    void shouldFindAddressWithUpperCaseZipCode() {
        AddressEntity address = new AddressEntity();
        address.setStreet("Avenida Brasil");
        address.setNumber("1000");
        address.setZipCode("12345-678");
        address.setComplement("Bloco B");

        addressRepository.save(address);

        AddressEntity found = addressRepository.findByZipCode("12345-678".toUpperCase());

        assertThat(found).isNotNull();
        assertThat(found.getZipCode()).isEqualTo("12345-678");
    }
}
