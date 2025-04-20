package br.com.fiap.msclients.application.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.msclients.application.dto.AddressDto;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.domain.model.Address;

@Component
public class AddressMapper {

    public Address toDomain(AddressEntity entity) {
        return new Address(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getZipCode(),
                entity.getComplement()
        );
    }

    public AddressEntity toEntity(Address domain) {
        AddressEntity entity = new AddressEntity();
        entity.setId(domain.getId());
        entity.setStreet(domain.getStreet());
        entity.setNumber(domain.getNumber());
        entity.setZipCode(domain.getZipCode());
        entity.setComplement(domain.getComplement());
        return entity;
    }

    public AddressDto toDto(Address domain) {
        return new AddressDto(
                domain.getId(),
                domain.getStreet(),
                domain.getNumber(),
                domain.getZipCode(),
                domain.getComplement()
        );
    }

    public Address toDomain(AddressDto dto) {
        return new Address(
                dto.id(),
                dto.street(),
                dto.number(),
                dto.zipCode(),
                dto.complement()
        );
    }

    public AddressDto toDto(AddressEntity entity) {
        return new AddressDto(
                entity.getId(),
                entity.getStreet(),
                entity.getNumber(),
                entity.getZipCode(),
                entity.getComplement()
        );
    }

    public AddressEntity toEntity(AddressDto dto) {
        AddressEntity entity = new AddressEntity();
        entity.setStreet(dto.street());
        entity.setNumber(dto.number());
        entity.setZipCode(dto.zipCode());
        entity.setComplement(dto.complement());
        return entity;
    }

    public void updateEntityFromDto(AddressDto dto, AddressEntity entity) {
        entity.setStreet(dto.street());
        entity.setNumber(dto.number());
        entity.setZipCode(dto.zipCode());
        entity.setComplement(dto.complement());
    }
}
