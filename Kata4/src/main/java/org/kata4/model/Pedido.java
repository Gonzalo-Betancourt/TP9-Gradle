package org.kata4.model;

import org.kata4.model.ItemPedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private LocalDate fecha;
    private List<ItemPedido> items;
    private double total;

    public Pedido() {
        this.fecha = LocalDate.now();
        this.items = new ArrayList<>();
    }

    public void agregarItem(ItemPedido item) {
        this.items.add(item);
        recalcularTotal();
    }

    public void recalcularTotal() {
        total = items.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
