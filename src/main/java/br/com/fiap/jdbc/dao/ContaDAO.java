package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Conta;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaDAO {

    private final DataSource dataSource;

    public ContaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Conta conta) {
        String sql = "INSERT INTO T_FTC_CONTA (numero_da_conta, titular, agencia, tipo, saldo, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, conta.getNumeroDaConta());
            stmt.setString(2, conta.getTitular());
            stmt.setString(3, conta.getAgencia());
            stmt.setString(4, conta.getTipo());
            stmt.setDouble(5, conta.getSaldo());
            stmt.setLong(6, conta.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir conta.", e);
        }
    }

    public List<Conta> getAll() {
        List<Conta> lista = new ArrayList<>();
        String sql = "SELECT numero_da_conta, titular, agencia, tipo, saldo, id_usuario FROM T_FTC_CONTA";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas.", e);
        }
        return lista;
    }

    public Optional<Conta> getById(String numeroDaConta) {
        String sql = "SELECT numero_da_conta, titular, agencia, tipo, saldo, id_usuario FROM T_FTC_CONTA WHERE numero_da_conta = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, numeroDaConta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta por numero.", e);
        }
        return Optional.empty();
    }

    public void update(Conta conta) {
        String sql = "UPDATE T_FTC_CONTA SET titular = ?, agencia = ?, tipo = ?, saldo = ?, id_usuario = ? WHERE numero_da_conta = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, conta.getTitular());
            stmt.setString(2, conta.getAgencia());
            stmt.setString(3, conta.getTipo());
            stmt.setDouble(4, conta.getSaldo());
            stmt.setLong(5, conta.getIdUsuario());
            stmt.setString(6, conta.getNumeroDaConta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta.", e);
        }
    }

    public void delete(String numeroDaConta) {
        String sql = "DELETE FROM T_FTC_CONTA WHERE numero_da_conta = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, numeroDaConta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta.", e);
        }
    }

    private Conta mapRow(ResultSet rs) throws SQLException {
        return new Conta(
                rs.getString("numero_da_conta"),
                rs.getString("titular"),
                rs.getString("agencia"),
                rs.getString("tipo"),
                rs.getDouble("saldo"),
                rs.getLong("id_usuario")
        );
    }
}
