package br.com.fiap.msclients.infrastructure.web.controller;

import br.com.fiap.msclients.application.dto.ClientDto;
import br.com.fiap.msclients.application.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final ClientDto client = new ClientDto(
            1L, "Ana", "ana@email.com", "12345678900",
            LocalDate.of(1990, 5, 20), 10L
    );

    @Test
    void shouldFindAllClients() throws Exception {
        when(service.findAll()).thenReturn(List.of(client));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Ana")));
    }

    @Test
    void shouldFindClientById() throws Exception {
        when(service.findById(1L)).thenReturn(client);

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("ana@email.com")));
    }

    @Test
    void shouldFindClientByCpf() throws Exception {
        when(service.findByCpf("12345678900")).thenReturn(client);

        mockMvc.perform(get("/clients/cpf/12345678900"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", is("12345678900")));
    }

    @Test
    void shouldFindClientByEmail() throws Exception {
        when(service.findByEmail("ana@email.com")).thenReturn(client);

        mockMvc.perform(get("/clients/email?email=ana@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ana")));
    }

    @Test
    void shouldFindClientByName() throws Exception {
        when(service.findByName("Ana")).thenReturn(client);

        mockMvc.perform(get("/clients/name?name=Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

//    @Test
//    void shouldCreateClient() throws Exception {
//        when(service.create(client)).thenReturn(client);
//
//        mockMvc.perform(post("/clients")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(client)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name", is("Ana")));
//    }

//    @Test
//    void shouldUpdateClient() throws Exception {
//        when(service.update(1L, client)).thenReturn(client);
//
//        mockMvc.perform(put("/clients/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(client)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email", is("ana@email.com")));
//    }
//
//    @Test
//    void shouldDeleteClient() throws Exception {
//        when(service.delete(1L)).thenReturn("Deleted");
//
//        mockMvc.perform(delete("/clients/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Deleted"));
//    }
    
 // Ao final da classe ClientControllerTest:

    @Test
    void shouldValidateClientExists() throws Exception {
        mockMvc.perform(get("/clients/validate/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateClient() throws Exception {
        when(service.create(client)).thenReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf", is("12345678900")));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        when(service.update(1L, client)).thenReturn(client);

        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Ana")));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        when(service.delete(1L)).thenReturn("Client deleted successfully!");

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Client deleted successfully!"));
    }

}
