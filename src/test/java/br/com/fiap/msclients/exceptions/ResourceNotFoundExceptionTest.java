package br.com.fiap.msclients.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

	@Test
	void testResourceNotFoundExceptionMessage() {
		// Arrange
		String errorMessage = "Resource not found";

		// Act
		ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

		// Assert
		assertNotNull(exception, "A exceção deve ser inicializada");
		assertEquals(errorMessage, exception.getMessage(),
				"A mensagem da exceção deve corresponder à mensagem fornecida");
	}

	@Test
	void testResourceNotFoundExceptionWithoutMessage() {
		// Act
		ResourceNotFoundException exception = new ResourceNotFoundException(null);

		// Assert
		assertNotNull(exception, "A exceção deve ser inicializada mesmo com uma mensagem nula");
		assertNull(exception.getMessage(), "A mensagem deve ser nula quando fornecida como null");
	}
}
