package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.model.Cofrinho;
import br.com.fiap.jdbc.service.CofrinhoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cofrinhos")
public class CofrinhoController {

    private final CofrinhoService cofrinhoService;

    public CofrinhoController(CofrinhoService cofrinhoService) {
        this.cofrinhoService = cofrinhoService;
    }

    @GetMapping
    public ResponseEntity<List<Cofrinho>> listarTodos() {
        return ResponseEntity.ok(cofrinhoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cofrinho> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cofrinhoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Cofrinho>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(cofrinhoService.listarPorUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<Cofrinho> criar(@Valid @RequestBody Cofrinho cofrinho) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cofrinhoService.salvar(cofrinho));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cofrinho> atualizar(@PathVariable Long id, @Valid @RequestBody Cofrinho cofrinho) {
        return ResponseEntity.ok(cofrinhoService.atualizar(id, cofrinho));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cofrinhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
