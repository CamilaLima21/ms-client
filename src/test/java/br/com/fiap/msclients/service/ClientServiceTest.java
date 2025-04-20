package br.com.fiap.msclients.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.fiap.msclients.dto.ClientDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Address;
import br.com.fiap.msclients.model.Client;
import br.com.fiap.msclients.repository.AddressRepository;
import br.com.fiap.msclients.repository.ClientRepository;
import br.com.fiap.msclients.utils.CpfValidator;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CpfValidator cpfValidator;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClientSuccessfully() throws ResourceNotFoundException {
        // Arrange
        Address mockAddress = new Address();
        mockAddress.setId(1L);

        ClientDto clientDto = new ClientDto(0L, "John Doe", "john@example.com", "12345678909", null, mockAddress);
        Client mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setName("John Doe");
        mockClient.setEmail("john@example.com");
        mockClient.setCpf("12345678909");
        mockClient.setAddress(mockAddress);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(mockAddress));
        when(cpfValidator.validateCpf("12345678909")).thenReturn(true);
        when(clientRepository.save(any(Client.class))).thenReturn(mockClient);

        // Act
        ClientDto savedClient = clientService.createClient(clientDto);

        // Assert
        assertNotNull(savedClient, "O cliente salvo não deve ser nulo");
        assertEquals(1L, savedClient.id(), "O ID do cliente salvo deve ser 1");
        assertEquals("John Doe", savedClient.name(), "O nome do cliente salvo deve ser John Doe");
        verify(addressRepository, times(1)).findById(1L);
        verify(cpfValidator, times(1)).validateCpf("12345678909");
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testCreateClientThrowsResourceNotFoundExceptionInvalidCpf() {
        // Arrange
        Address mockAddress = new Address();
        mockAddress.setId(1L);

        ClientDto clientDto = new ClientDto(0L, "John Doe", "john@example.com", "12345678909", null, mockAddress);

        when(addressRepository.findById(1L)).thenReturn(Optional.of(mockAddress));
        when(cpfValidator.validateCpf("12345678909")).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.createClient(clientDto);
        });
        assertEquals("Cpf invalid! ", exception.getMessage());
        verify(cpfValidator, times(1)).validateCpf("12345678909");
    }

    @Test
    void testSearchClientsSuccessfully() throws ResourceNotFoundException {
        // Arrange
        List<Client> mockClients = List.of(
            new Client(),
            new Client()
        );

        when(clientRepository.findAll()).thenReturn(mockClients);

        // Act
        List<Client> clients = clientService.searchClients();

        // Assert
        assertNotNull(clients, "A lista de clientes não deve ser nula");
        assertEquals(2, clients.size(), "A lista de clientes deve conter 2 elementos");
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testSearchClientsThrowsResourceNotFoundException() {
        // Arrange
        when(clientRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.searchClients();
        });
        assertEquals("No customers found.", exception.getMessage(), "A mensagem de erro deve corresponder");
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testSearchClientByIdSuccessfully() throws ResourceNotFoundException {
        // Arrange
        Client mockClient = new Client();
        mockClient.setId(1L); // Configuração correta do ID
        mockClient.setName("John Doe");
        mockClient.setEmail("john@example.com");
        mockClient.setCpf("12345678909");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));

        // Act
        Client client = clientService.searchClient(1L);

        // Assert
        assertNotNull(client, "O cliente retornado não deve ser nulo");
        assertEquals(1L, client.getId(), "O ID do cliente deve ser 1");
        assertEquals("John Doe", client.getName(), "O nome do cliente deve ser John Doe");
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testSearchClientByIdThrowsResourceNotFoundException() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.searchClient(1L);
        });
        assertEquals("Client not found for this id: 1", exception.getMessage(), "A mensagem de erro deve corresponder");
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteClientSuccessfully() throws ResourceNotFoundException {
        // Arrange
        Client mockClient = new Client();
        mockClient.setId(1L); // Configuração correta do ID

        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));
        doNothing().when(clientRepository).deleteById(1L);

        // Act
        String result = clientService.deleteClient(1L);

        // Assert
        assertEquals("Customer successfully deleted!", result, "A mensagem de sucesso deve corresponder");
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClientThrowsResourceNotFoundException() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.deleteClient(1L);
        });
        assertEquals("Client not found for this id: 1", exception.getMessage(), "A mensagem de erro deve corresponder");
        verify(clientRepository, times(1)).findById(1L);
    }

}

