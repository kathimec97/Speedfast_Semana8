package cl.duoc.vista;

import cl.duoc.controlador.ControladorRepartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que modela la interfaz gráfica para la gestión de repartidores en el sistema SpeedFast.
 * Permite registrar, editar, eliminar y visualizar los repartidores almacenados
 * mediante una tabla (JTable) conectada con su respectivo controlador (MVC).
 * @author Katherine
 */
public class VistaVentanaRepartidores extends JFrame {

    private JTextField txtNombre;
    private JTable tblRepartidores;
    private DefaultTableModel modeloRepartidores;
    private ControladorRepartidor controlador;

    /**
     * Constructor de la ventana.
     * Inicializa los componentes gráficos, configura los eventos de los botones
     * y carga los datos iniciales desde la base de datos.
     */
    public VistaVentanaRepartidores() {
        controlador = new ControladorRepartidor();
        setTitle("Gestión de Repartidores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel(new BorderLayout());

        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos del Repartidor"));

        JPanel panelInput = new JPanel(new BorderLayout(5, 5));
        panelInput.add(new JLabel("Nombre:"), BorderLayout.WEST);
        txtNombre = new JTextField();
        panelInput.add(txtNombre, BorderLayout.CENTER);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        panelButton.add(btnRegistrar);
        panelButton.add(btnEditar);
        panelButton.add(btnEliminar);

        panelSuperior.add(panelInput, BorderLayout.CENTER);
        panelSuperior.add(panelButton, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre"};
        modeloRepartidores = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRepartidores = new JTable(modeloRepartidores);

        tblRepartidores = new JTable(modeloRepartidores);

   tblRepartidores.addMouseListener(new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {
           int fila = tblRepartidores.getSelectedRow();
           if (fila >= 0) {
               txtNombre.setText(modeloRepartidores.getValueAt(fila, 1).toString());
           }
       }
   });

        add(new JScrollPane(tblRepartidores), BorderLayout.CENTER);

        //------Eventos de los Botones----

        //Botón Registrar: Captura el texto y delega la inserción al controlador
        btnRegistrar.addActionListener(e -> {
            controlador.agregarRepartidor(txtNombre.getText(), modeloRepartidores);
            txtNombre.setText("");
        });

        //Botón Editar: Toma el ID de la fila seleccionada y actualiza el registro
        btnEditar.addActionListener(e -> {
            int fila = tblRepartidores.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloRepartidores.getValueAt(fila, 0);
                controlador.editarRepartidor(id, txtNombre.getText(), modeloRepartidores);
                txtNombre.setText("");
                tblRepartidores.clearSelection();
            }
        });

        //Botón Eliminar: Borra el registro seleccionado previa confirmación
        btnEliminar.addActionListener(e -> {
            int fila = tblRepartidores.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloRepartidores.getValueAt(fila, 0);
                controlador.eliminarRepartidor(id, modeloRepartidores);
                txtNombre.setText("");
            }
        });

        //sincroniza la tabla con la base de datos al abrir la ventana
        try {
            controlador.cargarTabla(modeloRepartidores);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos iniciales: " + e);
        }
    }


}





