package br.com.fiap.msclients.infrastructure.persistence.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientEntityTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        AddressEntity address = new AddressEntity(1L, "Rua A", "123", "12345-000", "Apto 1");

        ClientEntity client = new ClientEntity(
            10L,
            "João Silva",
            "joao.silva@gmail.com",
            "12345678900",
            LocalDate.of(1990, 1, 1),
            address
        );

        assertThat(client.getId()).isEqualTo(10L);
        assertThat(client.getName()).isEqualTo("João Silva");
        assertThat(client.getEmail()).isEqualTo("joao.silva@gmail.com");
        assertThat(client.getCpf()).isEqualTo("12345678900");
        assertThat(client.getBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(client.getAddress()).isEqualTo(address);
    }

    @Test
    public void testSetters() {
        AddressEntity address = new AddressEntity(2L, "Rua B", "456", "54321-000", "Casa");
        ClientEntity client = new ClientEntity();

        client.setId(20L);
        client.setName("Maria Oliveira");
        client.setEmail("maria.oliveira@gmail.com");
        client.setCpf("98765432100");
        client.setBirth(LocalDate.of(1985, 5, 15));
        client.setAddress(address);

        assertThat(client.getId()).isEqualTo(20L);
        assertThat(client.getName()).isEqualTo("Maria Oliveira");
        assertThat(client.getEmail()).isEqualTo("maria.oliveira@gmail.com");
        assertThat(client.getCpf()).isEqualTo("98765432100");
        assertThat(client.getBirth()).isEqualTo(LocalDate.of(1985, 5, 15));
        assertThat(client.getAddress()).isEqualTo(address);
    }
}
