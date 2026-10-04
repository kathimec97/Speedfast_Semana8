package cl.duoc.dao.impl;

import cl.duoc.dao.EntregaDAO;
import cl.duoc.modelo.Entrega;
import cl.duoc.modelo.Pedido;
import cl.duoc.modelo.Repartidor;
import cl.duoc.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  Implementación de la interfaz EntregaDAO.
 *  Se encarga de gestionar todas las operaciones de persistencia (CRUD) para la entidad Entrega
 *  en la base de datos, garantizando un acceso seguro a los datos mediante PreparedStatement.
 *  @author Katherine
 *
 */

public class EntregaDAOImpl implements EntregaDAO {

    private final Connection conexion;
    private static final Logger LOGGER = Logger.getLogger(EntregaDAOImpl.class.getName());

    /**
     * Constructor de la clase.
     * Al instanciar esta clase, inicializa automaticamente la conexion a la base de datos
     * utilizando la clase de configuracion ConexionBD.
     */
    public EntregaDAOImpl() {
        this.conexion = ConexionBD.getConnection();
    }


    /**
     * Registra una nueva entrega en la base de datos.
     * Extrae los identificadores de los objetos Pedido y Repartidor asociados,
     * y convierte las fechas y horas locales a los formatos nativos de SQL.
     *
     * @param entrega Objeto de tipo entrega que contiene los datos a persistir.
     */
    @Override
    public void create(Entrega entrega) {
        String sql = "INSERT INTO entregas(id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, entrega.getPedido().getId());
            stmt.setInt(2, entrega.getRepartidor().getId());

            stmt.setDate(3, java.sql.Date.valueOf(entrega.getFechaEntrega()));
            stmt.setTime(4, java.sql.Time.valueOf(entrega.getHoraEntrega()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al registrar entrega", e);
        }
    }

    /**
     * Recupera todos los registros de entregas almacenados en la base de datos.
     *
     * @return Una lista (list) de Objeto Entrega. Si no hay registros, retorna una lista vacía.
     */
    @Override
    public List<Entrega> readAll() {
        ArrayList<Entrega> listaEntregas = new ArrayList<>();
        String sql = "SELECT * FROM entregas";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id_pedido"));

                Repartidor repartidor = new Repartidor();
                repartidor.setId(rs.getInt("id_repartidor"));

                Entrega entrega = new Entrega();
                entrega.setIdEntrega(rs.getInt("id"));
                entrega.setPedido(pedido);
                entrega.setRepartidor(repartidor);

                entrega.setFechaEntrega(rs.getDate("fecha").toLocalDate());
                entrega.setHoraEntrega(rs.getTime("hora").toLocalTime());

                listaEntregas.add(entrega);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener lista de entregas", e);
        }

        return listaEntregas;
    }

    /**
     * Actualiza la información de una entrega existente.
     * Permite modificar la fecha, la hora y reasignar el repartidor manteniendo intacto el pedido original.
     * @param entrega Objeto de tipo Entrega con los datos actualizados y el ID de registro a modificar.
     */
    @Override
    public void update(Entrega entrega) {
        String sql = "UPDATE entregas SET fecha = ?, hora = ?, id_repartidor = ? WHERE id = ? ";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(entrega.getFechaEntrega()));
            stmt.setTime(2, java.sql.Time.valueOf(entrega.getHoraEntrega()));
            stmt.setInt(3, entrega.getRepartidor().getId());
            stmt.setInt(4, entrega.getIdEntrega());

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar entrega", e);
        }
    }

    /**
     * Elimina permanentemente una entrega de la base de datos.
     *
     * @param id El identificador numérico único de la entrega a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM entregas WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar entrega", e);
        }
    }

    /**
     * Filtra y recupera las entregas asociadas a un repartidor especifico.
     *
     * @param repartidor El ID numérico del repartidor a buscar.
     * @return Una lista de entregas asignadas a dicho repartidor.
     */
    @Override
    public List<Entrega> readByRepartidor(int repartidor) {
        ArrayList<Entrega> listaEntregas = new ArrayList<>();
        String sql = "SELECT * FROM entregas WHERE id_repartidor = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, repartidor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id_pedido"));

                Repartidor rep = new Repartidor();
                rep.setId(rs.getInt("id_repartidor"));

                Entrega entrega = new Entrega();
                entrega.setIdEntrega(rs.getInt("id"));
                entrega.setPedido(pedido);
                entrega.setRepartidor(rep);
                entrega.setFechaEntrega(rs.getDate("fecha").toLocalDate());
                entrega.setHoraEntrega(rs.getTime("hora").toLocalTime());
                listaEntregas.add(entrega);

            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener lista de entregas", e);
        }
        return listaEntregas;
    }

    /**
     * Filtra y recupera las entregas asociadas a un pedido especifico.
     *
     * @param pedido El ID numérico del pedido a buscar.
     * @return Una lista de entregas vinculadas a dicho pedido.
     */

    @Override
    public List<Entrega> readByPedido(int pedido) {

        ArrayList<Entrega> listaEntregas = new ArrayList<>();
        String sql = "SELECT * FROM entregas WHERE id_pedido = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id_pedido"));

                Repartidor rep = new Repartidor();
                rep.setId(rs.getInt("id_repartidor"));

                Entrega entrega = new Entrega();
                entrega.setIdEntrega(rs.getInt("id"));
                entrega.setPedido(p);
                entrega.setRepartidor(rep);
                entrega.setFechaEntrega(rs.getDate("fecha").toLocalDate());
                entrega.setHoraEntrega(rs.getTime("hora").toLocalTime());
                listaEntregas.add(entrega);

            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener lista de entregas", e);
        }
        return listaEntregas;

    }

}