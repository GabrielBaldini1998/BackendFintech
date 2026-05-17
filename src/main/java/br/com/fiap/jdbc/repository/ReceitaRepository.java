package br.com.fiap.jdbc.repository;

import br.com.fiap.jdbc.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByNumeroDaConta(String numeroDaConta);
    List<Receita> findByDsReceita(String dsReceita);
}
