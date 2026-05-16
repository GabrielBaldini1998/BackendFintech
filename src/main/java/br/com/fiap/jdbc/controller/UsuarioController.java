package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.dao.UsuarioDAO;
import br.com.fiap.jdbc.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioDAO.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscar(@PathVariable int id) {
        return usuarioDAO.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@RequestBody Usuario usuario) {
        usuarioDAO.insert(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody Usuario usuario) {
        if (usuarioDAO.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        usuario.setIdUsuario(id);
        usuarioDAO.update(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (usuarioDAO.getById(id).isEmpty()) return ResponseEntity.notFound().build();
        usuarioDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
