package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Receita;
import br.com.fiap.jdbc.repository.ContaRepository;
import br.com.fiap.jdbc.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ContaRepository contaRepository;

    public List<Receita> listarTodos() {
        return receitaRepository.findAll();
    }

    public Receita buscarPorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada com ID: " + id));
    }

    public List<Receita> buscarPorConta(String numeroDaConta) {
        return receitaRepository.findByNumeroDaConta(numeroDaConta);
    }

    public Receita salvar(Receita receita) {
        if (receita.getVlRecebido() <= 0) {
            throw new IllegalArgumentException("Valor recebido deve ser positivo.");
        }
        contaRepository.findById(receita.getNumeroDaConta())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + receita.getNumeroDaConta()));
        return receitaRepository.save(receita);
    }

    public Receita atualizar(Long id, Receita receita) {
        buscarPorId(id);
        receita.setIdReceita(id);
        return receitaRepository.save(receita);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        receitaRepository.deleteById(id);
    }
}
