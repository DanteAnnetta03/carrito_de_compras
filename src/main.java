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

class Cliente{

    private String nombre;
    private String apellido;
    private String email;
    private List <Direccion> direcciones;
    private List <Carrito> carritos;
    private List <Tarjeta> tarjetas;
    private Boolean esPreferencial;

    public double getMontoDeuda(){
        double deudaTotal = 0;
        for(Carrito carrito : this.carritos)
        {
            deudaTotal += carrito.getMontoDeuda();
        }

        return deudaTotal;
    }
    public Boolean estaHabilitado(){
        return this.getMontoDeuda() < 20000;
    }

}

class Direccion{
    
    private String calle1;
    private String calle2;
    private int numero;
    private Boolean sinAltura;
    private int piso;
    private int cuerpo;
    private String departamento;
    private Ciudad ciudad;
    private Boolean puedeEnvio;

    public String getDireccion(){
        String dir = this.calle1;
        
        if(this.sinAltura && this.calle2 != null)
        {
            dir += " y " + this.calle2;
        }
        else if(!this.sinAltura)
        {
            dir += " " + this.numero;
        }
        if (this.piso != null) 
        {
        dir += " Piso " + this.piso;
        }
        if (this.departamento != null) 
        {
        dir += " Depto " + this.departamento;
        }
        return dir;
    }

    public String getLatitud(){
        return "0";
    }

    public String getLongitud(){
        return "0";
    }

    public Boolean estaHabilitadoEnvio(){
        return puedeEnvio;
    }
}

enum MarcaTarjeta {
    VISA, MASTERCARD, NARANJA;
}

class Tarjeta{
    private String nombre;
    private MarcaTarjeta tipo;
    private String numero;
    private String ccv;

    public String getUltimos4digitos(){
        return numero.substring(numero.length() - 4);
    }

    public String getNumeroTarjeta(){
        return numero;
    }
}

class Pago{
    private Carrito carritoAsociado;
    private Tarjeta tarjetaAsociada;
    private int monto;

    public Boolean verificarTarjetaAsociada(){
        int suma = 0;
        boolean duplicar = false;
        // Implementación del algoritmo de luhn para verificar si una tarjeta asociada a un pago es válida 

        for (int i = tarjetaAsociada.numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(tarjetaAsociada.numero.charAt(i));

            if (duplicar) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }

            suma += digito;
            duplicar = !duplicar;
        }

        return suma % 10 == 0;
    }
}

class Ciudad {
    private String nombre;
    private Provincia provincia;
}

class Provincia {
    private String nombre;
    private Pais pais;
}

class Pais {
    private String nombre;
}

enum Estado {
    EN_PROCESO, CERRADO;
}
enum Estado {
    EN_PROCESO,
    CERRADO
}

class Carrito {
    private List<Item> items;
    private LocalDateTime fechaCompra;
    private Cliente cliente;
    private Estado estado;
    private Direccion direccionEnvio;
    private Direccion direccionCobro;
    private List<Pago> pagos; 

    public LocalDateTime getFechaCompra() {
        return this.fechaCompra;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void cerrar() {
        this.setEstado(Estado.CERRADO);
    }

    // Suma el precio real de todos los items (con descuentos aplicados)
    public Double getMontoCarrito() {
        double total = 0;
        for (Item item : this.items) {
            total += item.getPrecio();
        }
        return total;
    }

    // Suma solo lo que fue pagado mediante pagos asociados
    public Double getMontoPagado() {
        // Pago no tiene lista en Carrito, pero Carrito puede tener pagos
        // Por ahora retorna 0 — ver nota abajo
        return 0.0;
    }

    public Double getMontoDeuda() {
        return this.getMonto() - this.getMontoPagado();
    }
}
