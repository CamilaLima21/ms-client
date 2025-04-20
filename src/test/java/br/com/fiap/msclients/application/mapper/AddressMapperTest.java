package br.com.fiap.msclients.application.mapper;

import br.com.fiap.msclients.application.dto.AddressDto;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.domain.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = new AddressMapper();
    }

    @Test
    void shouldConvertEntityToDomain() {
        // Given
        AddressEntity entity = new AddressEntity();
        entity.setId(1L);
        entity.setStreet("Main Street");
        entity.setNumber("123");
        entity.setZipCode("12345");
        entity.setComplement("Apt 1");

        // When
        Address domain = addressMapper.toDomain(entity);

        // Then
        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getStreet(), domain.getStreet());
        assertEquals(entity.getNumber(), domain.getNumber());
        assertEquals(entity.getZipCode(), domain.getZipCode());
        assertEquals(entity.getComplement(), domain.getComplement());
    }

    @Test
    void shouldConvertDomainToEntity() {
        // Given
        Address domain = new Address(1L, "Main Street", "123", "12345", "Apt 1");

        // When
        AddressEntity entity = addressMapper.toEntity(domain);

        // Then
        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getStreet(), entity.getStreet());
        assertEquals(domain.getNumber(), entity.getNumber());
        assertEquals(domain.getZipCode(), entity.getZipCode());
        assertEquals(domain.getComplement(), entity.getComplement());
    }

    @Test
    void shouldConvertDomainToDto() {
        // Given
        Address domain = new Address(1L, "Main Street", "123", "12345", "Apt 1");

        // When
        AddressDto dto = addressMapper.toDto(domain);

        // Then
        assertNotNull(dto);
        assertEquals(domain.getId(), dto.id());
        assertEquals(domain.getStreet(), dto.street());
        assertEquals(domain.getNumber(), dto.number());
        assertEquals(domain.getZipCode(), dto.zipCode());
        assertEquals(domain.getComplement(), dto.complement());
    }

    @Test
    void shouldConvertEntityToDto() {
        // Given
        AddressEntity entity = new AddressEntity();
        entity.setId(1L);
        entity.setStreet("Main Street");
        entity.setNumber("123");
        entity.setZipCode("12345");
        entity.setComplement("Apt 1");

        // When
        AddressDto dto = addressMapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getStreet(), dto.street());
        assertEquals(entity.getNumber(), dto.number());
        assertEquals(entity.getZipCode(), dto.zipCode());
        assertEquals(entity.getComplement(), dto.complement());
    }

    @Test
    void shouldUpdateEntityFromDto() {
        // Given
        AddressDto dto = new AddressDto(1L, "Main Street", "123", "12345", "Apt 1");
        AddressEntity entity = new AddressEntity();

        // When
        addressMapper.updateEntityFromDto(dto, entity);

        // Then
        assertNotNull(entity);
        assertEquals(dto.street(), entity.getStreet());
        assertEquals(dto.number(), entity.getNumber());
        assertEquals(dto.zipCode(), entity.getZipCode());
        assertEquals(dto.complement(), entity.getComplement());
    }
}
