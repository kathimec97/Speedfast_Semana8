package cl.duoc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de establecer y configurar la conexión con la base de datos.
 * @author Katherine
 *
 */
public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "25777";

    private ConexionBD() {}

 public static Connection getConnection() {
        Connection conexion = null;

        try{
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexion establecida!");
        }catch(SQLException e){
            System.out.println("Error al conectar con la base de datos!");
        }
        return conexion;
 }
}
