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

    private String generarCodigo() {
        String nombreAbreviado = nombre.length() > 2 ? nombre.substring(0, 2).toUpperCase() : nombre.toUpperCase();
        String fechaFormatoCorto = fechaVencimiento.replaceAll("-", "").substring(2, 6); // Solo los últimos 4 dígitos del año y mes
        Random random = new Random();
        int secuenciaAleatoria = random.nextInt(90) + 10; // Número aleatorio de 2 dígitos

        return nombreAbreviado + fechaFormatoCorto + secuenciaAleatoria;
    }


    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
