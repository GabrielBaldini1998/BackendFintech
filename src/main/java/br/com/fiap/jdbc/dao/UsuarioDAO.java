package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO T_FTC_USUARIO (id_usuario, nm_completo, dt_nascimento, nm_cpf_usuario, ds_email, ds_senha) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getNmCompleto());
            stmt.setDate(3, usuario.getDtNascimento());
            stmt.setString(4, usuario.getNmCpfUsuario());
            stmt.setString(5, usuario.getDsEmail());
            stmt.setString(6, usuario.getDsSenha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario.", e);
        }
    }

    public List<Usuario> getAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nm_completo, dt_nascimento, nm_cpf_usuario, ds_email, ds_senha FROM T_FTC_USUARIO";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuarios.", e);
        }
        return lista;
    }

    public Optional<Usuario> getById(int id) {
        String sql = "SELECT id_usuario, nm_completo, dt_nascimento, nm_cpf_usuario, ds_email, ds_senha FROM T_FTC_USUARIO WHERE id_usuario = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuario por ID.", e);
        }
        return Optional.empty();
    }

    public void update(Usuario usuario) {
        String sql = "UPDATE T_FTC_USUARIO SET nm_completo = ?, dt_nascimento = ?, nm_cpf_usuario = ?, ds_email = ?, ds_senha = ? WHERE id_usuario = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNmCompleto());
            stmt.setDate(2, usuario.getDtNascimento());
            stmt.setString(3, usuario.getNmCpfUsuario());
            stmt.setString(4, usuario.getDsEmail());
            stmt.setString(5, usuario.getDsSenha());
            stmt.setInt(6, usuario.getIdUsuario());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuario.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM T_FTC_USUARIO WHERE id_usuario = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuario.", e);
        }
    }

    private Usuario mapRow(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nm_completo"),
                rs.getDate("dt_nascimento"),
                rs.getString("nm_cpf_usuario"),
                rs.getString("ds_email"),
                rs.getString("ds_senha")
        );
    }
}
