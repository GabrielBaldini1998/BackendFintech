package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Despesa;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DespesaDAO {

    private final DataSource dataSource;

    public DespesaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Despesa despesa) {
        String sql = "INSERT INTO T_FTC_DESPESA (id_despesa, tp_despesa, vl_despesa, dt_despesa, numero_da_conta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, despesa.getIdDespesa());
            stmt.setString(2, despesa.getTpDespesa());
            stmt.setDouble(3, despesa.getVlDespesa());
            stmt.setDate(4, despesa.getDtDespesa());
            stmt.setString(5, despesa.getNumeroDaConta());

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

            while (rs.next()) {
                Despesa despesa = new Despesa(
                        rs.getInt("id_despesa"),
                        rs.getString("tp_despesa"),
                        rs.getDouble("vl_despesa"),
                        rs.getDate("dt_despesa"),
                        rs.getString("numero_da_conta")
                );
                lista.add(despesa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar despesas.", e);
        }

        return lista;
    }
}
