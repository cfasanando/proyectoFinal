package gestionMedicamentos.modelos;

import gestionMedicamentos.interfaces.MedicamentoAlerta;
import java.util.Random;

public abstract class MedicamentoBase implements MedicamentoAlerta {
    protected String codigo;
    protected String nombre;
    protected int cantidadDisponible;
    protected String fechaVencimiento;

    public MedicamentoBase(String nombre, int cantidadDisponible, String fechaVencimiento) {
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.fechaVencimiento = fechaVencimiento;
        this.codigo = generarCodigo();
    }

    protected String generarCodigo() {
        String nombreAbreviado = nombre.length() > 3 ? nombre.substring(0, 3).toUpperCase() : nombre.toUpperCase();
        
        String fechaFormatoCorto = fechaVencimiento.replaceAll("-", "").substring(2);
        
        Random random = new Random();
        int secuenciaAleatoria = random.nextInt(900) + 100;

        return nombreAbreviado + fechaFormatoCorto + "-" + secuenciaAleatoria;
    }

    public abstract void agregarMedicamento(int cantidad);
    public abstract void actualizarMedicamento(String nuevoNombre, int nuevaCantidad, String nuevaFechaVencimiento);
    public abstract void eliminarMedicamento();

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}
