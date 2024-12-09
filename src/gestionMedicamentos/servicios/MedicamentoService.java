package gestionMedicamentos.servicios;

import gestionMedicamentos.dao.MedicamentoDAO;
import gestionMedicamentos.modelos.Medicamento;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MedicamentoService {
    private MedicamentoDAO medicamentoDAO = new MedicamentoDAO();

    public void agregarMedicamento(Medicamento medicamento) {
        medicamentoDAO.insertarMedicamento(medicamento);
    }

    public List<Medicamento> listarMedicamentos() {
        return medicamentoDAO.obtenerMedicamentos();
    }

    public void actualizarMedicamento(Medicamento medicamento) {
        medicamentoDAO.actualizarMedicamento(medicamento);
    }

    public void eliminarMedicamento(String codigo) {
        medicamentoDAO.eliminarMedicamento(codigo);
    }

    public void verificarStock(String codigo) {
        int stock = medicamentoDAO.obtenerStock(codigo);

        if (stock == -1) {
            System.out.println("El codigo de medicamento no existe.");
            return;
        }

        if (stock > 20) {
            System.out.println("Conforme: Stock suficiente. " + stock + " unidades disponibles.");
        } else if (stock >= 10) {
            System.out.println("Advertencia: Stock moderado. " + stock + " unidades. Considere reabastecer pronto.");
        } else if (stock >= 2) {
            System.out.println("Atencion: Stock bajo. " + stock + " unidades. Es necesario reabastecer.");
        } else if (stock == 1) {
            System.out.println("Critico: Solo queda 1 unidad.");
        } else {
            System.out.println("Sin stock: El medicamento esta agotado.");
        }
    }

    public void verificarFechaVencimiento(String codigo) {
        String fechaVencimientoStr = medicamentoDAO.obtenerFechaVencimiento(codigo);

        if (fechaVencimientoStr == null) {
            System.out.println("El codigo de medicamento no existe.");
            return;
        }

        LocalDate fechaVencimiento = LocalDate.parse(fechaVencimientoStr);
        LocalDate hoy = LocalDate.now();
        long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaVencimiento);

        if (diasRestantes > 60) {
            System.out.println("Conforme: El medicamento vence en " + diasRestantes + " dias.");
        } else if (diasRestantes >= 30) {
            System.out.println("Advertencia: El medicamento vence en " + diasRestantes + " dias. Considere usarlo pronto.");
        } else if (diasRestantes >= 0) {
            System.out.println("Atencion: El medicamento vence en " + diasRestantes + " dias.");
        } else {
            System.out.println("Vencido: El medicamento esta vencido desde hace " + Math.abs(diasRestantes) + " dias.");
        }
    }
    
    // Método para actualizar el stock del medicamento
    public boolean actualizarStock(String codigoMedicamento, int nuevaCantidad) {
        return medicamentoDAO.actualizarStock(codigoMedicamento, nuevaCantidad);
    }
    
    public boolean existeMedicamento(String codigoMedicamento) {
        return medicamentoDAO.buscarMedicamentoPorCodigo(codigoMedicamento) != null;
    }

    public int obtenerStock(String codigoMedicamento) {
        return medicamentoDAO.obtenerStock(codigoMedicamento);
    }

    public List<Medicamento> buscarMedicamentos(String criterio) {
        return medicamentoDAO.buscarMedicamentos(criterio);
    }
}
