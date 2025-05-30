package org.kata1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/tp_9";
        String user = "root";
        String pass = "admin";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            CategoriaDAO gestor = new CategoriaDAO(conn);

            gestor.agregarCategoria("Librería", "Útiles escolares y oficina");

            List<Categoria> categorias = gestor.listarCategorias();
            System.out.println("📋 Categorías:");
            for (Categoria c : categorias) {
                System.out.println("- " + c.getNombre() + ": " + c.getDescripcion());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
