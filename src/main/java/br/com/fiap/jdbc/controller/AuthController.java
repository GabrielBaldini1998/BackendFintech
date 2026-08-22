package br.com.fiap.jdbc.controller;

import br.com.fiap.jdbc.model.Usuario;
import br.com.fiap.jdbc.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Pattern BCRYPT_HASH = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$.{53}$");

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String senha = body.get("senha");

        return usuarioRepository.findByDsEmail(email)
                .filter(u -> senhaConfere(senha, u))
                .map(u -> ResponseEntity.ok((Object) u))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("erro", "Email ou senha inválidos")));
    }

    // Contas criadas antes do hashing de senha ainda têm dsSenha em texto puro no
    // banco; aceitamos comparação direta nesse caso legado enquanto migram a senha.
    private boolean senhaConfere(String senhaInformada, Usuario usuario) {
        String senhaArmazenada = usuario.getDsSenha();
        if (BCRYPT_HASH.matcher(senhaArmazenada).matches()) {
            return passwordEncoder.matches(senhaInformada, senhaArmazenada);
        }
        return senhaArmazenada.equals(senhaInformada);
    }
}
