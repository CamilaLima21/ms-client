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

import br.com.fiap.msclients.dto.ClientDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Client;
import br.com.fiap.msclients.service.ClientService;

class ClientControllerTest {

	@Mock
	private ClientService clientService;

	@InjectMocks
	private ClientController clientController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSearchClients() throws ResourceNotFoundException {
		// Arrange
		List<Client> mockClients = Arrays.asList(new Client(), new Client());
		when(clientService.searchClients()).thenReturn(mockClients);

		// Act
		ResponseEntity<List<Client>> response = clientController.searchClients();

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().size());
		verify(clientService, times(1)).searchClients();
	}

	@Test
	void testSearchClientById() throws ResourceNotFoundException {
		// Arrange
		long id = 1L;

		Client mockClient = new Client();
		when(clientService.searchClient(id)).thenReturn(mockClient);

		// Act
		ResponseEntity<Client> response = clientController.searchClient(id);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockClient, response.getBody());
		verify(clientService, times(1)).searchClient(id);
	}

	@Test
	void testSearchClientByEmail() throws ResourceNotFoundException {
		// Arrange
		String email = "john@example.com";
		Client mockClient = new Client();
		when(clientService.searchClientByEmail(email)).thenReturn(mockClient);

		// Act
		ResponseEntity<Client> response = clientController.searchClientByEmail(email);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockClient, response.getBody());
		verify(clientService, times(1)).searchClientByEmail(email);
	}

	@Test
	void testSearchClientByName() throws ResourceNotFoundException {
		// Arrange
		String name = "Camila Marques";
		Client mockClient = new Client();
		when(clientService.searchClientByName(name)).thenReturn(mockClient);

		// Act
		ResponseEntity<Client> response = clientController.searchClientByName(name);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockClient, response.getBody());
		verify(clientService, times(1)).searchClientByName(name);
	}

	@Test
	void testSearchClientByCpf() throws ResourceNotFoundException {
		// Arrange
		String cpf = "123456789";
		Client mockClient = new Client();
		when(clientService.searchClientByCpf(cpf)).thenReturn(mockClient);

		// Act
		ResponseEntity<Client> response = clientController.searchClientByCpf(cpf);

		// Assert
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertEquals(mockClient, response.getBody());
		verify(clientService, times(1)).searchClientByCpf(cpf);
	}

	@Test
	void testCreateClient() throws ResourceNotFoundException {
		// Arrange
		ClientDto newClient = new ClientDto(0, null, null, null, null, null);
		ClientDto savedClient = new ClientDto(0, null, null, null, null, null);
		when(clientService.createClient(newClient)).thenReturn(savedClient);

		// Act
		ResponseEntity<ClientDto> response = clientController.createClient(newClient);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(savedClient, response.getBody());
		verify(clientService, times(1)).createClient(newClient);
	}

	@Test
	void testDeleteClient() throws ResourceNotFoundException {
		// Arrange
		long id = 1L;
		String deleteMessage = "Cliente deletado com sucesso";
		when(clientService.deleteClient(id)).thenReturn(deleteMessage);

		// Act
		ResponseEntity<String> response = clientController.deleteClient(id);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(deleteMessage, response.getBody());
		verify(clientService, times(1)).deleteClient(id);
	}
}
