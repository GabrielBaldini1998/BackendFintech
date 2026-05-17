package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "T_FTC_CONTA")
public class Conta {

    @Id
    @Column(name = "numero_da_conta", length = 20)
    private String numeroDaConta;

    @NotNull
    @Column(name = "titular", length = 100, nullable = false)
    private String titular;

    @NotNull
    @Column(name = "agencia", length = 10, nullable = false)
    private String agencia;

    @NotNull
    @Column(name = "tipo", length = 20, nullable = false)
    private String tipo;

    @Column(name = "saldo")
    private double saldo;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    @JsonIgnore
    private Usuario usuario;

    public Conta() {}

    public Conta(String numeroDaConta, String titular, String agencia, String tipo, double saldo, Long idUsuario) {
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
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    @Override
    public String toString() {
        return String.format("Conta [Numero: %s | Titular: %s | Agencia: %s | Tipo: %s | Saldo: R$ %.2f]",
                numeroDaConta, titular, agencia, tipo, saldo);
    }
}
