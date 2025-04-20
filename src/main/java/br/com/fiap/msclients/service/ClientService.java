package br.com.fiap.msclients.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.msclients.dto.ClientDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Address;
import br.com.fiap.msclients.model.Client;
import br.com.fiap.msclients.repository.AddressRepository;
import br.com.fiap.msclients.repository.ClientRepository;
import br.com.fiap.msclients.utils.CpfValidator;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final CpfValidator cpfValidator;

    public ClientService(ClientRepository clienteRepository, AddressRepository addressRepository, CpfValidator cpfValidator) {
        this.clientRepository = clienteRepository;
        this.addressRepository = addressRepository;
        this.cpfValidator = cpfValidator;
    }

    public ClientDto createClient(ClientDto clientDto) throws ResourceNotFoundException {
        Client client = toEntity(clientDto);

        Client finalClient = client;
        Address address = addressRepository.findById(
            client.getAddress().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id: " + finalClient.getAddress().getId()));

        if (!cpfValidator.validateCpf(client.getCpf())) {
            throw new ResourceNotFoundException("Cpf invalid! ");
        }

        try {
            client = clientRepository.save(client);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error inserting client: " + e.getMessage());
        } 

        return toDto(client);
    }

    public List<Client> searchClients() throws ResourceNotFoundException {
        List<Client> client = clientRepository.findAll();
        if (client.isEmpty()) {
            throw new ResourceNotFoundException("No customers found.");
        }
        return client;
    }

    public Client searchClient(long id) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id: " + id));
        return client;
    }

    public Client searchClientByEmail(String email) throws ResourceNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            throw new ResourceNotFoundException("Client with email: " + email + " not found.");
        }
        return client;
    }

    public Client searchClientByCpf(String cpf) throws ResourceNotFoundException {
        Client client = clientRepository.findByCpf(cpf);
        if (client == null) {
            throw new ResourceNotFoundException("Customer with CPF: " + cpf + " not found.");
        }
        return client;
    }    

    public Client searchClientByName(String name) throws ResourceNotFoundException {
        Client client = clientRepository.findByName(name);
        if (client == null) {
            throw new ResourceNotFoundException("Customer with name: " + name + " not found.");
        }
        return client;
    }

   
   public String deleteClient(long id) throws ResourceNotFoundException {
    try {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id: " + id));
        clientRepository.deleteById(client.getId());
    } catch (Exception e) {
        throw new ResourceNotFoundException("Client not found for this id: " + id);
    }
    return "Customer successfully deleted!";
}    

    public ClientDto toDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCpf(),
                client.getBirth(), 
                client.getAddress());
    }            

    public Client toEntity(ClientDto clientDto) {
        
        Client client = new Client();
        client.setId(clientDto.id());
        client.setName(clientDto.name());
        client.setEmail(clientDto.email());
        client.setCpf(clientDto.cpf());
        client.setBirth(clientDto.birth());
        client.setAddress(clientDto.address());

        return client;
    }

	 
}

