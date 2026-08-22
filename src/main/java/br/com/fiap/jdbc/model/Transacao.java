package br.com.fiap.jdbc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_FTC_TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transacao")
    @SequenceGenerator(name = "seq_transacao", sequenceName = "SEQ_TRANSACAO", allocationSize = 1)
    @Column(name = "id_transacao")
    private Long idTransacao;

    @NotNull
    @Column(name = "tp_transacao", length = 10, nullable = false)
    private String tpTransacao; // "RECEITA" | "DESPESA"

    @NotNull
    @Column(name = "ds_transacao", length = 200, nullable = false)
    private String dsTransacao;

    @NotNull
    @Column(name = "vl_transacao", nullable = false, precision = 15, scale = 2)
    private BigDecimal vlTransacao;

    @Column(name = "dt_transacao")
    private LocalDate dtTransacao;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_cofrinho")
    private Long idCofrinho;

    public Transacao() {}

    public Long getIdTransacao() { return idTransacao; }
    public void setIdTransacao(Long idTransacao) { this.idTransacao = idTransacao; }
    public String getTpTransacao() { return tpTransacao; }
    public void setTpTransacao(String tpTransacao) { this.tpTransacao = tpTransacao; }
    public String getDsTransacao() { return dsTransacao; }
    public void setDsTransacao(String dsTransacao) { this.dsTransacao = dsTransacao; }
    public BigDecimal getVlTransacao() { return vlTransacao; }
    public void setVlTransacao(BigDecimal vlTransacao) { this.vlTransacao = vlTransacao; }
    public LocalDate getDtTransacao() { return dtTransacao; }
    public void setDtTransacao(LocalDate dtTransacao) { this.dtTransacao = dtTransacao; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdCofrinho() { return idCofrinho; }
    public void setIdCofrinho(Long idCofrinho) { this.idCofrinho = idCofrinho; }

    @Override
    public String toString() {
        return String.format("Transacao [ID: %d | Tipo: %s | Desc: %s | Valor: %s]",
                idTransacao, tpTransacao, dsTransacao, vlTransacao);
    }
}
