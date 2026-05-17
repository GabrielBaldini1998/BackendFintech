package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Investimento;
import br.com.fiap.jdbc.repository.ContaRepository;
import br.com.fiap.jdbc.repository.InvestimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentoRepository;

    @Autowired
    private ContaRepository contaRepository;

    public List<Investimento> listarTodos() {
        return investimentoRepository.findAll();
    }

    public Investimento buscarPorId(Long id) {
        return investimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investimento não encontrado com ID: " + id));
    }

    public List<Investimento> buscarPorConta(String numeroDaConta) {
        return investimentoRepository.findByNumeroDaConta(numeroDaConta);
    }

    public Investimento salvar(Investimento investimento) {
        if (investimento.getVlAplicacao() <= 0) {
            throw new IllegalArgumentException("Valor de aplicação deve ser positivo.");
        }
        if (investimento.getDtVencimentoAplicacao() != null && investimento.getDtAplicacao() != null
                && investimento.getDtVencimentoAplicacao().before(investimento.getDtAplicacao())) {
            throw new IllegalArgumentException("Data de vencimento deve ser posterior à data de aplicação.");
        }
        contaRepository.findById(investimento.getNumeroDaConta())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + investimento.getNumeroDaConta()));
        return investimentoRepository.save(investimento);
    }

    public Investimento atualizar(Long id, Investimento investimento) {
        buscarPorId(id);
        investimento.setIdInvestimento(id);
        return investimentoRepository.save(investimento);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        investimentoRepository.deleteById(id);
    }
}
