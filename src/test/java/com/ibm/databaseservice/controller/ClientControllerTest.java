package com.ibm.databaseservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.databaseservice.model.Client;
import com.ibm.databaseservice.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testGetAllClients() throws Exception {
        when(clientService.findAll()).thenReturn(Arrays.asList(new Client()));
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetClientById() throws Exception {
        Client client = new Client(1L, "John Doe", 1234567890L, true, 100.0f, 1000.0f);
        when(clientService.findById(1L)).thenReturn(Optional.of(mockClient()));
        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.nome").value(client.getNome()));
    }

    @Test
    public void testCreateClient() throws Exception {
        Client client = new Client(1L, "John Doe", 1234567890L, true, 100.0f, 1000.0f);
        when(clientService.save(Mockito.any(Client.class))).thenReturn(client);
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testUpdateClient() throws Exception {
        Client client = new Client(1L, "John Doe", 1234567890L, true, 100.0f, 1000.0f);
        when(clientService.findById(1L)).thenReturn(Optional.of(client));
        when(clientService.save(client)).thenReturn(client);
        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("John Doe"));
    }

    @Test
    public void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());
    }
    private Client mockClient(){
        return new Client(1L, "John Doe", 1234567890L, true, 100.0f, 1000.0f);
    }
}