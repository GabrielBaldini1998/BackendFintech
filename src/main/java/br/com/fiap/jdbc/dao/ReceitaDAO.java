package br.com.fiap.jdbc.dao;

import br.com.fiap.jdbc.model.Receita;
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
public class ReceitaDAO {

    private final DataSource dataSource;

    public ReceitaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Receita receita) {
        // ID gerado pela trigger/sequence Oracle (SQ_T_FTC_RECEITA).
        // Se não houver trigger, substitua por: VALUES (SQ_T_FTC_RECEITA.NEXTVAL, ?, ?, ?, ?)
        String sql = "INSERT INTO T_FTC_RECEITA (dt_receita, vl_recebido, ds_receita, numero_da_conta) VALUES (?, ?, ?, ?)";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, receita.getDtReceita());
            stmt.setDouble(2, receita.getVlRecebido());
            stmt.setString(3, receita.getDsReceita());
            stmt.setString(4, receita.getNumeroDaConta());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir receita.", e);
        }
    }

    public List<Receita> getAll() {
        List<Receita> lista = new ArrayList<>();
        String sql = "SELECT id_receita, dt_receita, vl_recebido, ds_receita, numero_da_conta FROM T_FTC_RECEITA";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar receitas.", e);
        }
        return lista;
    }

    public Optional<Receita> getById(int id) {
        String sql = "SELECT id_receita, dt_receita, vl_recebido, ds_receita, numero_da_conta FROM T_FTC_RECEITA WHERE id_receita = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar receita por ID.", e);
        }
        return Optional.empty();
    }

    public void update(Receita receita) {
        String sql = "UPDATE T_FTC_RECEITA SET dt_receita = ?, vl_recebido = ?, ds_receita = ?, numero_da_conta = ? WHERE id_receita = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, receita.getDtReceita());
            stmt.setDouble(2, receita.getVlRecebido());
            stmt.setString(3, receita.getDsReceita());
            stmt.setString(4, receita.getNumeroDaConta());
            stmt.setInt(5, receita.getIdReceita());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar receita.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM T_FTC_RECEITA WHERE id_receita = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar receita.", e);
        }
    }

    private Receita mapRow(ResultSet rs) throws SQLException {
        return new Receita(
                rs.getInt("id_receita"),
                rs.getDate("dt_receita"),
                rs.getDouble("vl_recebido"),
                rs.getString("ds_receita"),
                rs.getString("numero_da_conta")
        );
    }
}
