package gestionPacientes.interfaces;

public interface HistorialPaciente {
    void agregarDiagnostico(String diagnostico);
    void agregarReceta(String receta);
    void verHistorialCompleto();
}

