package br.com.fiap.jdbc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "T_FTC_COFRINHO")
public class Cofrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cofrinho")
    @SequenceGenerator(name = "seq_cofrinho", sequenceName = "SEQ_COFRINHO", allocationSize = 1)
    @Column(name = "id_cofrinho")
    private Long idCofrinho;

    @NotNull
    @Column(name = "nm_cofrinho", length = 100, nullable = false)
    private String nmCofrinho;

    @Column(name = "ds_cofrinho", length = 200)
    private String dsCofrinho;

    @Column(name = "vl_meta", precision = 15, scale = 2)
    private BigDecimal vlMeta = BigDecimal.ZERO;

    @Column(name = "vl_atual", precision = 15, scale = 2)
    private BigDecimal vlAtual = BigDecimal.ZERO;

    @Column(name = "ds_icone", length = 50)
    private String dsIcone;

    @Column(name = "ds_cor", length = 20)
    private String dsCor;

    @NotNull
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    public Cofrinho() {}

    public Long getIdCofrinho() { return idCofrinho; }
    public void setIdCofrinho(Long idCofrinho) { this.idCofrinho = idCofrinho; }
    public String getNmCofrinho() { return nmCofrinho; }
    public void setNmCofrinho(String nmCofrinho) { this.nmCofrinho = nmCofrinho; }
    public String getDsCofrinho() { return dsCofrinho; }
    public void setDsCofrinho(String dsCofrinho) { this.dsCofrinho = dsCofrinho; }
    public BigDecimal getVlMeta() { return vlMeta; }
    public void setVlMeta(BigDecimal vlMeta) { this.vlMeta = vlMeta != null ? vlMeta : BigDecimal.ZERO; }
    public BigDecimal getVlAtual() { return vlAtual; }
    public void setVlAtual(BigDecimal vlAtual) { this.vlAtual = vlAtual != null ? vlAtual : BigDecimal.ZERO; }
    public String getDsIcone() { return dsIcone; }
    public void setDsIcone(String dsIcone) { this.dsIcone = dsIcone; }
    public String getDsCor() { return dsCor; }
    public void setDsCor(String dsCor) { this.dsCor = dsCor; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public String toString() {
        return String.format("Cofrinho [ID: %d | Nome: %s | Meta: %s | Atual: %s]",
                idCofrinho, nmCofrinho, vlMeta, vlAtual);
    }
}
