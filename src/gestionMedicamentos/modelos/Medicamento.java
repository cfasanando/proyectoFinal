package gestionMedicamentos.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Medicamento extends MedicamentoBase {

    public Medicamento(String nombre, int cantidadDisponible, String fechaVencimiento) {
        super(nombre, cantidadDisponible, fechaVencimiento);
    }

    @Override
    public void agregarMedicamento(int cantidad) {
        this.cantidadDisponible += cantidad;
        System.out.println("Medicamento agregado: " + nombre + ". Cantidad total: " + cantidadDisponible);
    }

    @Override
    public void actualizarMedicamento(String nuevoNombre, int nuevaCantidad, String nuevaFechaVencimiento) {
        this.nombre = nuevoNombre;
        this.cantidadDisponible = nuevaCantidad;
        this.fechaVencimiento = nuevaFechaVencimiento;
        this.codigo = generarCodigo();
        System.out.println("Medicamento actualizado: " + nombre);
    }

    @Override
    public void eliminarMedicamento() {
        System.out.println("Medicamento " + nombre + " eliminado.");
        this.nombre = null;
        this.cantidadDisponible = 0;
        this.fechaVencimiento = null;
    }

    @Override
    public void verificarStock() {
        System.out.println("El stock actual del medicamento " + nombre + " es de " + cantidadDisponible + " unidades.");
        if (cantidadDisponible < 10) {
            System.out.println("Alerta: Stock bajo para el medicamento " + nombre + ". Por favor, considere reabastecer.");
        }
    }

    @Override
    public void verificarFechaVencimiento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate fechaVencimientoLocalDate = LocalDate.parse(fechaVencimiento, formatter);
            LocalDate fechaActual = LocalDate.now();
            long diasRestantes = ChronoUnit.DAYS.between(fechaActual, fechaVencimientoLocalDate);

            if (diasRestantes < 0) {
                System.out.println("Alerta: El medicamento " + nombre + " ya está vencido.");
            } else if (diasRestantes <= 30) {
                System.out.println("Alerta: El medicamento " + nombre + " está próximo a vencer en " + diasRestantes + " días.");
            } else {
                System.out.println("El medicamento " + nombre + " está en buen estado. Quedan " + diasRestantes + " días para su vencimiento.");
            }
        } catch (Exception e) {
            System.out.println("Error al verificar la fecha de vencimiento para " + nombre + ": Formato de fecha incorrecto.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
