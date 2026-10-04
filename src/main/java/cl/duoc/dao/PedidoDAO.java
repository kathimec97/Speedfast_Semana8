package cl.duoc.dao;

import cl.duoc.modelo.Pedido;

import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia (CRUD) para la entidad Pedido.
 * Establece el contrato para gestionar la creación, modificación y seguimiento de los pedidos.
 */

public interface PedidoDAO {

    /**
     * Registra un nuevo pedido en el sistema.
     *
     * @param pedido Objeto que contiene los detalles del pedido (direccion, tipo, estado)
     */
    void create(Pedido pedido);

    /**
     * Actualiza la información de un pedido existente, útil para cambiar su estado o dirección.
     *
     * @param pedido Objeto con los datos actualizados y su respectivo ID.
     */
    void update(Pedido pedido);

    /**
     * Elimina permanentemente un pedido del sistema.
     *
     * @param id El identificador númerico único del pedido a eliminar.
     */
    void delete(int id);

    /**
     * Recupera todos los pedidos registrados en la base de datos.
     *
     * @return Una colección (List) con todos los objetos Pedido.
     */
    List<Pedido> readAll();


    /**
     * Filtra los pedidos según su estado actual.
     * @param estado El estado a buscar (PENDIENTE, EN_REPARTO, ENTREGADO).
     *
     * @return Lista de pedidos que coinciden con el estado.
     */
    List<Pedido> readByEstado(String estado);


    /**
     * Filtra los pedidos según su tipo.
     * @param tipo El tipo de pedido (COMIDA, ENCOMIENDA, EXPRESS).
     *
     * @return Lista de pedidos que coinciden con el tipo..
     */
    List<Pedido> readByTipo(String tipo);
}
