package br.com.fiap.jdbc.repository;

import br.com.fiap.jdbc.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, String> {
    List<Conta> findByIdUsuario(Long idUsuario);
    List<Conta> findByTipo(String tipo);
}
