package cl.duoc.controlador;

import cl.duoc.dao.PedidoDAO;
import cl.duoc.dao.impl.PedidoDAOImpl;
import cl.duoc.modelo.EstadoPedido;
import cl.duoc.modelo.Pedido;
import cl.duoc.modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * ControladorPedidos.
 * Gestiona la lógica de negocio para la entidad Pedido, conectando la vista
 * con el DAO correspondiente y aplicando validaciones y manejo de excepciones.
 *
 * @author Katherine
 */
public class ControladorPedidos {

    private final PedidoDAO pedidoDAO;

    public ControladorPedidos() throws SQLException, ClassNotFoundException {
        this.pedidoDAO = new PedidoDAOImpl();


    }

    /**
     * Carga y actualiza el modelo de latabla con los pedidos registrados, permitiendo filtrar
     * opcionalmente por tipo o estado.
     *
     * @param modelo       El DefaultTableModel de la tabla.
     * @param filtroTipo   Filtro seleccionado por tipo de pedido
     * @param filtroEstado Filtro seleccionado por estado de pedido
     */
    public void cargarTabla(DefaultTableModel modelo, String filtroTipo, String filtroEstado) {
        modelo.setRowCount(0);
        List<Pedido> lista;

        if (filtroEstado != null && !filtroEstado.equals("TODOS")) {
            lista = pedidoDAO.readByEstado(filtroEstado);
        } else if (filtroTipo != null && !filtroTipo.equals("TODOS")) {
            lista = pedidoDAO.readByTipo(filtroTipo);

        } else {
            lista = pedidoDAO.readAll();
        }

        for (Pedido pedido : lista) {
            modelo.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getEstadoPedido()
            });
        }

    }

    /**
     * Válida y registra un nuevo pedido en la base de datos con estado inicial PENDIENTE
     *
     * @param direccion
     * @param tipo
     * @param modelo
     */
    public void agregarPedido(String direccion, TipoPedido tipo, DefaultTableModel modelo) {
        if (direccion == null || direccion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La dirección del pedido es obligatoria");
            return;
        }

        try {
            Pedido nuevo = new Pedido(0, direccion, EstadoPedido.PENDIENTE, tipo);
            pedidoDAO.create(nuevo);
            cargarTabla(modelo, "TODOS", "TODOS");
            JOptionPane.showMessageDialog(null, "Pedido agregado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Válida y actualiza los datos de un pedido existente (incluyendo su estado)
     *
     */
    public void editarPedido(int id, String direccion, TipoPedido tipo, EstadoPedido estado, DefaultTableModel modelo) {
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un pedido válido para editar", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La dirección no puede estar vacía.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Pedido editado = new Pedido();
            editado.setId(id);
            editado.setDireccionEntrega(direccion);
            editado.setTipoPedido(tipo);
            editado.setEstadoPedido(estado);

            pedidoDAO.update(editado);
            cargarTabla(modelo, "TODOS", "TODOS");
            JOptionPane.showMessageDialog(null, "Pedido editado correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al editar el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    /**
     * Elimina un pedido de la base de datos previa confirmación
     *
     */
    public void eliminarPedido(int id, DefaultTableModel modelo) {
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un pedido para eliminar", "Validación", JOptionPane.WARNING_MESSAGE);
            return;

        }

        int confirmacion = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar este pedido?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                pedidoDAO.delete(id);
                cargarTabla(modelo, "TODOS", "TODOS");
                JOptionPane.showMessageDialog(null, "Pedido eliminado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

