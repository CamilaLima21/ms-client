package br.com.fiap.msclients.application.dto;

public record AddressDto(
	    long id,
	    String street,
	    String number,
	    String zipCode,
	    String complement
	) {}
