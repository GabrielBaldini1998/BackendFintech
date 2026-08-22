package br.com.fiap.jdbc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @Column(name = "dt_nascimento")
    private LocalDate dtNascimento;

    @NotNull
    @Column(name = "nm_documento", length = 18, unique = true, nullable = false)
    private String nmDocumento;

    @NotNull
    @Column(name = "tp_tipo", length = 5, nullable = false)
    private String tpTipo = "CPF";

    @NotNull
    @Column(name = "ds_email", length = 100, nullable = false)
    private String dsEmail;

    @Column(name = "ds_senha", length = 100, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String dsSenha;

    public Usuario() {}

    public Usuario(Long idUsuario, String nmCompleto, LocalDate dtNascimento, String nmDocumento, String tpTipo, String dsEmail, String dsSenha) {
        this.idUsuario = idUsuario;
        this.nmCompleto = nmCompleto;
        this.dtNascimento = dtNascimento;
        this.nmDocumento = nmDocumento;
        this.tpTipo = tpTipo != null ? tpTipo : "CPF";
        this.dsEmail = dsEmail;
        this.dsSenha = dsSenha;
    }

    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNmCompleto() { return nmCompleto; }
    public void setNmCompleto(String nmCompleto) { this.nmCompleto = nmCompleto; }
    public LocalDate getDtNascimento() { return dtNascimento; }
    public void setDtNascimento(LocalDate dtNascimento) { this.dtNascimento = dtNascimento; }
    public String getNmDocumento() { return nmDocumento; }
    public void setNmDocumento(String nmDocumento) { this.nmDocumento = nmDocumento; }
    public String getTpTipo() { return tpTipo; }
    public void setTpTipo(String tpTipo) { this.tpTipo = tpTipo != null ? tpTipo : "CPF"; }
    public String getDsEmail() { return dsEmail; }
    public void setDsEmail(String dsEmail) { this.dsEmail = dsEmail; }
    public String getDsSenha() { return dsSenha; }
    public void setDsSenha(String dsSenha) { this.dsSenha = dsSenha; }

    @Override
    public String toString() {
        return String.format("Usuario [ID: %d | Nome: %s | %s: %s | Email: %s]",
                idUsuario, nmCompleto, tpTipo, nmDocumento, dsEmail);
    }
}
