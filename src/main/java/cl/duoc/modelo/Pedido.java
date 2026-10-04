package cl.duoc.modelo;



/**
 * Representa un pedido a registrar dentro del sistema SpeedFast.
 * @author Katherine
 */
public class Pedido {
    private int id;
    private String direccionEntrega;
    private EstadoPedido estadoPedido;
    private TipoPedido tipoPedido;

    public Pedido(int id, String direccionEntrega, EstadoPedido estadoPedido, TipoPedido tipoPedido) {
        this.direccionEntrega = direccionEntrega;
        this.id = id;
        this.estadoPedido = EstadoPedido.PENDIENTE;
        this.tipoPedido = tipoPedido;
    }

    public Pedido() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    /**
     * Actualiza el estado del pedido a partir de una cadena de texto.
     * @param nuevoEstado
     */
    public void setEstadoPedido(String nuevoEstado) {
        this.estadoPedido = EstadoPedido.valueOf(nuevoEstado);
    }

    @Override
    public String toString() {
        return
                " \n #" + id + '\n' +
                        " Dirección de Entrega: " + direccionEntrega + '\n' +
                        " Estado del Pedido: " + estadoPedido;
    }
}
