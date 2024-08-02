package com.ibm.databaseservice.controller;

import com.ibm.databaseservice.model.Client;
import com.ibm.databaseservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.findById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            Client updatedClient = client.get();
            updatedClient.setNome(clientDetails.getNome());
            updatedClient.setTelefone(clientDetails.getTelefone());
            updatedClient.setCorrentista(clientDetails.getCorrentista());
            updatedClient.setScore_credito(clientDetails.getScore_credito());
            updatedClient.setSaldo_cc(clientDetails.getSaldo_cc());
            return ResponseEntity.ok(clientService.save(updatedClient));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}