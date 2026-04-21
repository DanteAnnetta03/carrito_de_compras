import java.time.LocalDateTime;
import java.util.List;

class Item {
    private Carrito carrito;
    private Producto producto;
    private int cantidad;
    private Double precioUnitario;

    public Double getPrecio() {
        return this.cantidad * this.precioUnitario;
    }

    public Double getPrecioOficial() {
        return this.producto.precioEnFecha(this.carrito.getFechaCompra());
    }

    public Double getDescuento() {
        return this.getPrecioOficial() - this.getPrecio();
    }
}

class Producto {
    private String EAN;
    private String nombre;
    private List<PrecioProducto> preciosHistoricos;

    public String getEAN() {
        return this.EAN;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Double precioEnFecha(LocalDateTime fecha) {
        for (PrecioProducto precio : preciosHistoricos) {
            if (precio.cumpleVigencia(fecha)) {
                return precio.getPrecio();
            }
        }
    }

}

class PrecioProducto {
    private LocalDateTime fechaInicioVigencia;
    private LocalDateTime fechaFinVigencia;
    private Double precio;

    public Double getPrecio() {
        return this.precio;
    }

    public LocalDateTime getFechaInicioVigencia() {
        return this.fechaInicioVigencia;
    }

    public LocalDateTime getFechaFinVigencia() {
        return this.fechaFinVigencia;
    }

    public Boolean cumpleVigencia(LocalDateTime fecha) {
        return !fecha.isBefore(fechaInicioVigencia) && !fecha.isAfter(fechaFinVigencia);
    }
}
