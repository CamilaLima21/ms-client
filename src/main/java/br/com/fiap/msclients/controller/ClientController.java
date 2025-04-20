package br.com.fiap.msclients.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.msclients.dto.ClientDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Client;
import br.com.fiap.msclients.service.ClientService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> searchClients() throws ResourceNotFoundException {
        return ResponseEntity.ok().body(clientService.searchClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> searchClient(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(clientService.searchClient(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> searchClientByEmail(@PathVariable String email) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(clientService.searchClientByEmail(email));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Client> searchClientByCpf(@PathVariable String cpf) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(clientService.searchClientByCpf(cpf));
    }    

    @GetMapping("/name/{name}")
    public ResponseEntity<Client> searchClientByName(@PathVariable String name) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(clientService.searchClientByName(name));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) throws ResourceNotFoundException {
        ClientDto clientSave = clientService.createClient(clientDto);
        return new ResponseEntity<>(clientSave, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable long id) throws ResourceNotFoundException {
        String msg = clientService.deleteClient(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
