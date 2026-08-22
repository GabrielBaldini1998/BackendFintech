package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAO {

    private final DataSource dataSource;

    public UsuarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Usuario usuario) {
        String sql = "INSERT INTO T_FTC_USUARIO (id_usuario, nm_completo, dt_nascimento, nm_documento, tp_tipo, ds_email, ds_senha) VALUES (SEQ_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNmCompleto());
            stmt.setObject(2, usuario.getDtNascimento());
            stmt.setString(3, usuario.getNmDocumento());
            stmt.setString(4, usuario.getTpTipo());
            stmt.setString(5, usuario.getDsEmail());
            stmt.setString(6, usuario.getDsSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario.", e);
        }
    }

    public List<Usuario> getAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nm_completo, dt_nascimento, nm_documento, tp_tipo, ds_email, ds_senha FROM T_FTC_USUARIO";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuarios.", e);
        }
        return lista;
    }

    public Optional<Usuario> getById(Long id) {
        String sql = "SELECT id_usuario, nm_completo, dt_nascimento, nm_documento, tp_tipo, ds_email, ds_senha FROM T_FTC_USUARIO WHERE id_usuario = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por ID.", e);
        }
        return Optional.empty();
    }

    public void update(Usuario usuario) {
        String sql = "UPDATE T_FTC_USUARIO SET nm_completo = ?, dt_nascimento = ?, nm_documento = ?, tp_tipo = ?, ds_email = ?, ds_senha = ? WHERE id_usuario = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNmCompleto());
            stmt.setObject(2, usuario.getDtNascimento());
            stmt.setString(3, usuario.getNmDocumento());
            stmt.setString(4, usuario.getTpTipo());
            stmt.setString(5, usuario.getDsEmail());
            stmt.setString(6, usuario.getDsSenha());
            stmt.setLong(7, usuario.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuario.", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM T_FTC_USUARIO WHERE id_usuario = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuario.", e);
        }
    }

    private Usuario mapRow(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getLong("id_usuario"),
                rs.getString("nm_completo"),
                rs.getObject("dt_nascimento", java.time.LocalDate.class),
                rs.getString("nm_documento"),
                rs.getString("tp_tipo"),
                rs.getString("ds_email"),
                rs.getString("ds_senha")
        );
    }
}
