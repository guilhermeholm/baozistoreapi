package com.example.baozistoreapi.controller;

import com.example.baozistoreapi.model.Pedido;
import com.example.baozistoreapi.repository.PedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoRepository repository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.repository = pedidoRepository;
    }

    @GetMapping public List<Pedido> findAll() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Pedido> findById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public Pedido create(@RequestBody Pedido pedido) { return repository.save(pedido); }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        return repository.findById(id)
                .map(record -> {
                    if (pedidoAtualizado.getClienteId() != null) record.setClienteId(pedidoAtualizado.getClienteId());
                    if (pedidoAtualizado.getProdutoId() != null) record.setProdutoId(pedidoAtualizado.getProdutoId());
                    if (pedidoAtualizado.getQuantidade() != null) record.setQuantidade(pedidoAtualizado.getQuantidade());
                    return ResponseEntity.ok(repository.save(record));
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