package br.com.fiap.msclients.infrastructure.web.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.fiap.msclients.infrastructure.web.exceptions.ErrorDetails;

import java.util.Date;

class ErrorDetailsTest {

	@Test
	void testErrorDetailsConstructorAndGetterMethods() {
		// Arrange
		Date timestamp = new Date();
		String message = "An error occurred";
		String details = "Resource not found";

		// Act
		ErrorDetails errorDetails = new ErrorDetails(timestamp, message, details);

		// Assert
		assertNotNull(errorDetails, "ErrorDetails object should not be null");
		assertEquals(timestamp, errorDetails.getTimestamp(), "Timestamp should match the input");
		assertEquals(message, errorDetails.getMessage(), "Message should match the input");
		assertEquals(details, errorDetails.getDetails(), "Details should match the input");
	}
}
