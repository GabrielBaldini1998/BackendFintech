package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.dao.ContaDAO;
import br.com.fiap.jdbc.model.Conta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaDAO contaDAO;

    public ContaController(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }

    @GetMapping
    public List<Conta> listar() {
        return contaDAO.getAll();
    }

    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<Conta> buscar(@PathVariable String numeroDaConta) {
        return contaDAO.getById(numeroDaConta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody Conta conta) {
        contaDAO.insert(conta);
    }

    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<Void> atualizar(@PathVariable String numeroDaConta, @RequestBody Conta conta) {
        if (contaDAO.getById(numeroDaConta).isEmpty()) return ResponseEntity.notFound().build();
        conta.setNumeroDaConta(numeroDaConta);
        contaDAO.update(conta);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<Void> deletar(@PathVariable String numeroDaConta) {
        if (contaDAO.getById(numeroDaConta).isEmpty()) return ResponseEntity.notFound().build();
        contaDAO.delete(numeroDaConta);
        return ResponseEntity.noContent().build();
    }
}
