package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.model.Conta;
import br.com.fiap.jdbc.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @GetMapping
    public ResponseEntity<List<Conta>> listar() {
        return ResponseEntity.ok(contaService.listarTodos());
    }

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<Conta> buscar(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(contaService.buscarPorId(numeroDaConta));
    }

    @PostMapping
    public ResponseEntity<Conta> criar(@RequestBody @Valid Conta conta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.salvar(conta));
    }

    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<Conta> atualizar(@PathVariable String numeroDaConta, @RequestBody @Valid Conta conta) {
        return ResponseEntity.ok(contaService.atualizar(numeroDaConta, conta));
    }

    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<Void> deletar(@PathVariable String numeroDaConta) {
        contaService.deletar(numeroDaConta);
        return ResponseEntity.noContent().build();
    }
}
