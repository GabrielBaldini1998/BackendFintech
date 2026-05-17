package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.sql.Date;

@Entity
@Table(name = "T_FTC_INVESTIMENTO")
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_investimento")
    @SequenceGenerator(name = "seq_investimento", sequenceName = "SEQ_INVESTIMENTO", allocationSize = 1)
    @Column(name = "id_investimento")
    private Long idInvestimento;

    @NotNull
    @Column(name = "nm_aplicacao", length = 100, nullable = false)
    private String nmAplicacao;

    @NotNull
    @Column(name = "nm_banco_corretora", length = 100, nullable = false)
    private String nmBancoCorretora;

    @Positive
    @Column(name = "vl_aplicacao", nullable = false)
    private double vlAplicacao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_aplicacao")
    private Date dtAplicacao;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_vencimento_aplicacao")
    private Date dtVencimentoAplicacao;

    @Column(name = "numero_da_conta", length = 20, nullable = false)
    private String numeroDaConta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_da_conta", insertable = false, updatable = false)
    @JsonIgnore
    private Conta conta;

    public Investimento() {}

    public Investimento(Long idInvestimento, String nmAplicacao, String nmBancoCorretora, double vlAplicacao,
                        Date dtAplicacao, Date dtVencimentoAplicacao, String numeroDaConta) {
        this.idInvestimento = idInvestimento;
        this.nmAplicacao = nmAplicacao;
        this.nmBancoCorretora = nmBancoCorretora;
        this.vlAplicacao = vlAplicacao;
        this.dtAplicacao = dtAplicacao;
        this.dtVencimentoAplicacao = dtVencimentoAplicacao;
        this.numeroDaConta = numeroDaConta;
    }

    public Long getIdInvestimento() { return idInvestimento; }
    public void setIdInvestimento(Long idInvestimento) { this.idInvestimento = idInvestimento; }
    public String getNmAplicacao() { return nmAplicacao; }
    public void setNmAplicacao(String nmAplicacao) { this.nmAplicacao = nmAplicacao; }
    public String getNmBancoCorretora() { return nmBancoCorretora; }
    public void setNmBancoCorretora(String nmBancoCorretora) { this.nmBancoCorretora = nmBancoCorretora; }
    public double getVlAplicacao() { return vlAplicacao; }
    public void setVlAplicacao(double vlAplicacao) { this.vlAplicacao = vlAplicacao; }
    public Date getDtAplicacao() { return dtAplicacao; }
    public void setDtAplicacao(Date dtAplicacao) { this.dtAplicacao = dtAplicacao; }
    public Date getDtVencimentoAplicacao() { return dtVencimentoAplicacao; }
    public void setDtVencimentoAplicacao(Date dtVencimentoAplicacao) { this.dtVencimentoAplicacao = dtVencimentoAplicacao; }
    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    @Override
    public String toString() {
        return String.format("Investimento [ID: %d | Aplicacao: %s | Banco/Corretora: %s | Valor: R$ %.2f | Vencimento: %s]",
                idInvestimento, nmAplicacao, nmBancoCorretora, vlAplicacao, dtVencimentoAplicacao);
    }
}
