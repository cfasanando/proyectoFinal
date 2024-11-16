package gestionCitas.modelos;

public class Medico {
    private String idMedico;
    private String nombre;
    private String especialidad;
    private boolean disponible;

    public Medico(String idMedico, String nombre, String especialidad) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponible = true;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
