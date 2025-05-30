package org.kata4.service;

import org.kata4.dao.PedidoDAO;
import org.kata4.model.Pedido;

public class PedidoService {

    private final PedidoDAO dao = new PedidoDAO();

    public void registrarPedido(Pedido pedido) {
        if (pedido.getItems().isEmpty()) {
            System.out.println("⚠ El pedido no tiene productos.");
            return;
        }

        try {
            pedido.recalcularTotal();
            dao.crearPedido(pedido);
        } catch (Exception e) {
            System.out.println("❌ Error al registrar el pedido.");
        }
    }
}
