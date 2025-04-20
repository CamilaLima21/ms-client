package br.com.fiap.msclients.infrastructure.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.msclients.application.dto.AddressDto;
import br.com.fiap.msclients.application.service.AddressService;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/cep/{zipCode}")
    public ResponseEntity<AddressDto> findByZipCode(@PathVariable String zipCode) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.findByZipCode(zipCode));
    }

    @PostMapping
    public ResponseEntity<AddressDto> create(@RequestBody AddressDto dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable long id, @Valid @RequestBody AddressDto addressDto) throws ResourceNotFoundException {
        AddressDto updatedAddress = service.updateAddress(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.delete(id));
    }
}
