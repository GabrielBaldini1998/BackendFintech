package br.com.fiap.jdbc.config;

import br.com.fiap.jdbc.model.Cofrinho;
import br.com.fiap.jdbc.model.Transacao;
import br.com.fiap.jdbc.model.Usuario;
import br.com.fiap.jdbc.repository.CofrinhoRepository;
import br.com.fiap.jdbc.repository.TransacaoRepository;
import br.com.fiap.jdbc.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Order(1)
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UsuarioRepository usuarioRepository;
    private final CofrinhoRepository cofrinhoRepository;
    private final TransacaoRepository transacaoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository,
                      CofrinhoRepository cofrinhoRepository,
                      TransacaoRepository transacaoRepository,
                      PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.cofrinhoRepository = cofrinhoRepository;
        this.transacaoRepository = transacaoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        try {
            if (usuarioRepository.findByDsEmail("admin@fincheck.com").isPresent()) {
                log.info("DataSeeder: dados já existem, seed ignorado.");
                return;
            }

            Usuario admin = new Usuario();
            admin.setNmCompleto("Admin FinCheck");
            admin.setNmDocumento("00000000000");
            admin.setTpTipo("CPF");
            admin.setDsEmail("admin@fincheck.com");
            admin.setDsSenha(passwordEncoder.encode("123456"));
            admin.setDtNascimento(LocalDate.parse("1990-01-01"));
            Usuario saved = usuarioRepository.save(admin);
            log.info("DataSeeder: usuário admin criado com ID {}", saved.getIdUsuario());

            Cofrinho cofViagem = new Cofrinho();
            cofViagem.setNmCofrinho("Viagem Europa");
            cofViagem.setDsCofrinho("Fundo para viagem à Europa em 2027");
            cofViagem.setVlMeta(new BigDecimal("15000.00"));
            cofViagem.setVlAtual(new BigDecimal("3200.00"));
            cofViagem.setDsIcone("Plane");
            cofViagem.setDsCor("#F59E0B");
            cofViagem.setIdUsuario(saved.getIdUsuario());
            cofrinhoRepository.save(cofViagem);

            Cofrinho cofEmerg = new Cofrinho();
            cofEmerg.setNmCofrinho("Reserva de Emergência");
            cofEmerg.setDsCofrinho("6 meses de despesas fixas");
            cofEmerg.setVlMeta(new BigDecimal("18000.00"));
            cofEmerg.setVlAtual(new BigDecimal("9500.00"));
            cofEmerg.setDsIcone("Shield");
            cofEmerg.setDsCor("#22C55E");
            cofEmerg.setIdUsuario(saved.getIdUsuario());
            cofrinhoRepository.save(cofEmerg);

            log.info("DataSeeder: 2 cofrinhos de exemplo criados.");

            Transacao t1 = new Transacao();
            t1.setTpTransacao("RECEITA");
            t1.setDsTransacao("Salário maio");
            t1.setVlTransacao(new BigDecimal("5000.00"));
            t1.setDtTransacao(LocalDate.parse("2026-05-05"));
            t1.setCategoria("Salário");
            t1.setIdUsuario(saved.getIdUsuario());
            transacaoRepository.save(t1);

            Transacao t2 = new Transacao();
            t2.setTpTransacao("DESPESA");
            t2.setDsTransacao("Aluguel");
            t2.setVlTransacao(new BigDecimal("1500.00"));
            t2.setDtTransacao(LocalDate.parse("2026-05-10"));
            t2.setCategoria("Moradia");
            t2.setIdUsuario(saved.getIdUsuario());
            transacaoRepository.save(t2);

            Transacao t3 = new Transacao();
            t3.setTpTransacao("DESPESA");
            t3.setDsTransacao("Supermercado");
            t3.setVlTransacao(new BigDecimal("620.00"));
            t3.setDtTransacao(LocalDate.parse("2026-05-12"));
            t3.setCategoria("Alimentação");
            t3.setIdUsuario(saved.getIdUsuario());
            transacaoRepository.save(t3);

            log.info("DataSeeder: 3 transações de exemplo criadas.");

        } catch (Exception e) {
            log.warn("DataSeeder: seed ignorado — {}", e.getMessage());
        }
    }
}
