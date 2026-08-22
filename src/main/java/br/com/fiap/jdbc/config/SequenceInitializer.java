package br.com.fiap.jdbc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
@Order(2)
public class SequenceInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SequenceInitializer.class);

    private final DataSource dataSource;

    public SequenceInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        syncSequence("SEQ_USUARIO",    "T_FTC_USUARIO",    "id_usuario");
        syncSequence("SEQ_TRANSACAO",  "T_FTC_TRANSACAO",  "id_transacao");
        syncSequence("SEQ_COFRINHO",   "T_FTC_COFRINHO",   "id_cofrinho");
    }

    private void syncSequence(String seqName, String tableName, String idCol) {
        try (Connection conn = dataSource.getConnection()) {
            long maxId = 0;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(
                         "SELECT NVL(MAX(" + idCol + "), 0) FROM " + tableName)) {
                if (rs.next()) maxId = rs.getLong(1);
            }

            if (maxId == 0) return;

            long curVal;
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT " + seqName + ".NEXTVAL FROM DUAL")) {
                rs.next();
                curVal = rs.getLong(1);
            }

            if (curVal <= maxId) {
                long jump = maxId - curVal + 1;
                try (Statement st = conn.createStatement()) {
                    st.execute("ALTER SEQUENCE " + seqName + " INCREMENT BY " + jump);
                }
                try (Statement st = conn.createStatement();
                     ResultSet rs = st.executeQuery("SELECT " + seqName + ".NEXTVAL FROM DUAL")) {
                    rs.next();
                }
                try (Statement st = conn.createStatement()) {
                    st.execute("ALTER SEQUENCE " + seqName + " INCREMENT BY 1");
                }
                log.info("Sequence {} synced past max ID {}", seqName, maxId);
            }
        } catch (Exception e) {
            log.warn("Could not sync sequence {}: {}", seqName, e.getMessage());
        }
    }
}
