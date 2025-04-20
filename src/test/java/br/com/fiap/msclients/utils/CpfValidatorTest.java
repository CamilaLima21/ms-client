package br.com.fiap.msclients.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CpfValidatorTest {

	private final CpfValidator cpfValidator = new CpfValidator();

	@Test
	void testValidateCpfWithValidCpf() {
		// Arrange
		String validCpf = "12345678909"; // CPF fictício válido para os cálculos

		// Act
		boolean isValid = cpfValidator.validateCpf(validCpf);

		// Assert
		assertTrue(isValid, "O CPF válido deve retornar true");
	}

	@Test
	void testValidateCpfWithInvalidCpf() {
		// Arrange
		String invalidCpf = "12345678901"; // CPF inválido

		// Act
		boolean isValid = cpfValidator.validateCpf(invalidCpf);

		// Assert
		assertFalse(isValid, "O CPF inválido deve retornar false");
	}

	@Test
	void testValidateCpfWithNullCpf() {
		// Arrange
		String nullCpf = null;

		// Act
		boolean isValid = cpfValidator.validateCpf(nullCpf);

		// Assert
		assertFalse(isValid, "Um CPF nulo deve retornar false");
	}

	@Test
	void testValidateCpfWithIncorrectLength() {
		// Arrange
		String shortCpf = "1234567"; // CPF com menos de 11 dígitos
		String longCpf = "123456789012"; // CPF com mais de 11 dígitos

		// Act
		boolean isShortValid = cpfValidator.validateCpf(shortCpf);
		boolean isLongValid = cpfValidator.validateCpf(longCpf);

		// Assert
		assertFalse(isShortValid, "Um CPF com menos de 11 dígitos deve retornar false");
		assertFalse(isLongValid, "Um CPF com mais de 11 dígitos deve retornar false");
	}

	@Test
	void testValidateCpfWithNonNumericCharacters() {
		// Arrange
		String nonNumericCpf = "12345678abc"; // CPF contendo caracteres não numéricos

		// Act
		boolean isValid = cpfValidator.validateCpf(nonNumericCpf);

		// Assert
		assertFalse(isValid, "Um CPF com caracteres não numéricos deve retornar false");
	}
}
