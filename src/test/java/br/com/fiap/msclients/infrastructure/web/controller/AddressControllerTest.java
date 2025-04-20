package br.com.fiap.msclients.infrastructure.web.controller;

import br.com.fiap.msclients.application.dto.AddressDto;
import br.com.fiap.msclients.application.service.AddressService;
import br.com.fiap.msclients.infrastructure.web.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService service;

    private final AddressDto sampleDto = new AddressDto(
            1L, "Rua Teste", "100", "12345-678", "Apto 101"
    );

    @Test
    @DisplayName("GET /addresses - deve retornar lista de endereços")
    void shouldReturnAllAddresses() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(sampleDto));

        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].street", is("Rua Teste")))
                .andExpect(jsonPath("$[0].number", is("100")))
                .andExpect(jsonPath("$[0].zipCode", is("12345-678")))
                .andExpect(jsonPath("$[0].complement", is("Apto 101")));
    }

    @Test
    @DisplayName("GET /addresses/{id} - deve retornar endereço por ID")
    void shouldReturnAddressById() throws Exception {
        Mockito.when(service.findById(1L)).thenReturn(sampleDto);

        mockMvc.perform(get("/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.zipCode", is("12345-678")));
    }

    @Test
    @DisplayName("GET /addresses/cep/{zipCode} - deve retornar endereço por CEP")
    void shouldReturnAddressByZipCode() throws Exception {
        Mockito.when(service.findByZipCode("12345-678")).thenReturn(sampleDto);

        mockMvc.perform(get("/addresses/cep/12345-678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zipCode", is("12345-678")));
    }

    @Test
    @DisplayName("POST /addresses - deve criar novo endereço")
    void shouldCreateAddress() throws Exception {
        Mockito.when(service.create(any(AddressDto.class))).thenReturn(sampleDto);

        String json = """
                {
                    "id": 1,
                    "street": "Rua Teste",
                    "number": "100",
                    "zipCode": "12345-678",
                    "complement": "Apto 101"
                }
                """;

        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.zipCode", is("12345-678")));
    }

    @Test
    @DisplayName("PUT /addresses/{id} - deve atualizar endereço")
    void shouldUpdateAddress() throws Exception {
        Mockito.when(service.updateAddress(eq(1L), any(AddressDto.class))).thenReturn(sampleDto);

        String json = """
                {
                    "id": 1,
                    "street": "Rua Teste",
                    "number": "100",
                    "zipCode": "12345-678",
                    "complement": "Apto 101"
                }
                """;

        mockMvc.perform(put("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.zipCode", is("12345-678")));
    }

    @Test
    @DisplayName("DELETE /addresses/{id} - deve deletar endereço")
    void shouldDeleteAddress() throws Exception {
        Mockito.when(service.delete(1L)).thenReturn("Endereço deletado com sucesso.");

        mockMvc.perform(delete("/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Endereço deletado com sucesso."));
    }

    @Test
    @DisplayName("GET /addresses/{id} - deve retornar 404 se não encontrado")
    void shouldReturn404WhenAddressNotFound() throws Exception {
        Mockito.when(service.findById(99L)).thenThrow(new ResourceNotFoundException("Endereço não encontrado"));

        mockMvc.perform(get("/addresses/99"))
                .andExpect(status().isNotFound());
    }
}
