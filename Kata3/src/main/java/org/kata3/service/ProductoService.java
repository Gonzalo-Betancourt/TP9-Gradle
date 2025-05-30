package org.kata3.service;

import org.kata3.dao.ProductoDAO;
import org.kata3.model.Producto;

import java.util.List;

public class ProductoService {

    private final ProductoDAO dao = new ProductoDAO();

    public void crear(Producto p) {
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            System.out.println("⚠ Nombre obligatorio.");
            return;
        }

        if (p.getPrecio() <= 0) {
            System.out.println("⚠ Precio debe ser mayor a cero.");
            return;
        }

        if (p.getCantidad() <= 0) {
            System.out.println("⚠ Cantidad debe ser mayor a cero.");
            return;
        }

        if (!dao.existeCategoria(p.getCategoria().getId())) {
            System.out.println("⚠ La categoría asignada no existe.");
            return;
        }

        dao.crear(p);
    }

    public List<Producto> listar() {
        return dao.listar();
    }

    public List<Producto> listarPorCategoria(int idCategoria) {
        return dao.listarPorCategoria(idCategoria);
    }
}
