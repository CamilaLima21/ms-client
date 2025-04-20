package br.com.fiap.msclients.infrastructure.web.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import br.com.fiap.msclients.infrastructure.web.exceptions.ErrorDetails;
import br.com.fiap.msclients.infrastructure.web.exceptions.GlobalExceptionHandler;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;

class GlobalExceptionHandlerTest {

	private GlobalExceptionHandler globalExceptionHandler;

	@Mock
	private WebRequest webRequest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		globalExceptionHandler = new GlobalExceptionHandler();
	}

	@Test
	void testResourceNotFoundException() {
		// Arrange
		ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
		when(webRequest.getDescription(false)).thenReturn("uri=/test");

		// Act
		ResponseEntity<?> response = globalExceptionHandler.resourceNotFoundException(exception, webRequest);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		ErrorDetails errorDetails = (ErrorDetails) response.getBody();
		assertNotNull(errorDetails);
		assertEquals("Resource not found", errorDetails.getMessage());
		assertEquals("uri=/test", errorDetails.getDetails());
	}

	@Test
	void testGlobleExcpetionHandler() {
		// Arrange
		Exception exception = new Exception("Internal server error");
		when(webRequest.getDescription(false)).thenReturn("uri=/test");

		// Act
		ResponseEntity<?> response = globalExceptionHandler.globleExcpetionHandler(exception, webRequest);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

		ErrorDetails errorDetails = (ErrorDetails) response.getBody();
		assertNotNull(errorDetails);
		assertEquals("Internal server error", errorDetails.getMessage());
		assertEquals("uri=/test", errorDetails.getDetails());
	}

	@Test
	void testHandleValidationExceptions() {
		// Arrange
		BindingResult bindingResult = mock(BindingResult.class);
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

		FieldError fieldError = new FieldError("objectName", "fieldName", "Error message");
		when(bindingResult.getAllErrors()).thenReturn(java.util.List.of(fieldError));

		// Act
		ResponseEntity<Object> response = globalExceptionHandler.handleValidationExceptions(exception, webRequest);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		@SuppressWarnings("unchecked")
		Map<String, String> errors = (Map<String, String>) response.getBody();
		assertNotNull(errors);
		assertEquals(1, errors.size());
		assertEquals("Error message", errors.get("fieldName"));
	}
}
