package gestionCitas.modelos;

import gestionPacientes.modelos.Paciente;

public class Cita {
    private String codigo;
    private String fecha;
    private String hora;
    private Medico medicoAsignado;
    private Paciente pacienteAsignado;
    private String diagnostico;
    private String receta;

    public Cita(String codigo, String fecha, String hora, Medico medicoAsignado, Paciente pacienteAsignado) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.hora = hora;
        this.medicoAsignado = medicoAsignado;
        this.pacienteAsignado = pacienteAsignado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Medico getMedicoAsignado() {
        return medicoAsignado;
    }

    public void setMedicoAsignado(Medico medicoAsignado) {
        this.medicoAsignado = medicoAsignado;
    }

    public Paciente getPacienteAsignado() {
        return pacienteAsignado;
    }

    public void setPacienteAsignado(Paciente pacienteAsignado) {
        this.pacienteAsignado = pacienteAsignado;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }
}
