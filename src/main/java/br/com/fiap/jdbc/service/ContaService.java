package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Conta;
import br.com.fiap.jdbc.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public List<Conta> listarTodos() {
        return contaRepository.findAll();
    }

    public Conta buscarPorId(String numeroDaConta) {
        return contaRepository.findById(numeroDaConta)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + numeroDaConta));
    }

    public List<Conta> buscarPorUsuario(Long idUsuario) {
        return contaRepository.findByIdUsuario(idUsuario);
    }

    public Conta salvar(Conta conta) {
        if (conta.getSaldo() < 0) {
            throw new IllegalArgumentException("Saldo inicial não pode ser negativo.");
        }
        return contaRepository.save(conta);
    }

    public Conta atualizar(String numeroDaConta, Conta conta) {
        buscarPorId(numeroDaConta);
        conta.setNumeroDaConta(numeroDaConta);
        return contaRepository.save(conta);
    }

    public void deletar(String numeroDaConta) {
        buscarPorId(numeroDaConta);
        contaRepository.deleteById(numeroDaConta);
    }
}
