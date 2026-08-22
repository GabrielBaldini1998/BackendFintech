package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Usuario;
import br.com.fiap.jdbc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private static final Pattern BCRYPT_HASH = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$.{53}$");

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getDsSenha() == null || usuario.getDsSenha().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
        usuarioRepository.findByNmDocumento(usuario.getNmDocumento())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Documento já cadastrado: " + usuario.getNmDocumento());
                });
        usuarioRepository.findByDsEmail(usuario.getDsEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("E-mail já cadastrado: " + usuario.getDsEmail());
                });
        hashearSenhaSeNecessario(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        usuario.setIdUsuario(id);
        // dsSenha não volta nas respostas da API (write-only); se o cliente não
        // mandar uma nova senha, mantém o hash já salvo em vez de apagá-lo.
        if (usuario.getDsSenha() == null || usuario.getDsSenha().isBlank()) {
            usuario.setDsSenha(existente.getDsSenha());
        }
        hashearSenhaSeNecessario(usuario);
        return usuarioRepository.save(usuario);
    }

    private void hashearSenhaSeNecessario(Usuario usuario) {
        String senha = usuario.getDsSenha();
        if (senha != null && !BCRYPT_HASH.matcher(senha).matches()) {
            usuario.setDsSenha(passwordEncoder.encode(senha));
        }
    }

    public void deletar(Long id) {
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }
}
