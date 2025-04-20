package br.com.fiap.msclients.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldCreateAddressWithCorrectFields() {
        // Arrange
        long expectedId = 1L;
        String expectedStreet = "Rua das Flores";
        String expectedNumber = "123";
        String expectedZipCode = "12345-678";
        String expectedComplement = "Apto 101";

        // Act
        Address address = new Address(expectedId, expectedStreet, expectedNumber, expectedZipCode, expectedComplement);

        // Assert
        assertEquals(expectedId, address.getId());
        assertEquals(expectedStreet, address.getStreet());
        assertEquals(expectedNumber, address.getNumber());
        assertEquals(expectedZipCode, address.getZipCode());
        assertEquals(expectedComplement, address.getComplement());
    }

    @Test
    void shouldNotCreateAddressWithNullFields() {

        Address address = new Address(0, null, null, null, null);

        assertNotNull(address);
        assertNull(address.getStreet());
        assertNull(address.getNumber());
        assertNull(address.getZipCode());
        assertNull(address.getComplement());
    }
}
