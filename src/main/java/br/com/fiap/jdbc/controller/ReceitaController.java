package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.dao.ReceitaDAO;
import br.com.fiap.jdbc.model.Receita;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    private final ReceitaDAO receitaDAO;

    public ReceitaController(ReceitaDAO receitaDAO) {
        this.receitaDAO = receitaDAO;
    }

    @GetMapping
    public List<Receita> listar() {
        return receitaDAO.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscar(@PathVariable int id) {
        return receitaDAO.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody Receita receita) {
        receitaDAO.insert(receita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody Receita receita) {
        if (receitaDAO.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        receita.setIdReceita(id);
        receitaDAO.update(receita);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (receitaDAO.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        receitaDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
