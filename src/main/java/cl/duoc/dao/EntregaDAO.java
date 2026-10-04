package cl.duoc.dao;

import cl.duoc.modelo.Entrega;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia y consultas específicas para la entidad Entrega.
 * Gestiona la tabla intermedia que vincula los pedidos con los repartidores.
 * @author Katherine
 */
public interface EntregaDAO {

    /**
     * Registra una nueva entrega en el sistema, asociando un pedido a un repartidor.
     *
     * @param entrega Objeto Entrega que contiene los IDs foráneos, fecha y hora.
     */
    void create(Entrega entrega);

    /**
     * Recupera todas las entregas registradas en la base de datos.
     *
     * @return Una colección (List) con todas las entregas.
     */
    List<Entrega> readAll();

    /**
     * Actualiza los detalles de una entrega existente (fecha, hora y/o repartidor asignado).
     *
     * @param entrega Objeto Entrega con los nuevos datos y su identificador.
     */
    void update(Entrega entrega);

    /**
     * Elimina permanentemente una entrega del sistema.
     *
     * @param id El identificador numérico único de la entrega a eliminar.
     */
    void delete(int id);

    /**
     * Filtra y obtiene todas las entregas asignadas a un repartidor específico.
     *
     * @param repartidor El identificador numérico del repartidor.
     * @return Una colección (List) de las entregas correspondientes a dicho repartidor.
     */
    List<Entrega> readByRepartidor(int repartidor);

    /**
     * Filtra y obtiene todas las entregas vinculadas a un pedido específico.
     *
     * @param pedido El identificador numérico del pedido.
     * @return Una colección (List) de las entregas correspondientes a dicho pedido.
     */
    List<Entrega> readByPedido(int pedido);

}
