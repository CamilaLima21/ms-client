package br.com.fiap.msclients.application.mapper;

import org.springframework.stereotype.Component;
import br.com.fiap.msclients.application.dto.ClientDto;
import br.com.fiap.msclients.infrastructure.persistence.entity.ClientEntity;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.domain.model.Client;

@Component
public class ClientMapper {

    private final AddressMapper addressMapper;

    public ClientMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Client toDomain(ClientEntity entity) {
        return new Client(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getCpf(),
            entity.getBirth(),
            addressMapper.toDomain(entity.getAddress())
        );
    }

    public ClientEntity toEntity(Client domain, AddressEntity addressEntity) {
        ClientEntity entity = new ClientEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setCpf(domain.getCpf());
        entity.setBirth(domain.getBirth());
        entity.setAddress(addressEntity);
        return entity;
    }

    public ClientDto toDto(Client domain) {
        return new ClientDto(
            domain.getId(),
            domain.getName(),
            domain.getEmail(),
            domain.getCpf(),
            domain.getBirth(),
            domain.getAddress().getId()
        );
    }

    public void updateFromDto(ClientDto dto, ClientEntity entity, AddressEntity addressEntity) {
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setCpf(dto.cpf());
        entity.setBirth(dto.birth());
        entity.setAddress(addressEntity);
    }
}
