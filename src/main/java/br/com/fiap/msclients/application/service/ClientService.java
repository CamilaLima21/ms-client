package br.com.fiap.msclients.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.msclients.application.dto.ClientDto;
import br.com.fiap.msclients.application.mapper.ClientMapper;
import br.com.fiap.msclients.infrastructure.persistence.entity.AddressEntity;
import br.com.fiap.msclients.infrastructure.persistence.entity.ClientEntity;
import br.com.fiap.msclients.infrastructure.persistence.repository.AddressRepository;
import br.com.fiap.msclients.infrastructure.persistence.repository.ClientRepository;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.domain.model.Client;
import br.com.fiap.msclients.domain.validator.CpfValidator;

@Service
public class ClientService {

	private final ClientRepository clientRepository;
	private final AddressRepository addressRepository;
	private final ClientMapper mapper;
	private final CpfValidator cpfValidator;

	public ClientService(ClientRepository clientRepository, AddressRepository addressRepository, ClientMapper mapper,
			CpfValidator cpfValidator) {
		this.clientRepository = clientRepository;
		this.addressRepository = addressRepository;
		this.mapper = mapper;
		this.cpfValidator = cpfValidator;
	}

	public ClientDto create(ClientDto dto) throws ResourceNotFoundException {
	    if (!cpfValidator.validateCpf(dto.cpf())) {
	        throw new IllegalArgumentException("Invalid CPF");
	    }

	    if (dto.addressId() <= 0) {
	        throw new IllegalArgumentException("A valid addressId must be provided");
	    }

	    AddressEntity address = addressRepository.findById(dto.addressId())
	            .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

	    Client client = new Client(dto.id(), dto.name(), dto.email(), dto.cpf(), dto.birth(), null);

	    ClientEntity saved = clientRepository.save(mapper.toEntity(client, address));
	    return mapper.toDto(mapper.toDomain(saved));
	}

	public List<ClientDto> findAll() {
		return clientRepository.findAll().stream().map(mapper::toDomain).map(mapper::toDto)
				.collect(Collectors.toList());
	}

	public ClientDto findById(long id) throws ResourceNotFoundException {
		ClientEntity entity = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		return mapper.toDto(mapper.toDomain(entity));
	}

	public ClientDto update(long id, ClientDto dto) throws ResourceNotFoundException {
		ClientEntity entity = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found"));

		AddressEntity address = addressRepository.findById(dto.addressId())
				.orElseThrow(() -> new ResourceNotFoundException("Address not found"));

		mapper.updateFromDto(dto, entity, address);
		ClientEntity updated = clientRepository.save(entity);
		return mapper.toDto(mapper.toDomain(updated));
	}

	public String delete(long id) throws ResourceNotFoundException {
		ClientEntity entity = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		clientRepository.delete(entity);
		return "Client deleted successfully!";
	}

	public ClientDto findByCpf(String cpf) throws ResourceNotFoundException {
		ClientEntity entity = clientRepository.findByCpf(cpf);
		if (entity == null) {
			throw new ResourceNotFoundException("Client not found with CPF: " + cpf);
		}
		return mapper.toDto(mapper.toDomain(entity));
	}

	public ClientDto findByEmail(String email) throws ResourceNotFoundException {
		ClientEntity entity = clientRepository.findByEmail(email);
		if (entity == null) {
			throw new ResourceNotFoundException("Client not found with email: " + email);
		}
		return mapper.toDto(mapper.toDomain(entity));
	}

	public ClientDto findByName(String name) throws ResourceNotFoundException {
		ClientEntity entity = clientRepository.findByName(name);
		if (entity == null) {
			throw new ResourceNotFoundException("Client not found with name: " + name);
		}
		return mapper.toDto(mapper.toDomain(entity));
	}

}
