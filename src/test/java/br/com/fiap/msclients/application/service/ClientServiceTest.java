package br.com.fiap.msclients.application.service;

import br.com.fiap.msclients.application.dto.ClientDto;
import br.com.fiap.msclients.application.mapper.ClientMapper;
import br.com.fiap.msclients.domain.model.Client;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.infrastructure.persistence.entity.ClientEntity;
import br.com.fiap.msclients.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.msclients.infrastructure.persistence.repository.ClientRepository;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.domain.validator.CpfValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private CpfValidator cpfValidator;

    private ClientDto clientDto;
    private ClientEntity clientEntity;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup common data
        clientDto = new ClientDto(1L, "João", "joao@email.com", "12345678900", java.time.LocalDate.now(), 10L);
        clientEntity = new ClientEntity();
        addressEntity = new AddressEntity();
    }

    @Test
    void shouldCreateClient() throws ResourceNotFoundException {
        // Configuração dos mocks
        when(cpfValidator.validateCpf(clientDto.cpf())).thenReturn(true);  // Validação do CPF
        when(addressRepository.findById(clientDto.addressId())).thenReturn(Optional.of(addressEntity));  // Encontrar endereço válido

        // Mock do clientMapper.toEntity
        when(clientMapper.toEntity(any(Client.class), any(AddressEntity.class))).thenReturn(clientEntity);

        // Mock do clientRepository.save
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        // Mock do clientMapper.toDto
        when(clientMapper.toDto(any(Client.class))).thenReturn(clientDto);

        // Mock para a conversão de clientEntity para clientDomain
        when(clientMapper.toDomain(clientEntity)).thenReturn(new Client(1L, "João", "joao@email.com", "12345678900", java.time.LocalDate.now(), null));

        // Definir valores no clientEntity para garantir que não sejam nulos
        clientEntity.setId(1L);  // Garantir que o ID não seja nulo
        clientEntity.setName("João");
        clientEntity.setEmail("joao@email.com");
        clientEntity.setCpf("12345678900");
        clientEntity.setBirth(java.time.LocalDate.now());
        clientEntity.setAddress(addressEntity);  // Garantir que o endereço esteja associado

        // Executar o método de criação
        ClientDto result = clientService.create(clientDto);

        // Verificação do que está sendo retornado
        System.out.println("Resultado: " + result);  // Verifique o que está sendo retornado

        // Assegurar que o resultado não seja null
        assertNotNull(result, "O resultado não deve ser nulo!");

        // Verifique os valores específicos do result para garantir que está correto
        assertEquals(clientDto.id(), result.id(), "Os IDs não são iguais!");
        assertEquals(clientDto.name(), result.name(), "Os nomes não são iguais!");
        assertEquals(clientDto.email(), result.email(), "Os emails não são iguais!");
        assertEquals(clientDto.cpf(), result.cpf(), "Os CPFs não são iguais!");

        // Assegurar que o retorno seja igual ao esperado
        assertEquals(clientDto, result, "O DTO retornado não é igual ao esperado!");
    }

    @Test
    void shouldThrowExceptionWhenCpfIsInvalid() {
        when(cpfValidator.validateCpf(clientDto.cpf())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> clientService.create(clientDto));
    }

    @Test
    void shouldThrowExceptionWhenAddressNotFound() {
        when(cpfValidator.validateCpf(clientDto.cpf())).thenReturn(true);
        when(addressRepository.findById(clientDto.addressId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.create(clientDto));
    }

    @Test
    void shouldFindClientById() throws ResourceNotFoundException {
        // Configuração da entidade do cliente
        clientEntity = new ClientEntity(1L, "João", "joao@email.com", "12345678900", java.time.LocalDate.now(), null);

        // Configuração do domínio
        Client domain = new Client(1L, "João", "joao@email.com", "12345678900", java.time.LocalDate.now(), null);

        // Configuração do DTO do cliente
        clientDto = new ClientDto(1L, "João", "joao@email.com", "12345678900", java.time.LocalDate.now(), 10L);

        // Mockando o repositório de clientes para retornar a entidade mockada
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        // Mockando o mapeamento de entity para domain
        when(clientMapper.toDomain(clientEntity)).thenReturn(domain);

        // Mockando o mapeamento de domain para dto
        when(clientMapper.toDto(domain)).thenReturn(clientDto);

        // Executando o método de busca
        ClientDto result = clientService.findById(1L);

        // Verificando se o resultado não é nulo
        assertNotNull(result, "O resultado não deve ser nulo!");

        // Verificando se o DTO retornado é o mesmo que o esperado
        assertEquals(clientDto, result, "O cliente retornado não é igual ao esperado!");
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(1L));
    }

    @Test
    void shouldUpdateClient() throws ResourceNotFoundException {
        // Prepare the updated ClientDto
        ClientDto updatedDto = new ClientDto(1L, "João", "newemail@email.com", "98765432100", java.time.LocalDate.now(), 10L);

        // Prepare a dummy Client object to be returned by toDomain
        Client domain = new Client(1L, "João", "newemail@email.com", "98765432100", java.time.LocalDate.now(), null);

        // Mock repository and mapper behavior
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(addressRepository.findById(updatedDto.addressId())).thenReturn(Optional.of(addressEntity));
        
        doNothing().when(clientMapper).updateFromDto(updatedDto, clientEntity, addressEntity);
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

        // ⚠️ Mock toDomain corretamente
        when(clientMapper.toDomain(clientEntity)).thenReturn(domain);

        // ⚠️ Depois de ter o domínio, mock toDto
        when(clientMapper.toDto(domain)).thenReturn(updatedDto);

        // Perform the update
        ClientDto result = clientService.update(1L, updatedDto);

        // Assertions
        assertNotNull(result);
        assertEquals(updatedDto, result);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundForUpdate() {
        ClientDto updatedDto = new ClientDto(1L, "João", "newemail@email.com", "98765432100", java.time.LocalDate.now(), 10L);

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.update(1L, updatedDto));
    }

    @Test
    void shouldDeleteClient() throws ResourceNotFoundException {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        String result = clientService.delete(1L);

        assertEquals("Client deleted successfully!", result);
        verify(clientRepository, times(1)).delete(clientEntity);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundForDeletion() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.delete(1L));
    }
}
