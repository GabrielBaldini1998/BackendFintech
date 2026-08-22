package br.com.fiap.jdbc.repository;

import br.com.fiap.jdbc.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByIdUsuario(Long idUsuario);
    List<Transacao> findByIdUsuarioAndTpTransacao(Long idUsuario, String tpTransacao);
}
