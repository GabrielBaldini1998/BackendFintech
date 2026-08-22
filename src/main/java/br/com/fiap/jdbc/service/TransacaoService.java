package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Transacao;
import br.com.fiap.jdbc.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public List<Transacao> listarTodas() {
        return transacaoRepository.findAll();
    }

    public List<Transacao> listarPorUsuario(Long idUsuario) {
        return transacaoRepository.findByIdUsuario(idUsuario);
    }

    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada com ID: " + id));
    }

    public Transacao salvar(Transacao transacao) {
        if (transacao.getTpTransacao() == null ||
                (!transacao.getTpTransacao().equals("RECEITA") && !transacao.getTpTransacao().equals("DESPESA"))) {
            throw new IllegalArgumentException("tpTransacao deve ser 'RECEITA' ou 'DESPESA'");
        }
        if (transacao.getVlTransacao() == null || transacao.getVlTransacao().signum() <= 0) {
            throw new IllegalArgumentException("vlTransacao deve ser maior que zero");
        }
        return transacaoRepository.save(transacao);
    }

    public Transacao atualizar(Long id, Transacao transacao) {
        buscarPorId(id);
        transacao.setIdTransacao(id);
        return salvar(transacao);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        transacaoRepository.deleteById(id);
    }
}
