package gestionPacientes.servicios;

import gestionPacientes.dao.PacienteDAO;
import gestionPacientes.modelos.Paciente;

import java.util.List;

public class PacienteService {
    private PacienteDAO pacienteDAO;

    public PacienteService() {
        this.pacienteDAO = new PacienteDAO();
    }

    // Agregar paciente
    public boolean agregarPaciente(Paciente paciente) {
        return pacienteDAO.insertarPaciente(paciente);
    }

    // Actualizar paciente
    public boolean actualizarPaciente(Paciente paciente) {
        return pacienteDAO.actualizarPaciente(paciente);
    }

    // Eliminar paciente
    public boolean eliminarPaciente(String codigo) {
        return pacienteDAO.eliminarPaciente(codigo);
    }
    
    // Buscar paciente por código
    public Paciente buscarPacientePorCodigo(String codigo) {
        return pacienteDAO.buscarPacientePorCodigo(codigo);
    }

    public List<Paciente> buscarPacientesPorCriterio(String criterio) {
        return pacienteDAO.buscarPacientesPorCriterio(criterio);
    }

    // Listar todos los pacientes
    public void listarPacientes() {
        List<Paciente> pacientes = pacienteDAO.obtenerPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
        } else {
            System.out.println("\n--- Lista de Pacientes ---");
            for (Paciente paciente : pacientes) {
                System.out.println("Código: " + paciente.getCodigoPaciente() +
                        " - Nombre: " + paciente.getNombre() +
                        " - Edad: " + paciente.getEdad());
            }
        }
    }
    
    public List<String> obtenerHistorialMedico(String codigoPaciente) {
        return pacienteDAO.obtenerHistorialMedico(codigoPaciente);
    }

    // Obtener lista de pacientes
    public List<Paciente> obtenerPacientes() {
        return pacienteDAO.obtenerPacientes();
    }
}
