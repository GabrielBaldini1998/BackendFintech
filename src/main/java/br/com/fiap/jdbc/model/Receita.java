package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Receita {
    private int idReceita;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtReceita;
    private double vlRecebido;
    private String dsReceita;
    private String numeroDaConta;

    public Receita() {}

    public Receita(int idReceita, Date dtReceita, double vlRecebido, String dsReceita, String numeroDaConta) {
        this.idReceita = idReceita;
        this.dtReceita = dtReceita;
        this.vlRecebido = vlRecebido;
        this.dsReceita = dsReceita;
        this.numeroDaConta = numeroDaConta;
    }

    public int getIdReceita() { return idReceita; }
    public void setIdReceita(int idReceita) { this.idReceita = idReceita; }
    public Date getDtReceita() { return dtReceita; }
    public void setDtReceita(Date dtReceita) { this.dtReceita = dtReceita; }
    public double getVlRecebido() { return vlRecebido; }
    public void setVlRecebido(double vlRecebido) { this.vlRecebido = vlRecebido; }
    public String getDsReceita() { return dsReceita; }
    public void setDsReceita(String dsReceita) { this.dsReceita = dsReceita; }
    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }

    @Override
    public String toString() {
        return String.format("Receita [ID: %d | Data: %s | Valor: R$ %.2f | Descricao: %s | Conta FK: %s]",
                idReceita, dtReceita, vlRecebido, dsReceita, numeroDaConta);
    }
}
