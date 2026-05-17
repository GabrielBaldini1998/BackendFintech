package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.model.Investimento;
import br.com.fiap.jdbc.service.InvestimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/investimentos")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @GetMapping
    public ResponseEntity<List<Investimento>> listar() {
        return ResponseEntity.ok(investimentoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(investimentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Investimento> criar(@RequestBody @Valid Investimento investimento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(investimentoService.salvar(investimento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investimento> atualizar(@PathVariable Long id, @RequestBody @Valid Investimento investimento) {
        return ResponseEntity.ok(investimentoService.atualizar(id, investimento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        investimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
