package br.com.fiap.msclients.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.msclients.application.dto.AddressDto;
import br.com.fiap.msclients.application.mapper.AddressMapper;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.domain.model.Address;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper mapper;

    public AddressService(AddressRepository addressRepository, AddressMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    public AddressDto create(AddressDto dto) {
        Address domain = mapper.toDomain(dto);
        AddressEntity saved = addressRepository.save(mapper.toEntity(domain));
        return mapper.toDto(mapper.toDomain(saved));
    }

    public List<AddressDto> findAll() {
        return addressRepository.findAll().stream()
                .map(mapper::toDomain)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public AddressDto findById(long id) throws ResourceNotFoundException {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        return mapper.toDto(mapper.toDomain(entity));
    }

    public AddressDto findByZipCode(String zipCode) throws ResourceNotFoundException {
        AddressEntity entity = addressRepository.findByZipCode(zipCode);
        if (entity == null)
            throw new ResourceNotFoundException("Address not found with zipCode: " + zipCode);
        return mapper.toDto(mapper.toDomain(entity));
    }

    public AddressDto updateAddress(long id, AddressDto addressDto) throws ResourceNotFoundException {
        AddressEntity existingEntity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for id: " + id));

        mapper.updateEntityFromDto(addressDto, existingEntity);
        AddressEntity updatedEntity = addressRepository.save(existingEntity);

        return mapper.toDto(mapper.toDomain(updatedEntity));
    }

    public String delete(long id) throws ResourceNotFoundException {
        AddressEntity entity = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        addressRepository.delete(entity);
        return "Address deleted successfully!";
    }
}
