package br.com.fiap.msclients.infrastructure.web.controller;

import br.com.fiap.msclients.application.dto.ClientDto;
import br.com.fiap.msclients.application.service.ClientService;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }
    
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClientDto> findByCpf(@PathVariable String cpf) throws ResourceNotFoundException {
        ClientDto client = service.findByCpf(cpf);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/email")
    public ResponseEntity<ClientDto> findByEmail(@RequestParam String email) throws ResourceNotFoundException {
        ClientDto client = service.findByEmail(email);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/name")
    public ResponseEntity<ClientDto> findByName(@RequestParam String name) throws ResourceNotFoundException {
        ClientDto client = service.findByName(name);
        return ResponseEntity.ok(client);
    }
    
    //endpoint para validação de cliente
    @GetMapping("/validate/{id}")
    public ResponseEntity<Void> validateClientExists(@PathVariable long id) throws ResourceNotFoundException {
        service.validateClientExists(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping
    public ResponseEntity<ClientDto> create(@Valid @RequestBody ClientDto dto) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable long id, @Valid @RequestBody ClientDto dto) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.delete(id));
    }
}
