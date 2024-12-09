package gestionMedicamentos.modelos;

public class Medicamento extends MedicamentoBase {

    public Medicamento(String nombre, int cantidadDisponible, String fechaVencimiento) {
        super(nombre, cantidadDisponible, fechaVencimiento);
    }

    public Medicamento(String codigo, String nombre, int cantidadDisponible, String fechaVencimiento) {
        super(nombre, cantidadDisponible, fechaVencimiento);
        this.codigo = codigo; // Sobrescribir el código generado con el código proporcionado
    }

    @Override
    public void verificarStock() {
        if (cantidadDisponible < 10) {
            System.out.println("Alerta: Stock bajo para el medicamento " + nombre + ".");
        } else {
            System.out.println("Stock suficiente para el medicamento " + nombre + ": " + cantidadDisponible + " unidades.");
        }
    }

    @Override
    public void verificarFechaVencimiento() {
        System.out.println("Fecha de vencimiento del medicamento " + nombre + ": " + fechaVencimiento);
    }
}
