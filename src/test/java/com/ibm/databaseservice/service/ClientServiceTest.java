package com.ibm.databaseservice.service;

import com.ibm.databaseservice.model.Client;
import com.ibm.databaseservice.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(clientRepository.findAll()).thenReturn(Arrays.asList(new Client()));
        assertEquals(1, clientService.findAll().size());
    }

    @Test
    public void testFindById() {
        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        assertEquals(client, clientService.findById(1L).get());
    }

    @Test
    public void testSave() {
        Client client = new Client();
        when(clientRepository.save(client)).thenReturn(client);
        assertEquals(client, clientService.save(client));
    }

    @Test
    public void testDeleteById() {
        clientService.deleteById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }
}