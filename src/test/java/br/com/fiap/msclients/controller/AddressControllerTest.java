package br.com.fiap.msclients.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.fiap.msclients.dto.AddressDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Address;
import br.com.fiap.msclients.service.AddressService;

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchAddress() throws ResourceNotFoundException {
        // Arrange
        List<Address> mockAddresses = Arrays.asList(
            new Address(),
            new Address()
        );
        when(addressService.searchAddress()).thenReturn(mockAddresses);

        // Act
        ResponseEntity<List<Address>> response = addressController.searchAddress();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(addressService, times(1)).searchAddress();
    }

    @Test
    void testSearchAddressById() throws ResourceNotFoundException {
        // Arrange
        long id = 1L;
        Address mockAddress = new Address();
        when(addressService.searchAddressById(id)).thenReturn(mockAddress);

        // Act
        ResponseEntity<Address> response = addressController.searchAddressById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAddress, response.getBody());
        verify(addressService, times(1)).searchAddressById(id);
    }

    @Test
    void testSearchAddressByCep() throws ResourceNotFoundException {
        // Arrange
        String cep = "12345678";
        Address mockAddress = new Address();
        when(addressService.searchAddressByCep(cep)).thenReturn(mockAddress);

        // Act
        ResponseEntity<Address> response = addressController.searchAddressByCep(cep);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAddress, response.getBody());
        verify(addressService, times(1)).searchAddressByCep(cep);
    }

    @Test
    void testCreateAddress() throws ResourceNotFoundException {
        // Arrange
        AddressDto addressDto = new AddressDto(0, null, null, null, null);
        AddressDto savedAddress = new AddressDto(0, null, null, null, null);
        when(addressService.createAddress(addressDto)).thenReturn(savedAddress);

        // Act
        ResponseEntity<AddressDto> response = addressController.createAddress(addressDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedAddress, response.getBody());
        verify(addressService, times(1)).createAddress(addressDto);
    }

    @Test
    void testDeleteAddress() throws ResourceNotFoundException {
        // Arrange
        long id = 1L;
        String deleteMessage = "Address deleted successfully";
        when(addressService.deleteAddress(id)).thenReturn(deleteMessage);

        // Act
        ResponseEntity<String> response = addressController.deleteAddress(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deleteMessage, response.getBody());
        verify(addressService, times(1)).deleteAddress(id);
    }
}

