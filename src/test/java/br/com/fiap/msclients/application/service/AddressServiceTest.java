package br.com.fiap.msclients.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.msclients.application.dto.AddressDto;
import br.com.fiap.msclients.application.mapper.AddressMapper;
import br.com.fiap.msclients.domain.model.Address;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressMapper addressMapper;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressRepository = mock(AddressRepository.class);
        addressMapper = mock(AddressMapper.class);
        addressService = new AddressService(addressRepository, addressMapper);
    }

    @Test
    void shouldCreateAddress() {
        AddressDto dto = new AddressDto(0L, "Rua A", "123", "00000-000", "Casa");
        Address domain = new Address(0L, "Rua A", "123", "00000-000", "Casa");
        AddressEntity entity = new AddressEntity();
        entity.setId(1L);
        AddressEntity savedEntity = new AddressEntity();
        savedEntity.setId(1L);
        Address savedDomain = new Address(1L, "Rua A", "123", "00000-000", "Casa");
        AddressDto savedDto = new AddressDto(1L, "Rua A", "123", "00000-000", "Casa");

        when(addressMapper.toDomain(dto)).thenReturn(domain);
        when(addressMapper.toEntity(domain)).thenReturn(entity);
        when(addressRepository.save(entity)).thenReturn(savedEntity);
        when(addressMapper.toDomain(savedEntity)).thenReturn(savedDomain);
        when(addressMapper.toDto(savedDomain)).thenReturn(savedDto);

        AddressDto result = addressService.create(dto);

        assertNotNull(result);
        assertEquals(savedDto, result);
    }

    @Test
    void shouldFindAllAddresses() {
        AddressEntity entity = new AddressEntity();
        Address domain = new Address(1L, "Rua A", "123", "00000-000", "Casa");
        AddressDto dto = new AddressDto(1L, "Rua A", "123", "00000-000", "Casa");

        when(addressRepository.findAll()).thenReturn(List.of(entity));
        when(addressMapper.toDomain(entity)).thenReturn(domain);
        when(addressMapper.toDto(domain)).thenReturn(dto);

        List<AddressDto> result = addressService.findAll();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldFindById() throws ResourceNotFoundException {
        long id = 1L;
        AddressEntity entity = new AddressEntity();
        Address domain = new Address(id, "Rua A", "123", "00000-000", "Casa");
        AddressDto dto = new AddressDto(id, "Rua A", "123", "00000-000", "Casa");

        when(addressRepository.findById(id)).thenReturn(Optional.of(entity));
        when(addressMapper.toDomain(entity)).thenReturn(domain);
        when(addressMapper.toDto(domain)).thenReturn(dto);

        AddressDto result = addressService.findById(id);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowWhenIdNotFound() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.findById(999L));
    }

    @Test
    void shouldFindByZipCode() throws ResourceNotFoundException {
        String zip = "12345-678";
        AddressEntity entity = new AddressEntity();
        Address domain = new Address(1L, "Rua B", "456", zip, null);
        AddressDto dto = new AddressDto(1L, "Rua B", "456", zip, null);

        when(addressRepository.findByZipCode(zip)).thenReturn(entity);
        when(addressMapper.toDomain(entity)).thenReturn(domain);
        when(addressMapper.toDto(domain)).thenReturn(dto);

        AddressDto result = addressService.findByZipCode(zip);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowWhenZipCodeNotFound() {
        when(addressRepository.findByZipCode("99999-000")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> addressService.findByZipCode("99999-000"));
    }

    @Test
    void shouldUpdateAddress() throws ResourceNotFoundException {
        long id = 1L;
        AddressDto dto = new AddressDto(id, "Rua Atualizada", "999", "54321-000", "Bloco B");
        AddressEntity existingEntity = new AddressEntity();
        AddressEntity updatedEntity = new AddressEntity();
        Address domain = new Address(id, "Rua Atualizada", "999", "54321-000", "Bloco B");

        when(addressRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        doNothing().when(addressMapper).updateEntityFromDto(dto, existingEntity);
        when(addressRepository.save(existingEntity)).thenReturn(updatedEntity);
        when(addressMapper.toDomain(updatedEntity)).thenReturn(domain);
        when(addressMapper.toDto(domain)).thenReturn(dto);

        AddressDto result = addressService.updateAddress(id, dto);

        assertEquals(dto, result);
    }

    @Test
    void shouldDeleteAddress() throws ResourceNotFoundException {
        long id = 1L;
        AddressEntity entity = new AddressEntity();
        when(addressRepository.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(addressRepository).delete(entity);

        String result = addressService.delete(id);

        assertEquals("Address deleted successfully!", result);
    }

    @Test
    void shouldThrowWhenDeleteNotFound() {
        when(addressRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.delete(999L));
    }
}
