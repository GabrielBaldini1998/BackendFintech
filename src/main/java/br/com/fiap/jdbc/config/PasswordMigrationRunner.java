package br.com.fiap.jdbc.config;

import br.com.fiap.jdbc.model.Usuario;
import br.com.fiap.jdbc.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PasswordMigrationRunner.class);
    private static final Pattern BCRYPT_HASH = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$.{53}$");

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordMigrationRunner(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        int migrados = 0;
        for (Usuario usuario : usuarioRepository.findAll()) {
            String senhaAtual = usuario.getDsSenha();
            if (senhaAtual != null && !BCRYPT_HASH.matcher(senhaAtual).matches()) {
                usuario.setDsSenha(passwordEncoder.encode(senhaAtual));
                usuarioRepository.save(usuario);
                migrados++;
            }
        }
        if (migrados > 0) {
            log.info("Migração de senhas: {} usuário(s) tiveram a senha convertida para hash bcrypt.", migrados);
        }
    }
}
