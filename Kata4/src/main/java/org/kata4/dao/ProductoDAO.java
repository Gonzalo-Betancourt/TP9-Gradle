package org.kata4.dao;

import org.kata4.config.ConexionDB;
import org.kata4.model.Categoria;
import org.kata4.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final Connection conexion;

    public ProductoDAO() {
        this.conexion = ConexionDB.obtenerConexion();
    }

    public void crear(Producto p) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, cantidad, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getCantidad());
            stmt.setInt(5, p.getCategoria().getId());
            stmt.executeUpdate();
            System.out.println("✅ Producto insertado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS nombre_categoria, c.descripcion AS descripcion_categoria " +
                "FROM productos p JOIN categorias c ON p.id_categoria = c.id";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id_categoria"), rs.getString("nombre_categoria"), rs.getString("descripcion_categoria"));
                Producto p = new Producto(
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
                        rs.getDouble("precio"), rs.getInt("cantidad"), cat
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public List<Producto> listarPorCategoria(int idCategoria) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre AS nombre_categoria, c.descripcion AS descripcion_categoria " +
                "FROM productos p JOIN categorias c ON p.id_categoria = c.id WHERE c.id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id_categoria"), rs.getString("nombre_categoria"), rs.getString("descripcion_categoria"));
                Producto p = new Producto(
                        rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
                        rs.getDouble("precio"), rs.getInt("cantidad"), cat
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    public boolean existeCategoria(int idCategoria) {
        String sql = "SELECT COUNT(*) FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}