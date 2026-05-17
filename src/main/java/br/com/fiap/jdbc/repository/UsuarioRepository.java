package br.com.fiap.jdbc.repository;

import br.com.fiap.jdbc.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNmCpfUsuario(String nmCpfUsuario);
    Optional<Usuario> findByDsEmail(String dsEmail);
}
