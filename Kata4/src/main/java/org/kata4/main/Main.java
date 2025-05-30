package org.kata4.main;

import org.kata4.model.Categoria;
import org.kata4.model.Producto;
import org.kata4.service.CategoriaService;
import org.kata4.service.ProductoService;
import org.kata4.model.ItemPedido;
import org.kata4.model.Pedido;
import org.kata4.service.PedidoService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService();
        PedidoService pedidoService = new PedidoService();

        Categoria categoria = categoriaService.leer(1);
        if (categoria == null) {
            categoriaService.crear(new Categoria("Gaming", "Tecnología gamer"));
            categoria = categoriaService.leer(1);
        }

        // Crear productos si no existen
        List<Producto> productos = productoService.listar();
        if (productos.isEmpty()) {
            productoService.crear(new Producto("Teclado RGB", "Mecánico", 100.0, 10, categoria));
            productoService.crear(new Producto("Mouse Gamer", "8000 DPI", 60.0, 20, categoria));
            productos = productoService.listar();
        }

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.agregarItem(new ItemPedido(productos.get(0), 2));
        pedido.agregarItem(new ItemPedido(productos.get(1), 1));

        // Registrar
        pedidoService.registrarPedido(pedido);
    }
}
