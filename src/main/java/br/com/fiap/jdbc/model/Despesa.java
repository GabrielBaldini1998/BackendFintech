package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.sql.Date;

@Entity
@Table(name = "T_FTC_DESPESA")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_despesa")
    @SequenceGenerator(name = "seq_despesa", sequenceName = "SEQ_DESPESA", allocationSize = 1)
    @Column(name = "id_despesa")
    private Long idDespesa;

    @NotNull
    @Column(name = "tp_despesa", length = 50, nullable = false)
    private String tpDespesa;

    @Positive
    @Column(name = "vl_despesa", nullable = false)
    private double vlDespesa;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_despesa")
    private Date dtDespesa;

    @Column(name = "numero_da_conta", length = 20, nullable = false)
    private String numeroDaConta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_da_conta", insertable = false, updatable = false)
    @JsonIgnore
    private Conta conta;

    public Despesa() {}

    public Despesa(Long idDespesa, String tpDespesa, double vlDespesa, Date dtDespesa, String numeroDaConta) {
        this.idDespesa = idDespesa;
        this.tpDespesa = tpDespesa;
        this.vlDespesa = vlDespesa;
        this.dtDespesa = dtDespesa;
        this.numeroDaConta = numeroDaConta;
    }

    public Long getIdDespesa() { return idDespesa; }
    public void setIdDespesa(Long idDespesa) { this.idDespesa = idDespesa; }
    public String getTpDespesa() { return tpDespesa; }
    public void setTpDespesa(String tpDespesa) { this.tpDespesa = tpDespesa; }
    public double getVlDespesa() { return vlDespesa; }
    public void setVlDespesa(double vlDespesa) { this.vlDespesa = vlDespesa; }
    public Date getDtDespesa() { return dtDespesa; }
    public void setDtDespesa(Date dtDespesa) { this.dtDespesa = dtDespesa; }
    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    @Override
    public String toString() {
        return String.format("Despesa [ID: %d | Tipo: %s | Valor: R$ %.2f | Data: %s | Conta FK: %s]",
                idDespesa, tpDespesa, vlDespesa, dtDespesa != null ? dtDespesa.toString() : "N/A", numeroDaConta);
    }
}
