package gestionCitas.modelos;

public class Medico {
    private String codigo;
    private String nombre;
    private String especialidad;
    private boolean disponible;

    public Medico(String codigo, String nombre, String especialidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.disponible = true;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
