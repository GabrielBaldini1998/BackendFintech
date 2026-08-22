package br.com.fiap.jdbc.repository;

import br.com.fiap.jdbc.model.Cofrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CofrinhoRepository extends JpaRepository<Cofrinho, Long> {
    List<Cofrinho> findByIdUsuario(Long idUsuario);
}
