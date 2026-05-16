package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class Usuario {
    private int idUsuario;
    private String nmCompleto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dtNascimento;
    private String nmCpfUsuario;
    private String dsEmail;
    private String dsSenha;

    public Usuario() {}

    public Usuario(int idUsuario, String nmCompleto, Date dtNascimento, String nmCpfUsuario, String dsEmail, String dsSenha) {
        this.idUsuario = idUsuario;
        this.nmCompleto = nmCompleto;
        this.dtNascimento = dtNascimento;
        this.nmCpfUsuario = nmCpfUsuario;
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNmCompleto() { return nmCompleto; }
    public void setNmCompleto(String nmCompleto) { this.nmCompleto = nmCompleto; }
    public Date getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(Date dtNascimento) { this.dtNascimento = dtNascimento; }
    public String getNmCpfUsuario() { return nmCpfUsuario; }
    public void setNmCpfUsuario(String nmCpfUsuario) { this.nmCpfUsuario = nmCpfUsuario; }
    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }
    public String getDsSenha() { return dsSenha; }
    public void setDsSenha(String dsSenha) { this.dsSenha = dsSenha; }

    @Override
    public String toString() {
        return String.format("Usuario [ID: %d | Nome: %s | CPF: %s | Email: %s]",
                idUsuario, nmCompleto, nmCpfUsuario, dsEmail);
    }
}
