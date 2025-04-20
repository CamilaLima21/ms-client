package br.com.fiap.msclients.application.mapper;

import br.com.fiap.msclients.application.dto.ClientDto;
import br.com.fiap.msclients.domain.model.Address;
import br.com.fiap.msclients.domain.model.Client;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.infrastructure.persistence.entity.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientMapperTest {

    private ClientMapper clientMapper;
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = mock(AddressMapper.class);
        clientMapper = new ClientMapper(addressMapper);
    }

    @Test
    void shouldConvertEntityToDomain() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);

        ClientEntity entity = new ClientEntity();
        entity.setId(10L);
        entity.setName("João");
        entity.setEmail("joao@email.com");
        entity.setCpf("12345678900");
        entity.setBirth(LocalDate.of(1990, 1, 1));
        entity.setAddress(addressEntity);

        Address address = new Address(1L, "Rua A", "10", "12345-678", "Apto 1");
        when(addressMapper.toDomain(addressEntity)).thenReturn(address);

        // When
        Client domain = clientMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getEmail(), domain.getEmail());
        assertEquals(entity.getCpf(), domain.getCpf());
        assertEquals(entity.getBirth(), domain.getBirth());
        assertEquals(address, domain.getAddress());
    }

    @Test
    void shouldConvertDomainToEntity() {
        // Given
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);

        Address address = new Address(1L, "Rua A", "10", "12345-678", "Apto 1");
        Client domain = new Client(10L, "Maria", "maria@email.com", "09876543211", LocalDate.of(1985, 5, 5), address);

        // When
        ClientEntity entity = clientMapper.toEntity(domain, addressEntity);

        // Then
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getEmail(), entity.getEmail());
        assertEquals(domain.getCpf(), entity.getCpf());
        assertEquals(domain.getBirth(), entity.getBirth());
        assertEquals(addressEntity, entity.getAddress());
    }

    @Test
    void shouldConvertDomainToDto() {
        // Given
        Address address = new Address(1L, "Rua B", "50", "98765-432", "Casa");
        Client domain = new Client(20L, "Ana", "ana@email.com", "11122233344", LocalDate.of(2000, 3, 15), address);

        // When
        ClientDto dto = clientMapper.toDto(domain);

        // Then
        assertNotNull(dto);
        assertEquals(domain.getId(), dto.id());
        assertEquals(domain.getName(), dto.name());
        assertEquals(domain.getEmail(), dto.email());
        assertEquals(domain.getCpf(), dto.cpf());
        assertEquals(domain.getBirth(), dto.birth());
        assertEquals(domain.getAddress().getId(), dto.addressId());
    }

    @Test
    void shouldUpdateEntityFromDto() {
        // Given
        ClientDto dto = new ClientDto(1L, "Pedro", "pedro@email.com", "99988877766", LocalDate.of(1995, 12, 10), 2L);
        ClientEntity entity = new ClientEntity();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(2L);

        // When
        clientMapper.updateFromDto(dto, entity, addressEntity);

        // Then
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.email(), entity.getEmail());
        assertEquals(dto.cpf(), entity.getCpf());
        assertEquals(dto.birth(), entity.getBirth());
        assertEquals(addressEntity, entity.getAddress());
    }
}
