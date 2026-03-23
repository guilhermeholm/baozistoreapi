package com.example.baozistoreapi.controller;

import com.example.baozistoreapi.model.Cliente;
import com.example.baozistoreapi.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.repository = clienteRepository;
    }

    @GetMapping
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        return repository.findById(id)
                .map(record -> {
                    if (clienteAtualizado.getNome() != null) {
                        record.setNome(clienteAtualizado.getNome());
                    }

                    if (clienteAtualizado.getClienteDesde() != null) {
                        record.setClienteDesde(clienteAtualizado.getClienteDesde());
                    }
                    Cliente updated = repository.save(record);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}