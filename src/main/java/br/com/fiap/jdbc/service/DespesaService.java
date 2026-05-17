package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Despesa;
import br.com.fiap.jdbc.repository.ContaRepository;
import br.com.fiap.jdbc.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ContaRepository contaRepository;

    public List<Despesa> listarTodos() {
        return despesaRepository.findAll();
    }

    public Despesa buscarPorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada com ID: " + id));
    }

    public List<Despesa> buscarPorConta(String numeroDaConta) {
        return despesaRepository.findByNumeroDaConta(numeroDaConta);
    }

    public Despesa salvar(Despesa despesa) {
        if (despesa.getVlDespesa() <= 0) {
            throw new IllegalArgumentException("Valor da despesa deve ser positivo.");
        }
        contaRepository.findById(despesa.getNumeroDaConta())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + despesa.getNumeroDaConta()));
        return despesaRepository.save(despesa);
    }

    public Despesa atualizar(Long id, Despesa despesa) {
        buscarPorId(id);
        despesa.setIdDespesa(id);
        return despesaRepository.save(despesa);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        despesaRepository.deleteById(id);
    }
}
