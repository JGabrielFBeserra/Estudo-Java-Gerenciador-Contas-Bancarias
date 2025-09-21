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

public class GerenciadorBancoDAO {

    public List<ContaCorrente> listarContas() throws SQLException {
        String sql = "SELECT numero, titular, saldo FROM contas ORDER BY numero";
        try (Connection conn = ConexaoDAO.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<ContaCorrente> contas = new ArrayList<>();
            while (rs.next()) {
                int numero = rs.getInt("numero");
                String titular = rs.getString("titular");
                double saldo = rs.getBigDecimal("saldo").doubleValue(); // DECIMAL -> double
                contas.add(new ContaCorrente(numero, titular, saldo));
            }
            return contas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar as contas do Banco de dados");
        }
    }

    public boolean depositar(int id, double valor) throws java.sql.SQLException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser > 0");
        }
        final String sql = "UPDATE contas SET saldo = saldo + ? WHERE numero = ?";
        try (var con = ConexaoDAO.getConnection(); var ps = con.prepareStatement(sql)) {
            ps.setBigDecimal(1, java.math.BigDecimal.valueOf(valor));
            ps.setInt(2, id);
            return ps.executeUpdate() == 1; // true = conta existia e foi atualizada
        }
    }

    public boolean sacar(int id, double valor) throws java.sql.SQLException {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser > 0");
        }
        final String sql = "UPDATE contas SET saldo = saldo - ? WHERE numero = ? AND saldo >= ?";
        try (var con = ConexaoDAO.getConnection(); var ps = con.prepareStatement(sql)) {
            var v = java.math.BigDecimal.valueOf(valor);
            ps.setBigDecimal(1, v);
            ps.setInt(2, id);
            ps.setBigDecimal(3, v);
            return ps.executeUpdate() == 1; // true = havia saldo suficiente e a conta existia
        }
    }

    public boolean existeId(int numero) throws SQLException {
        String sql = "SELECT 1 FROM contas WHERE numero = ?";
        try (Connection con = ConexaoDAO.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void inserirConta(ContaCorrente c) throws SQLException {
        String sql = "INSERT INTO contas (numero, titular, saldo) VALUES (?, ?, ?)";
        try (Connection con = ConexaoDAO.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getId());                 // se o seu getter for getNumero(), ajuste aqui
            ps.setString(2, c.getTitular());
            ps.setBigDecimal(3, BigDecimal.valueOf(c.getSaldo()));
            ps.executeUpdate();
        }
    }

    public ContaCorrente buscarPorNumero(int numero) throws SQLException {
        String sql = "SELECT numero, titular, saldo FROM contas WHERE numero = ?";
        try (var con = ConexaoDAO.getConnection(); var ps = con.prepareStatement(sql)) {
            ps.setInt(1, numero);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ContaCorrente(
                            rs.getInt("numero"),
                            rs.getString("titular"),
                            rs.getDouble("saldo"));
                }
                return null;
            }
        }

    }

    public void atualizarTitular(int numero, String novoTitular) throws SQLException {
        String sql = "UPDATE contas SET titular = ? WHERE numero = ?";
        try (var con = ConexaoDAO.getConnection(); var ps = con.prepareStatement(sql)) {
            ps.setString(1, novoTitular);
            ps.setInt(2, numero);
            int n = ps.executeUpdate();
            if (n == 0) {
                throw new SQLException("Conta não encontrada.");
            }
        }
    }

    public void transferir(int numeroOrigem, int numeroDestino, double valor)
            throws SQLException, br.com.banco.exceptions.SaldoInsuficienteException {

        if (numeroOrigem == numeroDestino) {
            throw new IllegalArgumentException("Contas de origem e destino não podem ser iguais.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser > 0.");
        }

        try (Connection con = ConexaoDAO.getConnection()) {
            // Início da transação
            con.setAutoCommit(false);

            try {
                // 1) Verifica e "trava" a conta de origem (linha) até o commit/rollback
                double saldoOrigem;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT saldo FROM contas WHERE numero = ? FOR UPDATE")) {
                    ps.setInt(1, numeroOrigem);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Conta de origem não encontrada.");
                        }
                        saldoOrigem = rs.getDouble(1);
                    }
                }

                // 2) Verifica e "trava" a conta de destino
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT numero FROM contas WHERE numero = ? FOR UPDATE")) {
                    ps.setInt(1, numeroDestino);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Conta de destino não encontrada.");
                        }
                    }
                }

                // 3) Debita origem garantindo que não fique negativo (condicional)
                int debited;
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE contas SET saldo = saldo - ? "
                        + "WHERE numero = ? AND saldo >= ?")) {
                    ps.setDouble(1, valor);
                    ps.setInt(2, numeroOrigem);
                    ps.setDouble(3, valor);
                    debited = ps.executeUpdate();
                }
                if (debited == 0) {
                    // Falhou por saldo insuficiente
                    throw new br.com.banco.exceptions.SaldoInsuficienteException(
                            "Saldo insuficiente para transferência.");
                }

                // 4) Credita destino
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE contas SET saldo = saldo + ? WHERE numero = ?")) {
                    ps.setDouble(1, valor);
                    ps.setInt(2, numeroDestino);
                    int credited = ps.executeUpdate();
                    if (credited == 0) {
                        // Algo muito improvável (acabou de ser verificado), mas por segurança:
                        throw new SQLException("Falha ao creditar conta de destino.");
                    }
                }

                // 5) Conclui a transação
                con.commit();

            } catch (Exception ex) {
                // Reverte tudo na falha
                try {
                    con.rollback();
                } catch (SQLException ignore) {
                }
                // Propaga mantendo o tipo (saldo insuficiente ou SQL)
                if (ex instanceof br.com.banco.exceptions.SaldoInsuficienteException sie) {
                    throw sie;
                }
                if (ex instanceof SQLException se) {
                    throw se;
                }
                throw new SQLException("Erro na transferência.", ex);
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException ignore) {
                }
            }
        }
    }

}
