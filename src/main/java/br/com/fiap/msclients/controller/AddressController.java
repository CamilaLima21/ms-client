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

import br.com.fiap.msclients.dto.AddressDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Address;
import br.com.fiap.msclients.service.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> searchAddress() throws ResourceNotFoundException {
        return ResponseEntity.ok().body(addressService.searchAddress());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> searchAddressById(@PathVariable long id) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(addressService.searchAddressById(id));
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Address> searchAddressByCep(@PathVariable String cep) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(addressService.searchAddressByCep(cep));
    }    

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressDto addressDto) throws ResourceNotFoundException {
    	AddressDto addressSave = addressService.createAddress(addressDto);
        return new ResponseEntity<>(addressSave, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable long id) throws ResourceNotFoundException {
        String msg = addressService.deleteAddress(id);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}

