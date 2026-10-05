package cl.duoc.vista;

import cl.duoc.controlador.ControladorPedidos;
import cl.duoc.modelo.EstadoPedido;
import cl.duoc.modelo.TipoPedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class VistaVentanaPedidos extends JFrame {


    private JTextField txtDireccion;
    private JComboBox<TipoPedido> comboTipo;
    private JComboBox<EstadoPedido> comboEstado;
    private JComboBox<String> comboFiltroEstado;
    private JComboBox<String> comboFiltroTipo;

    private JTable tlPedido;
    private DefaultTableModel modeloPedido;
    private ControladorPedidos controlador;

    public VistaVentanaPedidos() throws SQLException, ClassNotFoundException {
        controlador = new ControladorPedidos();

        setTitle("Gestion de Pedidos - Speedfast");
        setSize(750, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        txtDireccion = new JTextField();
        comboTipo = new JComboBox<>(TipoPedido.values());
        comboEstado = new JComboBox<>(EstadoPedido.values());

        panelFormulario.add(new JLabel("Direccion:"));
        panelFormulario.add(txtDireccion);
        panelFormulario.add(new JLabel("Tipo de Pedido:"));
        panelFormulario.add(comboTipo);
        panelFormulario.add(new JLabel("Estado (Para Editar): "));
        panelFormulario.add(comboEstado);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonGuardar = new JButton("Guardar");
        JButton botonEditar = new JButton("Editar");
        JButton botonEliminar = new JButton("Eliminar");

        panelBotones.add(botonGuardar);
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.add(new JLabel("Filtrar por estado:"));
        comboFiltroEstado = new JComboBox<>(new String []{"TODOS", "PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        panelFiltros.add(comboFiltroEstado);

        panelFiltros.add(new JLabel("Filtrar por tipo:"));
        JComboBox<String> comboFiltroTipo = new JComboBox<>(new String[]{"TODOS", "COMIDA", "ENCOMIENDA", "EXPRESS"});
        panelFiltros.add(comboFiltroTipo);


        panelCentro.add(panelFiltros, BorderLayout.NORTH);

        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloPedido = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tlPedido = new JTable(modeloPedido);

        tlPedido.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                int fila = tlPedido.getSelectedRow();
                if(fila >= 0) {
                    txtDireccion.setText(modeloPedido.getValueAt(fila, 1).toString());
                    comboTipo.setSelectedItem(modeloPedido.getValueAt(fila, 2));
                    comboEstado.setSelectedItem(modeloPedido.getValueAt(fila, 3));
                }
            }
        });

        panelCentro.add(new JScrollPane(tlPedido), BorderLayout.CENTER);
        add(panelCentro, BorderLayout.CENTER);

        //EVENTOS DE LOS BOTONES

        botonGuardar.addActionListener(e -> {
            controlador.agregarPedido(txtDireccion.getText(), (TipoPedido) comboTipo.getSelectedItem(), modeloPedido);
            txtDireccion.setText("");
            comboTipo.setSelectedItem(0);
        });

        botonEditar.addActionListener(e -> {
            int fila = tlPedido.getSelectedRow();
            if(fila >= 0) {
                int id = (int) modeloPedido.getValueAt(fila, 0);
                controlador.editarPedido(id, txtDireccion.getText(),
                        (TipoPedido) comboTipo.getSelectedItem(),
                        (EstadoPedido) comboEstado.getSelectedItem(),
                        modeloPedido);
                txtDireccion.setText("");
                tlPedido.clearSelection();
            }else {
                JOptionPane.showMessageDialog(null, "Por favor seleccione un pedido de la tabla para editar", "Atención", JOptionPane.WARNING_MESSAGE);

            }
        });

        botonEliminar.addActionListener(e -> {
            int fila = tlPedido.getSelectedRow();
            if(fila >= 0) {
                int id = (int) modeloPedido.getValueAt(fila, 0);
                controlador.eliminarPedido(id, modeloPedido);
                txtDireccion.setText("");
            }else {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de l tabla para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        });

        //Evento del comboBox de filtros
        comboFiltroEstado.addActionListener(e -> {
            String filtroEstado = comboFiltroEstado.getSelectedItem().toString();
            String filtroTipo = comboFiltroTipo.getSelectedItem().toString();
            try {
               controlador.cargarTabla(modeloPedido, filtroTipo, filtroEstado);
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al filtrar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        comboFiltroTipo.addActionListener(e -> {
            String filtroEstado = comboFiltroEstado.getSelectedItem().toString();
            String filtroTipo = comboFiltroTipo.getSelectedItem().toString();
            try{
                controlador.cargarTabla(modeloPedido, filtroTipo, filtroEstado);

            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al filtrar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        //CARGAR INICIAL DE LAS TABLAS
        try {
            controlador.cargarTabla(modeloPedido, "TODOS", "TODOS");

        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }
}