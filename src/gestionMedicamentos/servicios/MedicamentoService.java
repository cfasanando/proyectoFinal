package gestionMedicamentos.servicios;

import gestionMedicamentos.modelos.Medicamento;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MedicamentoService {

    private Set<Medicamento> medicamentos;
    private static MedicamentoService instancia;

    public MedicamentoService() {
        this.medicamentos = new HashSet<>();
    }
    
    public static MedicamentoService getInstance() {
        if (instancia == null) {
            instancia = new MedicamentoService();
        }
        return instancia;
    }
    
    public boolean reducirStock(String codigo, int cantidad) {
        Medicamento medicamento = buscarPorCodigo(codigo);
        if (medicamento != null && medicamento.getCantidadDisponible() >= cantidad) {
            medicamento.setCantidadDisponible(medicamento.getCantidadDisponible() - cantidad);
            return true;
        }
        return false;
    }

    public boolean agregarMedicamento(Medicamento medicamento) {
        boolean agregado = medicamentos.add(medicamento);
        if (agregado) {
            System.out.println("Medicamento " + medicamento.getNombre() + " agregado al inventario con código " + medicamento.getCodigo());
        } else {
            System.out.println("El medicamento " + medicamento.getNombre() + " ya existe en el inventario.");
        }
        return agregado;
    }

    public boolean eliminarMedicamentoPorCodigo(String codigo) {
        Medicamento medicamento = buscarPorCodigo(codigo);
        if (medicamento != null) {
            medicamentos.remove(medicamento);
            System.out.println("Medicamento con código " + codigo + " eliminado del inventario.");
            return true;
        } else {
            System.out.println("El medicamento con código " + codigo + " no se encuentra en el inventario.");
            return false;
        }
    }

    public Medicamento buscarPorCodigo(String codigo) {
        return medicamentos.stream()
                .filter(m -> m.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public Set<Medicamento> buscarPorCodigoParcial(String prefijo) {
        return medicamentos.stream()
                .filter(m -> m.getCodigo().toLowerCase().startsWith(prefijo.toLowerCase()))
                .collect(Collectors.toSet());
    }

    public void listarMedicamentos() {
        System.out.println("\n--- Lista de Medicamentos ---");
        for (Medicamento medicamento : medicamentos) {
            System.out.println("Código: " + medicamento.getCodigo() + " - Nombre: " + medicamento.getNombre() +
                    " - Cantidad: " + medicamento.getCantidadDisponible() + " - Vencimiento: " + medicamento.getFechaVencimiento());
        }
    }

    public Set<Medicamento> getMedicamentos() {
        return medicamentos;
    }
}
