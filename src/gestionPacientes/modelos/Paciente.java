package gestionPacientes.modelos;

public class Paciente extends PacienteBase {

    public Paciente(String codigo, int edad, String nombre) {
        super(nombre, edad, codigo);
    }
}
