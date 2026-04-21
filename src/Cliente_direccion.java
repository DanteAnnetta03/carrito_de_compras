import java.util.List;

class Cliente{

    private String nombre;
    private String apellido;
    private String email;
    private List <Direccion> direcciones;
    private List <Carrito> carritos;
    private List <Tarjeta> tarjeta;
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
    private Int numero;
    private Boolean sinAltura;
    private Int piso;
    private Int cuerpo;
    private String departamento;
    private Ciudad ciudad;

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
        return true;
    }
}