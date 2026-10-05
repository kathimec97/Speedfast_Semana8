


package cl.duoc;

import cl.duoc.vista.VistaVentanaPrincipal;
import javax.swing.SwingUtilities;

        public class Main {
            public static void main(String[] args) {


                SwingUtilities.invokeLater(() -> {


                    VistaVentanaPrincipal ventanaPrincipal = new VistaVentanaPrincipal();


                    ventanaPrincipal.setVisible(true);

                });
            }
        }

