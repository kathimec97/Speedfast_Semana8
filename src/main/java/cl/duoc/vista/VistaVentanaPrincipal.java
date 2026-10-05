package cl.duoc.vista;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class VistaVentanaPrincipal extends JFrame {
    private JButton botonPedidos;
    private JButton botonRepartidores;
    private JButton botonEntregas;

    public VistaVentanaPrincipal() {

        setTitle("Ventana Principal-SpeedFast");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Ventana Principal-SpeedFast", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 80, 40, 80));

        JButton botonPedidos = new JButton("Pedidos");
        JButton botonRepartidores = new JButton("Repartidores");
        JButton botonEntregas = new JButton("Entregas");

        panelBotones.add(botonPedidos);
        panelBotones.add(botonRepartidores);
        panelBotones.add(botonEntregas);
        add(panelBotones, BorderLayout.CENTER);

        botonPedidos.addActionListener(e -> {
            try {
                new VistaVentanaPedidos().setVisible(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        botonRepartidores.addActionListener(e -> new VistaVentanaRepartidores().setVisible(true));
        botonEntregas.addActionListener(e -> {
            new VistaVentanaEntregas().setVisible(true);
        });

    }

}
