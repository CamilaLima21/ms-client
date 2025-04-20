package br.com.fiap.msclients.service;

import org.springframework.stereotype.Service;

import br.com.fiap.msclients.dto.AddressDto;
import br.com.fiap.msclients.exceptions.ResourceNotFoundException;
import br.com.fiap.msclients.model.Address;
import br.com.fiap.msclients.repository.AddressRepository;


import java.util.List;

@Service
public class AddressService {

    private  final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public AddressDto createAddress(AddressDto addressDto) throws ResourceNotFoundException {
    	Address address = toEntity(addressDto);

         try {
        	 address = addressRepository.save(address);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error entering address: " + e.getMessage());
        }

        return toDto(address);
    }

    public List<Address> searchAddress() throws ResourceNotFoundException {
        List<Address> address = addressRepository.findAll();
        if (address.isEmpty()) {
            throw new ResourceNotFoundException("No address found.");
        }
        return address;         
    }

    public Address searchAddressById(long id) throws ResourceNotFoundException {
    	Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id: " + id));
        return address;
    }

    public Address searchAddressByCep(String cep) throws ResourceNotFoundException {
    	Address address = addressRepository.findByZipCode(cep);
        if (address == null) {
            throw new ResourceNotFoundException("Address with zip code: " + cep + " not found.");
        }
        return address;
    }    

    public String deleteAddress(long id) throws ResourceNotFoundException {
        try {
        	Address address = addressRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id: " + id));
        	addressRepository.deleteById(address.getId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Address not found for this id: " + id);
        }
        return "Address deleted successfully!";
    }

    public AddressDto toDto(Address address) {
        return new AddressDto(
        		address.getId(),
        		address.getStreet(),
        		address.getNumber(),
        		address.getZipCode(),
        		address.getComplement());
    }

    public Address toEntity(AddressDto addressDto) {
    	Address address = new Address();
    	address.setStreet(addressDto.street());
        address.setNumber(addressDto.number());
        address.setZipCode(addressDto.zipCode());
        address.setComplement(addressDto.complement());
        
        return address;
    }

}

