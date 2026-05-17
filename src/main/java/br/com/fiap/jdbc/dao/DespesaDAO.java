package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Despesa;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DespesaDAO {

    private final DataSource dataSource;

    public DespesaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Despesa despesa) {
        String sql = "INSERT INTO T_FTC_DESPESA (id_despesa, tp_despesa, vl_despesa, dt_despesa, numero_da_conta) VALUES (SEQ_DESPESA.NEXTVAL, ?, ?, ?, ?)";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, despesa.getTpDespesa());
            stmt.setDouble(2, despesa.getVlDespesa());
            stmt.setDate(3, despesa.getDtDespesa());
            stmt.setString(4, despesa.getNumeroDaConta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir despesa.", e);
        }
    }

    public List<Despesa> getAll() {
        List<Despesa> lista = new ArrayList<>();
        String sql = "SELECT id_despesa, tp_despesa, vl_despesa, dt_despesa, numero_da_conta FROM T_FTC_DESPESA";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesas.", e);
        }
        return lista;
    }

    public Optional<Despesa> getById(Long id) {
        String sql = "SELECT id_despesa, tp_despesa, vl_despesa, dt_despesa, numero_da_conta FROM T_FTC_DESPESA WHERE id_despesa = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesa por ID.", e);
        }
        return Optional.empty();
    }

    public void update(Despesa despesa) {
        String sql = "UPDATE T_FTC_DESPESA SET tp_despesa = ?, vl_despesa = ?, dt_despesa = ?, numero_da_conta = ? WHERE id_despesa = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, despesa.getTpDespesa());
            stmt.setDouble(2, despesa.getVlDespesa());
            stmt.setDate(3, despesa.getDtDespesa());
            stmt.setString(4, despesa.getNumeroDaConta());
            stmt.setLong(5, despesa.getIdDespesa());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar despesa.", e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM T_FTC_DESPESA WHERE id_despesa = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar despesa.", e);
        }
    }

    private Despesa mapRow(ResultSet rs) throws SQLException {
        return new Despesa(
                rs.getLong("id_despesa"),
                rs.getString("tp_despesa"),
                rs.getDouble("vl_despesa"),
                rs.getDate("dt_despesa"),
                rs.getString("numero_da_conta")
        );
    }
}
