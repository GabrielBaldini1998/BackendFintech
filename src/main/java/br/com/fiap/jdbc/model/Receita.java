package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.sql.Date;

@Entity
@Table(name = "T_FTC_RECEITA")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_receita")
    @SequenceGenerator(name = "seq_receita", sequenceName = "SEQ_RECEITA", allocationSize = 1)
    @Column(name = "id_receita")
    private Long idReceita;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_receita")
    private Date dtReceita;

    @Positive
    @Column(name = "vl_recebido", nullable = false)
    private double vlRecebido;

    @NotNull
    @Column(name = "ds_receita", length = 200, nullable = false)
    private String dsReceita;

    @Column(name = "numero_da_conta", length = 20, nullable = false)
    private String numeroDaConta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_da_conta", insertable = false, updatable = false)
    @JsonIgnore
    private Conta conta;

    public Receita() {}

    public Receita(Long idReceita, Date dtReceita, double vlRecebido, String dsReceita, String numeroDaConta) {
        this.idReceita = idReceita;
        this.dtReceita = dtReceita;
        this.vlRecebido = vlRecebido;
        this.dsReceita = dsReceita;
        this.numeroDaConta = numeroDaConta;
    }

    public Long getIdReceita() { return idReceita; }
    public void setIdReceita(Long idReceita) { this.idReceita = idReceita; }
    public Date getDtReceita() { return dtReceita; }
    public void setDtReceita(Date dtReceita) { this.dtReceita = dtReceita; }
    public double getVlRecebido() { return vlRecebido; }
    public void setVlRecebido(double vlRecebido) { this.vlRecebido = vlRecebido; }
    public String getDsReceita() { return dsReceita; }
    public void setDsReceita(String dsReceita) { this.dsReceita = dsReceita; }
    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    @Override
    public String toString() {
        return String.format("Receita [ID: %d | Data: %s | Valor: R$ %.2f | Descricao: %s | Conta FK: %s]",
                idReceita, dtReceita, vlRecebido, dsReceita, numeroDaConta);
    }
}
