package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.dao.InvestimentoDAO;
import br.com.fiap.jdbc.model.Investimento;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/investimentos")
public class InvestimentoController {

    private final InvestimentoDAO investimentoDAO;

    public InvestimentoController(InvestimentoDAO investimentoDAO) {
        this.investimentoDAO = investimentoDAO;
    }

    @GetMapping
    public List<Investimento> listar() {
        return investimentoDAO.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscar(@PathVariable int id) {
        return investimentoDAO.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody Investimento investimento) {
        investimentoDAO.insert(investimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody Investimento investimento) {
        if (investimentoDAO.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        investimento.setIdInvestimento(id);
        investimentoDAO.update(investimento);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (investimentoDAO.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        investimentoDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
