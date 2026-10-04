package cl.duoc.dao.impl;

import cl.duoc.dao.PedidoDAO;
import cl.duoc.modelo.EstadoPedido;
import cl.duoc.modelo.Pedido;
import cl.duoc.modelo.TipoPedido;
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
 * Implementación de la interfaz PedidoDAO.
 * Se encarga de gestionar todas las operaciones de persistencia (CRUD) para la entidad Pedido
 * en la base de datos, garantizando un acceso seguro a los datos mediante PreparedStatement.
 * @author Katherine
 */
public class PedidoDAOImpl implements PedidoDAO {

    private final Connection conexion;
    private static final Logger LOGGER = Logger.getLogger(PedidoDAOImpl.class.getName());

    /**
     * Constructor de la clase.
     * Al instanciar esta clase, inicializa automaticamente la conexion a la base de datos
     * utilizando la clase de configuracion ConexionBD.
     */
    public PedidoDAOImpl() {
        this.conexion = ConexionBD.getConnection();
    }

    /**
     * Registra un nuevo pedido en la base de datos.
     * El identificador (ID) no se incluye en la consulta, ya que se genera automáticamente.
     *
     * @param pedido Objeto de tipo Pedido que contiene la dirección, el tipo y el estado a guardar.
     */
    @Override
    public void create(Pedido pedido) {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido().name());
            stmt.setString(3, pedido.getEstadoPedido().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al crear Pedido", e);
        }
    }

    /**
     * Actualiza la información de un pedido existente en la base de datos.
     * Extrae el ID del objeto recibido para identificar de forma exacta qué registro debe modificar a traves
     * de la cláusula WHERE.
     * @param pedido Objeto de tipo Pedido que contiene los nuevos datos y el ID del registro a editar.
     */
    @Override
    public void update(Pedido pedido) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo=?, estado=?  WHERE id = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido().name());
            stmt.setString(3, pedido.getEstadoPedido().name());
            stmt.setInt(4, pedido.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Error al actualizar Pedido ",e);
        }
    }

    /**
     * Elimina un pedido de la base de datos de manera permanente.
     *
     * @param id El número identificador único del pedido que se desea borrar.
     */
    @Override
    public void delete(int id) {
    String sql = "DELETE FROM pedidos  WHERE id = ?";
    try(PreparedStatement stmt = conexion.prepareStatement(sql)){
        stmt.setInt(1, id);
        stmt.executeUpdate();
        
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE,"Error al eliminar Pedido ",e);
    }
    }

    /**
     * Consulta y recupera todos los pedidos registrados en la tabla de la base de datos.
     * Recorre el ResultSet devuelto por MySQL, mapea cada fila a un objeto Pedido y los agrupa.
     *
     * @return Una colección (List) que contiene todos los objetos Pedido encontrados.
     */
    @Override
    public List<Pedido> readAll() {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setDireccionEntrega(rs.getString("direccion"));
                pedido.setTipoPedido(TipoPedido.valueOf(rs.getString("tipo")));
                pedido.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado")));
                listaPedidos.add(pedido);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener Pedidos", e);
        }
    return listaPedidos;
    }

    @Override
    public List<Pedido> readByEstado(String estado) {
        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos  WHERE estado = ?";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id"));
                pedido.setDireccionEntrega(rs.getString("direccion"));
                pedido.setTipoPedido(TipoPedido.valueOf(rs.getString("tipo")));
                pedido.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado")));

                listaPedidos.add(pedido);
            }
            }catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al filtrar Pedidos por estado", e);
        }
        return listaPedidos;
    }

    @Override
    public List<Pedido> readByTipo(String tipo) {

            ArrayList<Pedido> listaPedidos = new ArrayList<>();
            String sql = "SELECT * FROM pedidos  WHERE tipo = ?";

            try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
                stmt.setString(1, tipo);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setDireccionEntrega(rs.getString("direccion"));
                    pedido.setTipoPedido(TipoPedido.valueOf(rs.getString("tipo")));
                    pedido.setEstadoPedido(rs.getString("estado"));

                    listaPedidos.add(pedido);
                }
            }catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error al filtrar Pedidos por tipo.", e);
            }
            return listaPedidos;
        }

    }

