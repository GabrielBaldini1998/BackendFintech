package br.com.fiap.jdbc.model;

public class Conta {
    private String numeroDaConta;
    private String titular;
    private String agencia;
    private String tipo;
    private double saldo;
    private int idUsuario;

    public Conta() {}

    public Conta(String numeroDaConta, String titular, String agencia, String tipo, double saldo, int idUsuario) {
        this.numeroDaConta = numeroDaConta;
        this.titular = titular;
        this.agencia = agencia;
        this.tipo = tipo;
        this.saldo = saldo;
        this.idUsuario = idUsuario;
    }

    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public String toString() {
        return String.format("Conta [Numero: %s | Titular: %s | Agencia: %s | Tipo: %s | Saldo: R$ %.2f]",
                numeroDaConta, titular, agencia, tipo, saldo);
    }
}
