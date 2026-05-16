package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.dao.DespesaDAO;
import br.com.fiap.jdbc.model.Despesa;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    private final DespesaDAO despesaDAO;

    public DespesaController(DespesaDAO despesaDAO) {
        this.despesaDAO = despesaDAO;
    }

    @GetMapping
    public List<Despesa> listar() {
        return despesaDAO.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody Despesa despesa) {
        despesaDAO.insert(despesa);
    }
}
