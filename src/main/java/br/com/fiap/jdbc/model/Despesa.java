package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Despesa {
    private int idDespesa;
    private String tpDespesa;
    private double vlDespesa;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtDespesa;
    private String numeroDaConta;

    public Despesa() {}

    public Despesa(int idDespesa, String tpDespesa, double vlDespesa, Date dtDespesa, String numeroDaConta) {
        this.idDespesa = idDespesa;
        this.tpDespesa = tpDespesa;
        this.vlDespesa = vlDespesa;
        this.dtDespesa = dtDespesa;
        this.numeroDaConta = numeroDaConta;
    }

    public int getIdDespesa() { return idDespesa; }
    public void setIdDespesa(int idDespesa) { this.idDespesa = idDespesa; }
    public String getTpDespesa() { return tpDespesa; }
    public void setTpDespesa(String tpDespesa) { this.tpDespesa = tpDespesa; }
    public double getVlDespesa() { return vlDespesa; }
    public void setVlDespesa(double vlDespesa) { this.vlDespesa = vlDespesa; }
    public Date getDtDespesa() { return dtDespesa; }
    public void setDtDespesa(Date dtDespesa) { this.dtDespesa = dtDespesa; }
    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }

    @Override
    public String toString() {
        return String.format("Despesa [ID: %d | Tipo: %s | Valor: R$ %.2f | Data: %s | Conta FK: %s]",
                idDespesa, tpDespesa, vlDespesa, dtDespesa.toString(), numeroDaConta);
    }
}