package cl.duoc.dao.impl;

import cl.duoc.modelo.Repartidor;

import java.util.List;

public interface RepartidorDAO {
     void create(Repartidor repartidor);
     List<Repartidor> readAll();
     void update(Repartidor repartidor);
     void delete(int id);
}
