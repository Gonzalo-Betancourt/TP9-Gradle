package org.kata4.service;

import org.kata4.dao.CategoriaDAO;
import org.kata4.model.Categoria;

import java.util.List;

public class CategoriaService implements GenericService<Categoria> {

    private final CategoriaDAO dao = new CategoriaDAO();

    @Override
    public void crear(Categoria c) {
        if (c.getNombre() == null || c.getNombre().isBlank()) {
            System.out.println("⚠ El nombre no puede estar vacío.");
            return;
        }
        if (dao.existeNombre(c.getNombre())) {
            System.out.println("⚠ Ya existe una categoría con ese nombre.");
            return;
        }
        dao.crear(c);
    }

    @Override
    public Categoria leer(int id) {
        return dao.leer(id);
    }

    @Override
    public void actualizar(Categoria c) {
        dao.actualizar(c);
    }

    @Override
    public void eliminar(int id) {
        dao.eliminar(id);
    }

    @Override
    public List<Categoria> listar() {
        return dao.listar();
    }
}
