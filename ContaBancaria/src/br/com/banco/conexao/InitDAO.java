package br.com.banco.conexao;

import br.com.banco.conexao.ConexaoDAO;
import br.com.banco.model.ContaCorrente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InitDAO {

    // cria o banco e a tabela se não existirem
    public void inicializarBanco() throws SQLException {
        try (Connection con = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/?user=root&password=aluno&useSSL=false&serverTimezone=UTC");
             Statement st = con.createStatement()) {

            st.executeUpdate(
                "CREATE DATABASE IF NOT EXISTS banco_digital " +
                "DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_0900_ai_ci"
            );
        }

        try (Connection con = ConexaoDAO.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS contas (" +
                "  numero INT NOT NULL," +
                "  titular VARCHAR(100) NOT NULL," +
                "  saldo DECIMAL(10,2) NOT NULL DEFAULT 0.00," +
                "  CONSTRAINT pk_contas PRIMARY KEY (numero)," +
                "  CONSTRAINT ck_saldo_nao_negativo CHECK (saldo >= 0)" +
                ");"
            );
        }
    }

    // qaundo a tabela existir ele checa se tem contas
    public boolean tabelaVazia() throws SQLException {
        try (Connection con = ConexaoDAO.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM contas"); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1) == 0;
        }
    }

    // caso não tenha, ela popula com o txt
    public void importarTxtSeTabelaVazia(String path) throws SQLException, IOException {
        if (!tabelaVazia()) {
            return;
        }

        final String sql = """
        INSERT INTO contas (numero, titular, saldo) VALUES (?,?,?)
        ON DUPLICATE KEY UPDATE titular = VALUES(titular), saldo = VALUES(saldo)
        """;

        try (Connection con = ConexaoDAO.getConnection(); PreparedStatement ps = con.prepareStatement(sql); Stream<String> lines = Files.lines(Path.of(path))) {

            lines.map(String::trim)
                    .filter(l -> !l.isEmpty())
                    .map(l -> l.split(","))
                    .filter(p -> p.length == 3)
                    .map(p -> new br.com.banco.model.ContaCorrente(
                    Integer.parseInt(p[0].trim()),
                    p[1].trim(),
                    Double.parseDouble(p[2].trim())
            ))
                    .forEach(c -> {
                        try {
                            ps.setInt(1, c.getId());
                            ps.setString(2, c.getTitular());
                            ps.setBigDecimal(3, BigDecimal.valueOf(c.getSaldo()));
                            ps.addBatch();
                        } catch (SQLException e) {
                            // encapsula a checked exception para poder usar o for
                            throw new RuntimeException(e);
                        }
                    });

            ps.executeBatch();
        }
    }
}
