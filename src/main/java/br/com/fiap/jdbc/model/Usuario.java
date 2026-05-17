package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "T_FTC_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotNull
    @Column(name = "nm_completo", length = 100, nullable = false)
    private String nmCompleto;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dt_nascimento")
    private Date dtNascimento;

    @NotNull
    @Column(name = "nm_cpf_usuario", length = 11, unique = true, nullable = false)
    private String nmCpfUsuario;

    @NotNull
    @Column(name = "ds_email", length = 100, nullable = false)
    private String dsEmail;

    @NotNull
    @Column(name = "ds_senha", length = 100, nullable = false)
    private String dsSenha;

    public Usuario() {}

    public Usuario(Long idUsuario, String nmCompleto, Date dtNascimento, String nmCpfUsuario, String dsEmail, String dsSenha) {
        this.idUsuario = idUsuario;
        this.nmCompleto = nmCompleto;
        this.dtNascimento = dtNascimento;
        this.nmCpfUsuario = nmCpfUsuario;
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
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
