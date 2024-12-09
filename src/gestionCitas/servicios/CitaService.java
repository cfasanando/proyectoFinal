package gestionCitas.servicios;

import gestionCitas.dao.CitaDAO;
import gestionCitas.modelos.Cita;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.servicios.PacienteService;
import java.util.List;

public class CitaService {
    private final CitaDAO citaDAO;
    private final MedicamentoService medicamentoService;

    // Constructor con MedicoService, PacienteService y MedicamentoService
    public CitaService(MedicoService medicoService, PacienteService pacienteService, MedicamentoService medicamentoService) {
        this.citaDAO = new CitaDAO(medicoService, pacienteService);
        this.medicamentoService = medicamentoService;
    }

    public List<Cita> obtenerCitas() {
        return citaDAO.obtenerCitas();
    }

    public Cita buscarCitaPorCodigo(String codigoCita) {
        return citaDAO.buscarCitaPorCodigo(codigoCita);
    }

    public void programarCita(Cita cita) {
        boolean exito = citaDAO.insertarCita(cita);
        if (exito) {
            System.out.println("Cita programada correctamente.");
        } else {
            System.out.println("Error al programar la cita.");
        }
    }

    public void cancelarCita(String codigoCita) {
        if (!citaDAO.existeCita(codigoCita)) {
            System.out.println("La cita con el código ingresado no existe. No se puede cancelar.");
            return;
        }

        boolean exito = citaDAO.eliminarCita(codigoCita);
        if (exito) {
            System.out.println("Cita cancelada con éxito.");
        } else {
            System.out.println("No se pudo cancelar la cita. Verifique el código proporcionado.");
        }
    }
    
    public boolean existeCita(String codigoCita) {
        return citaDAO.existeCita(codigoCita);
    }

    public void actualizarCita(Cita cita) {
        boolean exito = citaDAO.actualizarCita(cita);
        if (exito) {
            System.out.println("Cita actualizada correctamente.");
        } else {
            System.out.println("Error al actualizar la cita.");
        }
    }

    public void listarCitas() {
        List<Cita> citas = citaDAO.obtenerCitas();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            System.out.println("\n--- Lista de Citas ---");
            for (Cita cita : citas) {
                System.out.println("ID: " + cita.getCodigo() +
                        " - Fecha: " + cita.getFecha() +
                        " - Hora: " + cita.getHora() +
                        " - Paciente: " + cita.getPacienteAsignado().getNombre() +
                        " - Médico: " + cita.getMedicoAsignado().getNombre());
            }
        }
    }
    
    public boolean tieneDiagnostico(String codigoCita) {
        Cita cita = citaDAO.buscarCitaPorCodigo(codigoCita);
        return cita != null && cita.getDiagnostico() != null && !cita.getDiagnostico().isEmpty();
    }    

    public boolean registrarDiagnosticoYReceta(String codigoCita, String diagnostico, String codigoMedicamento, int cantidad, boolean actualizarDiagnostico) {
        // Validar que la cita existe
        Cita cita = citaDAO.buscarCitaPorCodigo(codigoCita);
        if (cita == null) {
            System.out.println("La cita con el código ingresado no existe.");
            return false;
        }

        // Validar que el medicamento existe
        if (!medicamentoService.existeMedicamento(codigoMedicamento)) {
            System.out.println("El medicamento con el código ingresado no existe.");
            return false;
        }

        // Validar que hay suficiente stock del medicamento
        int stockDisponible = medicamentoService.obtenerStock(codigoMedicamento);
        if (stockDisponible < cantidad) {
            System.out.println("No hay suficiente stock del medicamento. Stock disponible: " + stockDisponible);
            return false;
        }

        // Actualizar el stock del medicamento
        boolean stockActualizado = medicamentoService.actualizarStock(codigoMedicamento, stockDisponible - cantidad);
        if (!stockActualizado) {
            System.out.println("Error al actualizar el stock del medicamento.");
            return false;
        }

        // Registrar o actualizar el diagnóstico
        if (actualizarDiagnostico || !tieneDiagnostico(codigoCita)) {
            if (!citaDAO.actualizarDiagnostico(codigoCita, diagnostico)) {
                System.out.println("Error al registrar el diagnóstico.");
                return false;
            }
        }

        // Registrar o actualizar la receta
        if (!citaDAO.registrarOActualizarReceta(codigoCita, codigoMedicamento, cantidad)) {
            System.out.println("Error al registrar la receta.");
            return false;
        }

        System.out.println("Diagnóstico y receta registrados correctamente.");
        return true;
    }

}
