package cl.duoc.dao;

import cl.duoc.modelo.Repartidor;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia (CRUD) para la entidad Repartidor.
 * Establece el contrato estándar para interactuar con la tabla de repartidores en la base de datos.
 */
public interface RepartidorDAO {
     /**
      * Registra un nuevo repartidor en el sistema.
      *
      * @param repartidor Objeto que contiene los datos del repartidor a persistir.
      * @throws SQLException
      */
     void create(Repartidor repartidor) throws SQLException;

     /**
      * Recupera todos los repartidores registrados en la base de datos.
      *
      * @return Una colección (List) con todos los objetos Repartidor.
      * @throws SQLException
      */
     List<Repartidor> readAll() throws SQLException;

     /**
      * Actualiza la información de un repartidor existente.
      *
      * @param repartidor Objeto repartidor con los datos modificados y su respectivo ID.
      */
     void update(Repartidor repartidor);

     /**
      * Elimina permanentemente a un repartidor del sistema.
      *
      * @param id El identificador numérico del repartidor a eliminar.
      */
     void delete(int id);
}
