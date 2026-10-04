package cl.duoc.dao.impl;

import cl.duoc.modelo.Pedido;

import java.util.List;

public interface PedidoDAO {
    void create(Pedido pedido);
    void update(Pedido pedido);
    void delete(int id);
    List<Pedido> readAll();
}
