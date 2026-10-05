package cl.duoc.vista;




import cl.duoc.controlador.ControladorEntregas;
import cl.duoc.dao.PedidoDAO;
import cl.duoc.dao.impl.PedidoDAOImpl;
import cl.duoc.dao.impl.RepartidorDAOImpl;
import cl.duoc.modelo.Pedido;
import cl.duoc.modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Vista principal para la gestión de Entregas.
 * Proporciona la interfaz gráfica para asignar repartidores a pedidos pendientes,
 * visualizar el historial de entregas con filtros dinámicos y administrar los registros (CRUD).
 */
public class VistaVentanaEntregas extends JFrame {

    private JComboBox<String> comboPedidos;
    private JComboBox<String> comboRepartidores;
    private JButton botonGuardar;
    private JButton botonActualizar;
    private JButton botonEliminar;

    private JTable tablaEntregas;
    private DefaultTableModel modeloEntregas;

    private final ControladorEntregas controlador;

    public VistaVentanaEntregas() {
        this.controlador = new ControladorEntregas();

        setTitle("Ventana de Entregas - Speedfast");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelEntrega = new JPanel(new GridLayout(3, 2, 10, 10));
        panelEntrega.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        comboPedidos = new JComboBox<>();
        comboRepartidores = new JComboBox<>();
        botonGuardar = new JButton("Guardar");
        panelEntrega.add(new JLabel("Pedido Pendiente:"));
        panelEntrega.add(comboPedidos);
        panelEntrega.add(new JLabel("Repartidor:"));
        panelEntrega.add(comboRepartidores);
        panelEntrega.add(new JLabel(""));
        panelEntrega.add(botonGuardar);

        add(panelEntrega, BorderLayout.NORTH);


        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.add(new JLabel("Filtrar por:"));

        JComboBox<String> comboFiltro = new JComboBox<>(new String[]{"TODOS", "Pedido", "Repartidor"});
        panelFiltros.add(comboFiltro);

        JComboBox<String> comboValorFiltro = new JComboBox<>();
        comboValorFiltro.setEnabled(false);
        panelFiltros.add(comboValorFiltro);



        panelCentro.add(panelFiltros,  BorderLayout.NORTH);

        String[] columnas = {"ID Entrega", "ID Pedido", "ID Repartidor", "Fecha", "Hora"};
        modeloEntregas = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEntregas = new JTable(modeloEntregas);
        JScrollPane scrollPane = new JScrollPane(tablaEntregas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Entregas"));
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonActualizar = new JButton("Actualizar/Reasignar Repartidor");
        botonEliminar = new JButton("Eliminar");

        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        add(panelBotones, BorderLayout.SOUTH);


        //Boton Guardar
        botonGuardar.addActionListener(e -> {
            String pedidoSeleccionado = (String) comboPedidos.getSelectedItem();
            String repartidorSeleccionado = (String) comboRepartidores.getSelectedItem();

            if (pedidoSeleccionado == null || repartidorSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int idPedido = Integer.parseInt(pedidoSeleccionado.split(" ")[0]);
                int idRepartidor = Integer.parseInt(repartidorSeleccionado.split(" ")[0]);
                LocalDate fecha = LocalDate.now();
                LocalTime hora = LocalTime.now();

                controlador.guardar(idPedido, idRepartidor, fecha, hora, modeloEntregas);
                cargarCombos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar la selección: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        //Boton Actualizar/Editar
        botonActualizar.addActionListener(e -> {
            int fila = tablaEntregas.getSelectedRow();
            if(fila >= 0) {
                String repartidorSeleccionado = (String) comboRepartidores.getSelectedItem();

                if (repartidorSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Seleccione un nuevo repartidor del listado para reasignar", "Atención", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                try {
                    int idEntrega = (int) modeloEntregas.getValueAt(fila, 0);

                    int idRepartidor = Integer.parseInt(repartidorSeleccionado.split(" ")[0]);

                    LocalDate fecha = LocalDate.now();
                    LocalTime hora = LocalTime.now().withSecond(0).withNano(0);

                    controlador.editarEntrega(idEntrega, idRepartidor, fecha, hora, modeloEntregas);

                    tablaEntregas.clearSelection();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la entrega: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla para editar.", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        });


        //Boton Eliminar
        botonEliminar.addActionListener(e -> {
                    int fila = tablaEntregas.getSelectedRow();
                    if (fila >= 0) {
                        int idEntrega = (int) modeloEntregas.getValueAt(fila, 0);

                        controlador.eliminarEntrega(idEntrega, modeloEntregas);
                        cargarCombos();
                        tablaEntregas.clearSelection();
                    } else {
                        JOptionPane.showMessageDialog(this, "Seleccione una entrega de la tabla para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);

                    }

                });

        // Evento del comboBox de Tipo de Filtro
        comboFiltro.addActionListener(e -> {
            String tipoFiltro = comboFiltro.getSelectedItem().toString();
            comboValorFiltro.removeAllItems();

            if (tipoFiltro.equals("TODOS")) {
                comboValorFiltro.setEnabled(false);
                try {
                    controlador.cargarTabla(modeloEntregas);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar la tabla: ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (tipoFiltro.equals("Pedido")) {
                comboValorFiltro.setEnabled(true);

                try{
                    PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
                    List<Pedido> pendientes = pedidoDAO.readByEstado("PENDIENTE");
                    for (Pedido p : pendientes) {
                        comboValorFiltro.addItem(p.getId() + " - " + p.getDireccionEntrega());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,"Error al cargar pedidos: " + ex.getMessage());
                }
            } else if (tipoFiltro.equals("Repartidor")) {
                comboValorFiltro.setEnabled(true);
                try {
                    RepartidorDAOImpl rDAO = new RepartidorDAOImpl();
                    List<Repartidor> repartidores = rDAO.readAll();
                    for (Repartidor r : repartidores) {
                        comboValorFiltro.addItem(r.getId() + " - " + r.getNombre());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,"Error al cargar repartidores: " + ex.getMessage());
                }
            }



        });

        comboValorFiltro.addActionListener(e -> {
            if (comboValorFiltro.getSelectedItem() == null) return;

            String tipoFiltro = comboFiltro.getSelectedItem().toString();
            String valorFiltro = comboValorFiltro.getSelectedItem().toString();

            try {
                int idBusqueda = Integer.parseInt(valorFiltro.split(" ")[0]);
                controlador.cargarTabla(modeloEntregas, tipoFiltro, idBusqueda);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al filtrar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cargarCombos();

        try {
            controlador.cargarTabla(modeloEntregas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        setVisible(true);
    }

    private void cargarCombos() {
        comboPedidos.removeAllItems();
        comboRepartidores.removeAllItems();

        try {
            PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
            List<Pedido> pendientes = pedidoDAO.readByEstado("PENDIENTE");
            for (Pedido pedido : pendientes) {
                comboPedidos.addItem(pedido.getId() + " - " + pedido.getDireccionEntrega());
            }

            RepartidorDAOImpl repartidorDAO = new RepartidorDAOImpl();
            List<Repartidor> repartidores = repartidorDAO.readAll();
            for (Repartidor repartidor : repartidores) {
                comboRepartidores.addItem(repartidor.getId() + " - " + repartidor.getNombre());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los menus desplegables" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}