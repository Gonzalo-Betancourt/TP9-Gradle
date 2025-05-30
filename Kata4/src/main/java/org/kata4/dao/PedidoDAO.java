package org.kata4.dao;

import org.kata4.config.ConexionDB;
import org.kata4.model.Producto;
import org.kata4.model.ItemPedido;
import org.kata4.model.Pedido;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PedidoDAO {

    private final Connection conexion;

    public PedidoDAO() {
        this.conexion = ConexionDB.obtenerConexion();
    }

    public void crearPedido(Pedido pedido) throws SQLException {
        String sqlPedido = "INSERT INTO pedidos (fecha, total) VALUES (?, ?)";
        String sqlItem = "INSERT INTO items_pedido (pedido_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET cantidad = cantidad - ? WHERE id = ?";

        try {
            conexion.setAutoCommit(false);

            // Insertar pedido
            PreparedStatement stmtPedido = conexion.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setDate(1, Date.valueOf(pedido.getFecha()));
            stmtPedido.setDouble(2, pedido.getTotal());
            stmtPedido.executeUpdate();

            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (!rs.next()) throw new SQLException("No se pudo obtener el ID del pedido.");
            int idPedido = rs.getInt(1);

            for (ItemPedido item : pedido.getItems()) {
                Producto prod = item.getProducto();

                // Validaciones críticas
                if (item.getCantidad() <= 0) throw new SQLException("Cantidad inválida para producto: " + prod.getNombre());
                if (item.getCantidad() > prod.getCantidad()) throw new SQLException("Stock insuficiente para: " + prod.getNombre());

                // Insertar ítem
                PreparedStatement stmtItem = conexion.prepareStatement(sqlItem);
                stmtItem.setInt(1, idPedido);
                stmtItem.setInt(2, prod.getId());
                stmtItem.setInt(3, item.getCantidad());
                stmtItem.setDouble(4, item.getSubtotal());
                stmtItem.executeUpdate();

                // Descontar stock
                PreparedStatement stmtStock = conexion.prepareStatement(sqlStock);
                stmtStock.setInt(1, item.getCantidad());
                stmtStock.setInt(2, prod.getId());
                stmtStock.executeUpdate();
            }

            conexion.commit();
            System.out.println("✅ Pedido registrado correctamente.");
        } catch (SQLException e) {
            conexion.rollback();
            System.out.println("❌ Pedido cancelado. Motivo: " + e.getMessage());
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }
    }
}
