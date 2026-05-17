package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Investimento;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InvestimentoDAO {

    private final DataSource dataSource;

    public InvestimentoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Investimento investimento) {
        String sql = "INSERT INTO T_FTC_INVESTIMENTO (id_investimento, nm_aplicacao, nm_banco_corretora, vl_aplicacao, dt_aplicacao, dt_vencimento_aplicacao, numero_da_conta) VALUES (SEQ_INVESTIMENTO.NEXTVAL, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, investimento.getNmAplicacao());
            stmt.setString(2, investimento.getNmBancoCorretora());
            stmt.setDouble(3, investimento.getVlAplicacao());
            stmt.setDate(4, investimento.getDtAplicacao());
            stmt.setDate(5, investimento.getDtVencimentoAplicacao());
            stmt.setString(6, investimento.getNumeroDaConta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir investimento.", e);
        }
    }

    public List<Investimento> getAll() {
        List<Investimento> lista = new ArrayList<>();
        String sql = "SELECT id_investimento, nm_aplicacao, nm_banco_corretora, vl_aplicacao, dt_aplicacao, dt_vencimento_aplicacao, numero_da_conta FROM T_FTC_INVESTIMENTO";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar investimentos.", e);
        }
        return lista;
    }

    public Optional<Investimento> getById(Long id) {
        String sql = "SELECT id_investimento, nm_aplicacao, nm_banco_corretora, vl_aplicacao, dt_aplicacao, dt_vencimento_aplicacao, numero_da_conta FROM T_FTC_INVESTIMENTO WHERE id_investimento = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar investimento por ID.", e);
        }
        return Optional.empty();
    }

    public void update(Investimento investimento) {
        String sql = "UPDATE T_FTC_INVESTIMENTO SET nm_aplicacao = ?, nm_banco_corretora = ?, vl_aplicacao = ?, dt_aplicacao = ?, dt_vencimento_aplicacao = ?, numero_da_conta = ? WHERE id_investimento = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, investimento.getNmAplicacao());
            stmt.setString(2, investimento.getNmBancoCorretora());
            stmt.setDouble(3, investimento.getVlAplicacao());
            stmt.setDate(4, investimento.getDtAplicacao());
            stmt.setDate(5, investimento.getDtVencimentoAplicacao());
            stmt.setString(6, investimento.getNumeroDaConta());
            stmt.setLong(7, investimento.getIdInvestimento());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar investimento.", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM T_FTC_INVESTIMENTO WHERE id_investimento = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar investimento.", e);
        }
    }

    private Investimento mapRow(ResultSet rs) throws SQLException {
        return new Investimento(
                rs.getLong("id_investimento"),
                rs.getString("nm_aplicacao"),
                rs.getString("nm_banco_corretora"),
                rs.getDouble("vl_aplicacao"),
                rs.getDate("dt_aplicacao"),
                rs.getDate("dt_vencimento_aplicacao"),
                rs.getString("numero_da_conta")
        );
    }
}
