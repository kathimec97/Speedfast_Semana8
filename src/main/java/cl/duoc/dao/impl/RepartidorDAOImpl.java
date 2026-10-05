package cl.duoc.dao.impl;

import cl.duoc.dao.RepartidorDAO;
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
 *  * Implementación de la interfaz RepartidorDAO.
 *  * Se encarga de gestionar todas las operaciones de persistencia (CRUD) para la entidad Repartidor
 *  * en la base de datos, garantizando un acceso seguro a los datos mediante PreparedStatement.
 *  * @author Katherine
 *  */

public class RepartidorDAOImpl implements RepartidorDAO {


    private final Connection conexion;
    private static final Logger LOGGER = Logger.getLogger(RepartidorDAOImpl.class.getName());


     /**
      * Constructor de la clase.
      * Al instanciar esta clase, inicializa automaticamente la conexion a la base de datos
      * utilizando la clase de configuracion ConexionBD.
      */
    public RepartidorDAOImpl() {
        this.conexion = ConexionBD.getConnection();
    }


     /**
      * Registra un nuevo repartidor en la base de datos.
      * El ID se genera automáticamente mediante AUTO_INCREMENT en MySQL.
      *
      * @param repartidor Objeto de tipo Repartidor que contiene el nombre a guardar.
      * @throws SQLException
      */
    @Override
    public void create(Repartidor repartidor) throws SQLException {
    String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, repartidor.getNombre());
        stmt.executeUpdate();

    }catch(SQLException e){
        LOGGER.log(Level.SEVERE,"Error al crea al Repartidor ",e);
    }
    }


     /**
      * Consulta y recupera todos los repartidores registrados en la base de datos.
      *
      * @return Una colección (List) que contiene todos los objetos Repartidor encontrados.
      * @throws SQLException
      */
    @Override
    public List<Repartidor> readAll() throws SQLException {

        ArrayList<Repartidor> listaRepartidores = new ArrayList<>();

        String sql = "SELECT * FROM repartidores";

        try(PreparedStatement stmt = conexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Repartidor repartidor = new Repartidor();
                repartidor.setId(rs.getInt("id"));
                repartidor.setNombre(rs.getString("nombre"));
                listaRepartidores.add(repartidor);
            }

        }catch(SQLException e){
            LOGGER.log(Level.SEVERE,"Error al listar los repartidores ",e);
        }
        return listaRepartidores;
    }

     /**
      * Actualiza la información de un repartidor existente.
      * Utiliza el ID del objeto recibido para ubicar el registro exacto a modificar
      * en la base de datos.
      * @param repartidor Objeto de tipo Repartidor con el nuevo nombre y el ID del registro a editar.
      */
    @Override
    public void update(Repartidor repartidor) {
    String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

    try(PreparedStatement stmt = conexion.prepareStatement(sql)) {

        stmt.setString(1, repartidor.getNombre());
        stmt.setInt(2, repartidor.getId());
        stmt.executeUpdate();
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE,"Error al actualizar Repartidor ",e);
    }
    }

     /**
      * Elimina un repartidor de la base de datos de forma permanente
      * @param id El número identificador único del repartidor que se desea borrar.
      */
    @Override
    public void delete(int id) {
    String sql = "DELETE FROM repartidores WHERE id = ?";
    try(PreparedStatement stmt = conexion.prepareStatement(sql)){
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }catch(SQLException e){
        LOGGER.log(Level.SEVERE,"Error al eliminar Repartidor ",e);
    }
    }
}
