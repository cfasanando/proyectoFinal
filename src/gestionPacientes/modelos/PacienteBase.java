package gestionPacientes.modelos;

import gestionCitas.modelos.Cita;
import java.util.ArrayList;
import java.util.List;

public class PacienteBase {
    protected String nombre;
    protected int edad;
    protected String genero;
    protected String codigoPaciente;
    protected List<Cita> historialCitas;
    protected List<String> historialDiagnosticos;
    protected List<String> historialRecetas;

    public PacienteBase(String nombre, int edad, String codigoPaciente) {
        this.nombre = nombre;
        this.edad = edad;
        this.codigoPaciente = codigoPaciente;
        this.historialCitas = new ArrayList<>();
        this.historialDiagnosticos = new ArrayList<>();
        this.historialRecetas = new ArrayList<>();
    }

    // Getter para el nombre del paciente
    public String getNombre() {
        return nombre;
    }

    // Setter para el nombre del paciente
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para la edad del paciente
    public int getEdad() {
        return edad;
    }

    // Setter para la edad del paciente
    public void setEdad(int edad) {
        this.edad = edad;
    }

    // Getter para el código del paciente
    public String getCodigoPaciente() {
        return codigoPaciente;
    }

    // Setter para el código del paciente
    public void setCodigoPaciente(String codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }
    
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Getter para el historial de citas
    public List<Cita> getHistorialCitas() {
        return historialCitas;
    }

    // Método para ver el historial completo
    public void verHistorialCompleto() {
        System.out.println("\n--- Historial Completo del Paciente: " + nombre + " ---");

        System.out.println("\nDiagnosticos:");
        if (historialDiagnosticos.isEmpty()) {
            System.out.println("No hay diagnosticos registrados.");
        } else {
            historialDiagnosticos.forEach(diagnostico -> System.out.println("- " + diagnostico));
        }

        System.out.println("\nRecetas:");
        if (historialRecetas.isEmpty()) {
            System.out.println("No hay recetas registradas.");
        } else {
            historialRecetas.forEach(receta -> System.out.println("- " + receta));
        }

        System.out.println("\nCitas:");
        if (historialCitas.isEmpty()) {
            System.out.println("No hay citas registradas.");
        } else {
            historialCitas.forEach(cita -> System.out.println("- Cita: " + cita.getFecha() + " " + cita.getHora()));
        }
    }
}
