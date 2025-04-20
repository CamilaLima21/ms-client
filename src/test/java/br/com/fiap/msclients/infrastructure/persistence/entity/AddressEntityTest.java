package br.com.fiap.msclients.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AddressEntityTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        AddressEntity address = new AddressEntity(1L, "Rua A", "123", "12345-000", "Apto 1");

        assertThat(address.getId()).isEqualTo(1L);
        assertThat(address.getStreet()).isEqualTo("Rua A");
        assertThat(address.getNumber()).isEqualTo("123");
        assertThat(address.getZipCode()).isEqualTo("12345-000");
        assertThat(address.getComplement()).isEqualTo("Apto 1");
    }

    @Test
    public void testSetters() {
        AddressEntity address = new AddressEntity();
        address.setId(2L);
        address.setStreet("Rua B");
        address.setNumber("456");
        address.setZipCode("54321-000");
        address.setComplement("Casa");

        assertThat(address.getId()).isEqualTo(2L);
        assertThat(address.getStreet()).isEqualTo("Rua B");
        assertThat(address.getNumber()).isEqualTo("456");
        assertThat(address.getZipCode()).isEqualTo("54321-000");
        assertThat(address.getComplement()).isEqualTo("Casa");
    }
}
