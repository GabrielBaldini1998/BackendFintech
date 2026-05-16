package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Investimento {
    private int idInvestimento;
    private String nmAplicacao;
    private String nmBancoCorretora;
    private double vlAplicacao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtAplicacao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtVencimentoAplicacao;
    private String numeroDaConta;

    public Investimento() {}

    public Investimento(int idInvestimento, String nmAplicacao, String nmBancoCorretora, double vlAplicacao,
                        Date dtAplicacao, Date dtVencimentoAplicacao, String numeroDaConta) {
        this.idInvestimento = idInvestimento;
        this.nmAplicacao = nmAplicacao;
        this.nmBancoCorretora = nmBancoCorretora;
        this.vlAplicacao = vlAplicacao;
        this.dtAplicacao = dtAplicacao;
        this.dtVencimentoAplicacao = dtVencimentoAplicacao;
        this.numeroDaConta = numeroDaConta;
    }

    public int getIdInvestimento() { return idInvestimento; }
    public void setIdInvestimento(int idInvestimento) { this.idInvestimento = idInvestimento; }
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

    @Override
    public String toString() {
        return String.format("Investimento [ID: %d | Aplicacao: %s | Banco/Corretora: %s | Valor: R$ %.2f | Vencimento: %s]",
                idInvestimento, nmAplicacao, nmBancoCorretora, vlAplicacao, dtVencimentoAplicacao);
    }
}
