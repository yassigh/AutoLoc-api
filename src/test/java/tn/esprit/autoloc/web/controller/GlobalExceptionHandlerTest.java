package tn.esprit.autoloc.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import tn.esprit.autoloc.dto.request.ClientRequestDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Client", 999L);
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleResourceNotFound(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Not Found", response.getBody().get("error"));
        assertTrue(response.getBody().get("message").toString().contains("Client"));
    }

    @Test
    void testHandleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("Paramètre invalide");
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleIllegalArgument(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Paramètre invalide", response.getBody().get("message"));
    }

    @Test
    void testHandleValidationExceptions() throws NoSuchMethodException {
        ClientRequestDTO target = new ClientRequestDTO();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "clientRequestDTO");
        bindingResult.addError(new FieldError("clientRequestDTO", "email", "L'adresse email doit être valide"));
        bindingResult.addError(new FieldError("clientRequestDTO", "nom", "Le nom du client est obligatoire"));

        MethodParameter parameter = new MethodParameter(
                ClientController.class.getMethod("create", ClientRequestDTO.class), 0);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);

        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Validation Error", response.getBody().get("error"));
        assertEquals("Erreur de validation des données transmises", response.getBody().get("message"));

        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody().get("errors");
        assertNotNull(errors);
        assertEquals("L'adresse email doit être valide", errors.get("email"));
        assertEquals("Le nom du client est obligatoire", errors.get("nom"));
    }
}
