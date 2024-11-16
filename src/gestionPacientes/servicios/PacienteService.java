package gestionPacientes.servicios;

import gestionCitas.modelos.Cita;
import gestionPacientes.modelos.Paciente;
import java.util.HashSet;
import java.util.Set;

public class PacienteService {
    private Set<Paciente> pacientes;
    private static PacienteService instancia;
   
    private PacienteService() {
        this.pacientes = new HashSet<>();
    }

    public static PacienteService getInstance() {
        if (instancia == null) {
            instancia = new PacienteService();
        }
        return instancia;
    }

    public boolean agregarPaciente(Paciente paciente) {
        boolean agregado = pacientes.add(paciente);
        if (agregado) {
            System.out.println("Paciente " + paciente.getNombre() + " agregado con ID " + paciente.getIdPaciente());
        } else {
            System.out.println("El paciente " + paciente.getNombre() + " ya está registrado.");
        }
        return agregado;
    }

    public Paciente buscarPacientePorId(String idPaciente) {
        return pacientes.stream()
                .filter(p -> p.getIdPaciente().equalsIgnoreCase(idPaciente))
                .findFirst()
                .orElse(null);
    }

    public void listarPacientes() {
        System.out.println("\n--- Lista de Pacientes ---");
        pacientes.forEach(p -> System.out.println("ID: " + p.getIdPaciente() + " - Nombre: " + p.getNombre() + " - Edad: " + p.getEdad()));
    }

    public void agregarCitaAlHistorialDePaciente(String idPaciente, Cita cita) {
        Paciente paciente = buscarPacientePorId(idPaciente);
        if (paciente != null) {
            paciente.getHistorialCitas().add(cita);
            System.out.println("Cita agregada al historial del paciente " + paciente.getNombre());
        } else {
            System.out.println("Paciente con ID " + idPaciente + " no encontrado.");
        }
    }
    
    public Set<Paciente> getPacientes() {
        return pacientes;
    }
}
