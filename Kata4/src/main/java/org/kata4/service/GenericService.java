package org.kata4.service;

import java.util.List;

public interface GenericService<T> {
    void crear(T t);
    T leer(int id);
    void actualizar(T t);
    void eliminar(int id);
    List<T> listar();
}
