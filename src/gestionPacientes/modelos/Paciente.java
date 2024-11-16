package gestionPacientes.modelos;

import gestionPacientes.interfaces.HistorialPaciente;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends PacienteBase implements HistorialPaciente {
    private List<String> historialDiagnosticos;
    private List<String> historialRecetas;

    public Paciente(String nombre, int edad, String idPaciente) {
        super(nombre, edad, idPaciente);
        this.historialDiagnosticos = new ArrayList<>();
        this.historialRecetas = new ArrayList<>();
    }

    @Override
    public void agregarDiagnostico(String diagnostico) {
        historialDiagnosticos.add(diagnostico);
        System.out.println("Diagnóstico agregado para el paciente " + nombre);
    }

    @Override
    public void agregarReceta(String receta) {
        historialRecetas.add(receta);
        System.out.println("Receta agregada para el paciente " + nombre);
    }

    @Override
    public void verHistorialCompleto() {
        System.out.println("Historial de Diagnósticos del paciente " + nombre + ":");
        historialDiagnosticos.forEach(System.out::println);

        System.out.println("\nHistorial de Recetas:");
        historialRecetas.forEach(System.out::println);
    }

    @Override
    public void actualizarDatos(String nuevoNombre, int nuevaEdad) {
        this.nombre = nuevoNombre;
        this.edad = nuevaEdad;
        System.out.println("Datos del paciente actualizados: " + nombre);
    }

    public String getIdPaciente() {
        return idPaciente;
    }
}
