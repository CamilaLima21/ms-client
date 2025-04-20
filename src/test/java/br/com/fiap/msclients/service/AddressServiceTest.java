package br.com.fiap.msclients.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.fiap.msclients.dto.AddressDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Address;
import br.com.fiap.msclients.repository.AddressRepository;

class AddressServiceTest {

	@Mock
	private AddressRepository addressRepository;

	@InjectMocks
	private AddressService addressService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateAddressSuccessfully() throws ResourceNotFoundException {
		// Arrange
		AddressDto addressDto = new AddressDto(0, null, null, null, null);
		Address mockAddress = new Address();
		mockAddress.setId(1L);
		mockAddress.setStreet("Street A");
		mockAddress.setNumber("123A");
		mockAddress.setZipCode("12345678");
		mockAddress.setComplement("Complement A");

		Mockito.when(addressRepository.save(any(Address.class))).thenReturn(mockAddress);

		// Act
		AddressDto savedAddress = addressService.createAddress(addressDto);

		// Assert
		assertNotNull(savedAddress);
		assertEquals(1L, savedAddress.id());
		assertEquals("Street A", savedAddress.street());
		verify(addressRepository, times(1)).save(any(Address.class));
	}

	@Test
	void testCreateAddressThrowsResourceNotFoundException() {
		// Arrange
		AddressDto addressDto = new AddressDto(0, null, null, null, null);

		Mockito.when(addressRepository.save(any(Address.class))).thenThrow(new RuntimeException("Database error"));

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.createAddress(addressDto);
		});
		assertEquals("Error entering address: Database error", exception.getMessage());
		verify(addressRepository, times(1)).save(any(Address.class));
	}

	@Test
	void testSearchAddressByIdSuccessfully() throws ResourceNotFoundException {
		// Arrange
		Address mockAddress = new Address();
		mockAddress.setId(1L); // Configuração correta do ID
		mockAddress.setStreet("Street A");
		mockAddress.setNumber("123A");
		mockAddress.setZipCode("12345678");
		mockAddress.setComplement("Complement A");

		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.of(mockAddress));

		// Act
		Address address = addressService.searchAddressById(1L);

		// Assert
		assertNotNull(address);
		assertEquals(1L, address.getId()); // Verifica se o ID está correto
		verify(addressRepository, times(1)).findById(1L);
	}

	@Test
	void testSearchAddressSuccessfully() throws ResourceNotFoundException {
		// Arrange
		List<Address> mockAddresses = List.of(new Address(), new Address());

		Mockito.when(addressRepository.findAll()).thenReturn(mockAddresses);

		// Act
		List<Address> addresses = addressService.searchAddress();

		// Assert
		assertNotNull(addresses);
		assertEquals(2, addresses.size());
		verify(addressRepository, times(1)).findAll();
	}

	@Test
	void testSearchAddressThrowsResourceNotFoundException() {
		// Arrange
		Mockito.when(addressRepository.findAll()).thenReturn(List.of());

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.searchAddress();
		});
		assertEquals("No address found.", exception.getMessage());
		verify(addressRepository, times(1)).findAll();
	}

	@Test
	void testSearchAddressByIdThrowsResourceNotFoundException() {
		// Arrange
		Mockito.when(addressRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.searchAddressById(1L);
		});
		assertEquals("Address not found for this id: 1", exception.getMessage());
		verify(addressRepository, times(1)).findById(1L);
	}

	@Test
    void testSearchAddressByCepSuccessfully() throws ResourceNotFoundException {
        // Arrange
        Address mockAddress = new Address();
        mockAddress.setZipCode("12345678"); // Configuração correta do zipCode

        when(addressRepository.findByZipCode("12345678")).thenReturn(mockAddress);

        // Act
        Address address = addressService.searchAddressByCep("12345678");

        // Assert
        assertNotNull(address, "Address retornado não deve ser nulo");
        assertEquals("12345678", address.getZipCode(), "O CEP deve ser 12345678");
        verify(addressRepository, times(1)).findByZipCode("12345678");
    }

	@Test
	void testSearchAddressByCepThrowsResourceNotFoundException() {
		// Arrange
		when(addressRepository.findByZipCode("12345678")).thenReturn(null);

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.searchAddressByCep("12345678");
		});
		assertEquals("Address with zip code: 12345678 not found.", exception.getMessage());
		verify(addressRepository, times(1)).findByZipCode("12345678");
	}

	@Test
	void testDeleteAddressSuccessfully() throws ResourceNotFoundException {
		// Arrange
		Address mockAddress = new Address();
		mockAddress.setId(1L); // Configuração correta do ID

		when(addressRepository.findById(1L)).thenReturn(Optional.of(mockAddress));
		doNothing().when(addressRepository).deleteById(1L);

		// Act
		String result = addressService.deleteAddress(1L);

		// Assert
		assertEquals("Address deleted successfully!", result);
		verify(addressRepository, times(1)).findById(1L);
		verify(addressRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteAddressThrowsResourceNotFoundException() {
		// Arrange
		when(addressRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addressService.deleteAddress(1L);
		});
		assertEquals("Address not found for this id: 1", exception.getMessage());
		verify(addressRepository, times(1)).findById(1L);
	}

}