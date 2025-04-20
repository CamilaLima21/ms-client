package br.com.fiap.msclients.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void shouldCreateClientWithCorrectFields() {
        // Arrange
        long expectedId = 1L;
        String expectedName = "João Silva";
        String expectedEmail = "joao.silva@teste.com";
        String expectedCpf = "123.456.789-00";
        LocalDate expectedBirth = LocalDate.of(1990, 5, 15);

        Address expectedAddress = new Address(1L, "Rua das Acácias", "100", "54321-000", "Bloco B");

        // Act
        Client client = new Client(expectedId, expectedName, expectedEmail, expectedCpf, expectedBirth, expectedAddress);

        // Assert
        assertEquals(expectedId, client.getId());
        assertEquals(expectedName, client.getName());
        assertEquals(expectedEmail, client.getEmail());
        assertEquals(expectedCpf, client.getCpf());
        assertEquals(expectedBirth, client.getBirth());
        assertNotNull(client.getAddress());
        assertEquals(expectedAddress, client.getAddress());
    }

    @Test
    void shouldHandleNullAddress() {
        // Arrange
        long expectedId = 2L;
        String expectedName = "Maria Oliveira";
        String expectedEmail = "maria.oliveira@teste.com";
        String expectedCpf = "987.654.321-00";
        LocalDate expectedBirth = LocalDate.of(1985, 8, 30);

        // Act
        Client client = new Client(expectedId, expectedName, expectedEmail, expectedCpf, expectedBirth, null);

        // Assert
        assertEquals(expectedId, client.getId());
        assertEquals(expectedName, client.getName());
        assertEquals(expectedEmail, client.getEmail());
        assertEquals(expectedCpf, client.getCpf());
        assertEquals(expectedBirth, client.getBirth());
        assertNull(client.getAddress());
    }

    @Test
    void shouldCreateClientWithNullFields() {
        // Testando um cliente com valores nulos, exceto o id, se necessário.
        Client client = new Client(0L, null, null, null, null, null);

        assertNotNull(client);
        assertNull(client.getName());
        assertNull(client.getEmail());
        assertNull(client.getCpf());
        assertNull(client.getBirth());
        assertNull(client.getAddress());
    }

    @Test
    void shouldEqualClientsWithSameData() {
        // Arrange
        Address address = new Address(1L, "Rua das Acácias", "100", "54321-000", "Bloco B");
        Client client1 = new Client(1L, "Carlos Pereira", "carlos.pereira@teste.com", "111.222.333-44", LocalDate.of(1992, 3, 12), address);
        Client client2 = new Client(1L, "Carlos Pereira", "carlos.pereira@teste.com", "111.222.333-44", LocalDate.of(1992, 3, 12), address);

        // Act & Assert
        assertEquals(client1.getId(), client2.getId());
        assertEquals(client1.getName(), client2.getName());
        assertEquals(client1.getEmail(), client2.getEmail());
        assertEquals(client1.getCpf(), client2.getCpf());
        assertEquals(client1.getBirth(), client2.getBirth());
        assertEquals(client1.getAddress(), client2.getAddress());
    }


    @Test
    void shouldNotEqualClientsWithDifferentData() {
        // Arrange
        Address address1 = new Address(1L, "Rua das Acácias", "100", "54321-000", "Bloco B");
        Address address2 = new Address(2L, "Rua Nova", "200", "98765-432", "Apto 202");

        Client client1 = new Client(1L, "Carlos Pereira", "carlos.pereira@teste.com", "111.222.333-44", LocalDate.of(1992, 3, 12), address1);
        Client client2 = new Client(2L, "Carlos Pereira", "carlos.pereira@teste.com", "111.222.333-44", LocalDate.of(1992, 3, 12), address2);

        // Act & Assert
        assertNotEquals(client1, client2);  // Diferente id e endereço
    }
}
