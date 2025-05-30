package org.kata3.main;

import org.kata3.model.Categoria;
import org.kata3.model.Producto;
import org.kata3.service.CategoriaService;
import org.kata3.service.ProductoService;

public class Main {
    public static void main(String[] args) {
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService();

        Categoria categoria = categoriaService.leer(1);
        if (categoria == null) {
            categoriaService.crear(new Categoria("Tecnología", "Electrónica y gadgets"));
            categoria = categoriaService.leer(1);
        }

        Producto producto = new Producto("Notebook", "Intel i5", 750.0, 5, categoria);
        productoService.crear(producto);

        System.out.println("📋 Productos:");
        productoService.listar().forEach(p ->
                System.out.println("- " + p.getNombre() + " | $" + p.getPrecio() + " | Categoría: " + p.getCategoria().getNombre())
        );
    }
}
