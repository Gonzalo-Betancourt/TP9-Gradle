package org.kata2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/tp_9";
    private static final String USER = "root";
    private static final String PASS = "admin";

    public static Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error al conectar con la base de datos", e);
        }
    }
}
