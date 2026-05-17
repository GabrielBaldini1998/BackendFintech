package br.com.fiap.jdbc.service;

import br.com.fiap.jdbc.exception.ResourceNotFoundException;
import br.com.fiap.jdbc.model.Usuario;
import br.com.fiap.jdbc.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public Usuario salvar(Usuario usuario) {
        usuarioRepository.findByNmCpfUsuario(usuario.getNmCpfUsuario())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("CPF já cadastrado: " + usuario.getNmCpfUsuario());
                });
        usuarioRepository.findByDsEmail(usuario.getDsEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("E-mail já cadastrado: " + usuario.getDsEmail());
                });
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        buscarPorId(id);
        usuario.setIdUsuario(id);
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        usuarioRepository.deleteById(id);
    }
}
