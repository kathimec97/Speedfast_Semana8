package cl.duoc.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modelo que representa una entrega en el sistema SpeedFast.
 * <p>
 * Es el registro de la relación entre un Pedido especifico con el repartidor que hizo la entrega.
 * Además de la fecha y la hora exacta en la que ocurrió la entrega.
 *
 * @author Katherine
 */
public class Entrega {

        private int idEntrega;
        private Pedido pedido;
        private Repartidor repartidor;
        private LocalDate fechaEntrega;
        private LocalTime horaEntrega;

        public Entrega() {

        }

        public int getIdEntrega() {
            return idEntrega;
        }

        public void setIdEntrega(int idEntrega) {
            this.idEntrega = idEntrega;
        }

        public Repartidor getRepartidor() {
            return repartidor;
        }

        public void setRepartidor(Repartidor repartidor) {
            this.repartidor = repartidor;
        }

        public Pedido getPedido() {
            return pedido;
        }

        public void setPedido(Pedido pedido) {
            this.pedido = pedido;
        }

        public LocalDate getFechaEntrega() {
            return fechaEntrega;
        }

        public void setFechaEntrega(LocalDate fechaEntrega) {
            this.fechaEntrega = fechaEntrega;
        }

        public LocalTime getHoraEntrega() {
            return horaEntrega;
        }

        public void setHoraEntrega(LocalTime horaEntrega) {
            this.horaEntrega = horaEntrega;
        }
    }


