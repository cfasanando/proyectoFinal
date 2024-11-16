package gestionPacientes.modelos;

import gestionCitas.modelos.Cita;
import java.util.ArrayList;
import java.util.List;

public abstract class PacienteBase {
    protected String nombre;
    protected int edad;
    protected String idPaciente;
    protected List<Cita> historialCitas;

    public PacienteBase(String nombre, int edad, String idPaciente) {
        this.nombre = nombre;
        this.edad = edad;
        this.idPaciente = idPaciente;
        this.historialCitas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getIdPaciente() {
        return idPaciente;
    }
    
    public List<Cita> getHistorialCitas() {
        return historialCitas;
    }

    public abstract void actualizarDatos(String nuevoNombre, int nuevaEdad);
}
