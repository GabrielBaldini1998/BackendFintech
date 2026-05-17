package br.com.fiap.jdbc.repository;

import br.com.fiap.jdbc.model.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {
    List<Investimento> findByNumeroDaConta(String numeroDaConta);
    List<Investimento> findByNmBancoCorretora(String nmBancoCorretora);
}
