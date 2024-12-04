package com.contenedores;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContenedorDAO {
    private final String url = "jdbc:sqlite:contenedores.db";

    public ContenedorDAO() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS contenedores (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "volumen REAL NOT NULL," +
                         "altura REAL NOT NULL," +
                         "radio REAL NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarContenedor(ContenedorCilindrico contenedor) {
        String sql = "INSERT INTO contenedores (volumen, altura, radio) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, contenedor.getVolumen());
            pstmt.setDouble(2, contenedor.getAltura());
            pstmt.setDouble(3, contenedor.getRadio());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ContenedorCilindrico> obtenerTodos() {
        List<ContenedorCilindrico> contenedores = new ArrayList<>();
        String sql = "SELECT * FROM contenedores";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contenedores.add(new ContenedorCilindrico(
                        rs.getInt("id"),
                        rs.getDouble("volumen"),
                        rs.getDouble("altura"),
                        rs.getDouble("radio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contenedores;
    }

    public void modificarContenedor(int id, double nuevoVolumen) {
        String sql = "UPDATE contenedores SET volumen = ?, altura = ?, radio = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            double radio = Math.sqrt(nuevoVolumen / (Math.PI * 10));
            double altura = nuevoVolumen / (Math.PI * radio * radio);
            pstmt.setDouble(1, nuevoVolumen);
            pstmt.setDouble(2, altura);
            pstmt.setDouble(3, radio);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarContenedor(int id) {
        String sql = "DELETE FROM contenedores WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
