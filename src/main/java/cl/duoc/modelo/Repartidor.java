package cl.duoc.modelo;


/**
 * Representa a los repartidores registrados en el sistema SpeedFast
 *
 * @author Katherine
 */
public class Repartidor {
    private int id;
    private String nombre;


    public Repartidor(String nombre, int id) {

        this.nombre = nombre;
        this.id = id;

    }

    public Repartidor() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

