package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.model.Despesa;
import br.com.fiap.jdbc.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @GetMapping
    public ResponseEntity<List<Despesa>> listar() {
        return ResponseEntity.ok(despesaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(despesaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Despesa> criar(@RequestBody @Valid Despesa despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(despesaService.salvar(despesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesa> atualizar(@PathVariable Long id, @RequestBody @Valid Despesa despesa) {
        return ResponseEntity.ok(despesaService.atualizar(id, despesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        despesaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
