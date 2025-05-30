package org.kata2.main;

import org.kata2.model.Categoria;
import org.kata2.service.CategoriaService;

public class Main {
    public static void main(String[] args) {
        CategoriaService service = new CategoriaService();

        // Crear categoría
        service.crear(new Categoria("Hogar", "Productos para el hogar"));

        // Listar
        service.listar().forEach(c -> System.out.println(c.getId() + ": " + c.getNombre()));
    }
}
