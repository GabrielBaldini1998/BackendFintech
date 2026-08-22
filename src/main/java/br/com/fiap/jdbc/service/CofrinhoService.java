package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Cofrinho;
import br.com.fiap.jdbc.repository.CofrinhoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CofrinhoService {

    private final CofrinhoRepository cofrinhoRepository;

    public CofrinhoService(CofrinhoRepository cofrinhoRepository) {
        this.cofrinhoRepository = cofrinhoRepository;
    }

    public List<Cofrinho> listarTodos() {
        return cofrinhoRepository.findAll();
    }

    public List<Cofrinho> listarPorUsuario(Long idUsuario) {
        return cofrinhoRepository.findByIdUsuario(idUsuario);
    }

    public Cofrinho buscarPorId(Long id) {
        return cofrinhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cofrinho não encontrado com ID: " + id));
    }

    public Cofrinho salvar(Cofrinho cofrinho) {
        if (cofrinho.getVlMeta() == null) cofrinho.setVlMeta(BigDecimal.ZERO);
        if (cofrinho.getVlAtual() == null) cofrinho.setVlAtual(BigDecimal.ZERO);
        return cofrinhoRepository.save(cofrinho);
    }

    public Cofrinho atualizar(Long id, Cofrinho cofrinho) {
        buscarPorId(id);
        cofrinho.setIdCofrinho(id);
        return salvar(cofrinho);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        cofrinhoRepository.deleteById(id);
    }
}
