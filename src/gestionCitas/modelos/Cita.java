package gestionCitas.modelos;

import gestionPacientes.modelos.Paciente;

public class Cita {
    private String fecha;
    private String hora;
    private Medico medicoAsignado;
    private Paciente pacienteAsignado;
    private String diagnostico;
    private String receta;

    public Cita(String fecha, String hora, Medico medicoAsignado, Paciente pacienteAsignado) {
        this.fecha = fecha;
        this.hora = hora;
        this.medicoAsignado = medicoAsignado;
        this.pacienteAsignado = pacienteAsignado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public Medico getMedicoAsignado() {
        return medicoAsignado;
    }

    public Paciente getPacienteAsignado() {
        return pacienteAsignado;
    }

    public void registrarDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
        pacienteAsignado.agregarDiagnostico(diagnostico);
    }

    public void registrarReceta(String receta) {
        this.receta = receta;
        pacienteAsignado.agregarReceta(receta);
    }
}
