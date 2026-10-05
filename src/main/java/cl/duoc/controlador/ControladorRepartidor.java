package cl.duoc.controlador;

import cl.duoc.dao.RepartidorDAO;
import cl.duoc.dao.impl.RepartidorDAOImpl;
import cl.duoc.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Actúa como intermediario entre la interfaz gráfica y la base de datos.
 * Su responsabilidad es validar los datos de entrada, manejar errores y actualizar los components visuales
 * como el JTable.
 * @author Katherine
 */
public class ControladorRepartidor {

    private final RepartidorDAO repartidorDAO;

    public ControladorRepartidor() {
        this.repartidorDAO = new RepartidorDAOImpl();
    }

            /**
             * Limpia y vuelve a poblar el DefaultTableModel con los datos actualizados
             * de la base de datos. Se debe llamar al iniciar la ventana y después de cada operación.
             *
             * @param modelo El modelo de la tabla (JTable) que se desea actualizar.
             * @throws SQLException
             */
            public void cargarTabla(DefaultTableModel modelo) throws SQLException {
                modelo.setRowCount(0);
                List<Repartidor> lista = repartidorDAO.readAll();
                for (Repartidor repartidor : lista) {
                    modelo.addRow(new Object[]{repartidor.getId(), repartidor.getNombre()});
                }
            }

            /**
             * Válida y registra un nuevo repartidor en la base de datos.
             *
             * @param modelo El modelo de la tabla visual para actualizarla tras el registro.
             * @param nombre El nombre del repartidor capturado desde el formulario
             *
             */
            public void agregarRepartidor(String nombre, DefaultTableModel modelo) {

                if (nombre == null || nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre es obligatorio");
                }

                try {
                    Repartidor nuevo = new Repartidor();
                    nuevo.setNombre(nombre);

                    repartidorDAO.create(nuevo);

                    cargarTabla(modelo);
                    JOptionPane.showMessageDialog(null, "Repartidor agregado correctamente");

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            /**
             * Solicita al DAO la lista completa de repartidores.
             *
             * @return Lista de objetos Repartidor.
             * @throws SQLException
             */
            public List<Repartidor> ListarRepartidores() throws SQLException {
                return repartidorDAO.readAll();
            }


            /**
             * Válida y actualiza los datos de un repartidor existente.
             *
             * @param id     El identificador único del repartidor a editar.
             * @param nombre El nuevo nombre del repartidor.
             * @param modelo El modelo de la tabla visual para refrescar los datos.
             */
            public void editarRepartidor(int id, String nombre, DefaultTableModel modelo) {
                if (id <= 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un repartidor valido");
                    return;
                }

                if (nombre == null || nombre.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
                    return;
                }

                try {
                    Repartidor repartidorEditado = new Repartidor();
                    repartidorEditado.setId(id);
                    repartidorEditado.setNombre(nombre);

                    repartidorDAO.update(repartidorEditado);

                    cargarTabla(modelo);
                    JOptionPane.showMessageDialog(null, "Repartidor editado correctamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al editar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

            /**
             * Elimina un repartidor de la base de datos previa confirmación del usuario.
             *
             * @param id     El identificador del repartidor a eliminar.
             * @param modelo El modelo de la tabla para actualizar la vista.
             */
            public void eliminarRepartidor(int id, DefaultTableModel modelo) {

                if (id <= 0) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un repartidor valido");
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar este repartidor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        repartidorDAO.delete(id);

                        cargarTabla(modelo);
                        JOptionPane.showMessageDialog(null, "Repartidor eliminado correctamente");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }

        }
