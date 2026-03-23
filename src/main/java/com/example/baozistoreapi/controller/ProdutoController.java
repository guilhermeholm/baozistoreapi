package com.example.baozistoreapi.controller;

import com.example.baozistoreapi.model.Produto;
import com.example.baozistoreapi.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.repository = produtoRepository;
    }

    @GetMapping public List<Produto> findAll() { return repository.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Produto> findById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public Produto create(@RequestBody Produto produto) { return repository.save(produto); }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return repository.findById(id)
                .map(record -> {
                    if (produtoAtualizado.getNome() != null) record.setNome(produtoAtualizado.getNome());
                    if (produtoAtualizado.getPreco() != null) record.setPreco(produtoAtualizado.getPreco());
                    if (produtoAtualizado.getEstoque() != null) record.setEstoque(produtoAtualizado.getEstoque());
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