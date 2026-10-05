package cl.duoc.controlador;

import cl.duoc.dao.EntregaDAO;
import cl.duoc.dao.impl.EntregaDAOImpl;
import cl.duoc.modelo.Entrega;
import cl.duoc.modelo.Pedido;
import cl.duoc.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ControladorEntregas {
    private final EntregaDAO entregaDAO;


    public ControladorEntregas() {
        this.entregaDAO = new EntregaDAOImpl();

    }

    public void cargarTabla(DefaultTableModel modelo) throws SQLException {
        modelo.setRowCount(0);
        List<Entrega> lista = entregaDAO.readAll();

        for (Entrega entrega : lista) {
            modelo.addRow(new Object[]{entrega.getIdEntrega(),
            entrega.getPedido() != null ? entrega.getPedido().getId() : "",
            entrega.getRepartidor() != null ? entrega.getRepartidor().getId() : "",
            entrega.getFechaEntrega(),
            entrega.getHoraEntrega()
            });

        }
    }

    public void guardar(int idPedido, int idRepartidor, LocalDate fecha, LocalTime hora, DefaultTableModel modelo) {
        try{
            Pedido pedido = new Pedido();
            pedido.setId(idPedido);

            Repartidor repartidor = new Repartidor();
            repartidor.setId(idRepartidor);

            Entrega nueva =  new Entrega();
            nueva.setPedido(pedido);
            nueva.setRepartidor(repartidor);
            nueva.setFechaEntrega(fecha);
            nueva.setHoraEntrega(hora);

            entregaDAO.create(nueva);
            cargarTabla(modelo);
            JOptionPane.showMessageDialog(null, "Entrega guardado exitosamente");
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la entrega: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);


        }
    }

    public void editarEntrega(int idEntrega, int idRepartidor, LocalDate fecha, LocalTime hora, DefaultTableModel modelo) {
        if (idEntrega <=0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una entrega", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            Repartidor repartidor = new Repartidor();
            repartidor.setId(idRepartidor);

            Entrega editada = new Entrega();
            editada.setIdEntrega(idEntrega);
            editada.setRepartidor(repartidor);
            editada.setFechaEntrega(fecha);
            editada.setHoraEntrega(hora);

            entregaDAO.update(editada);
            cargarTabla(modelo);
            JOptionPane.showMessageDialog(null, "Entrega editado exitosamente");
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar entrega: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarEntrega(int idEntrega, DefaultTableModel modelo) {
        if(idEntrega <=0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una entrega", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estas seguro que deseas eliminar esta entrega?", "Confirmar eliminación",  JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            try{
                entregaDAO.delete(idEntrega);
                cargarTabla(modelo);
                JOptionPane.showMessageDialog(null, "Entrega eliminado exitosamente");
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar entrega: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        }
    }

}
