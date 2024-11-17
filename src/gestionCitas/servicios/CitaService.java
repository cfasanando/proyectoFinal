package gestionCitas.servicios;

import gestionCitas.modelos.Cita;
import gestionCitas.modelos.Medico;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionMedicamentos.modelos.Medicamento;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CitaService {
    private List<Cita> citas;
    private List<Medico> medicos;
    private MedicamentoService medicamentoService;
    private static CitaService instancia;
   
    private CitaService(MedicamentoService medicamentoService) {
        this.citas = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.medicamentoService = medicamentoService;
    }

    public static CitaService getInstance(MedicamentoService medicamentoService) {
        if (instancia == null) {
            instancia = new CitaService(medicamentoService);
        }
        return instancia;
    }
    
    public void registrarReceta(Cita cita, List<Medicamento> medicamentos, List<Integer> cantidades) {
        for (int i = 0; i < medicamentos.size(); i++) {
            Medicamento medicamento = medicamentos.get(i);
            int cantidad = cantidades.get(i);
            if (medicamentoService.reducirStock(medicamento.getCodigo(), cantidad)) {
                cita.registrarReceta("Medicamento: " + medicamento.getNombre() + ", Cantidad: " + cantidad);
                System.out.println("Medicamento " + medicamento.getNombre() + " registrado en la receta.");
            } else {
                System.out.println("No hay suficiente stock de " + medicamento.getNombre() + ".");
            }
        }
    }

    public void registrarMedico(Medico medico) {
        medicos.add(medico);
        System.out.println("Médico " + medico.getNombre() + " registrado con éxito.");
    }

    public Optional<Medico> buscarMedicoPorId(String idMedico) {
        return medicos.stream()
                .filter(medico -> medico.getIdMedico().equals(idMedico))
                .findFirst();
    }

    public void listarMedicos() {
        System.out.println("\n--- Lista de Médicos ---");
        medicos.forEach(medico -> System.out.println("ID: " + medico.getIdMedico() +
                " - Nombre: " + medico.getNombre() + " - Especialidad: " + medico.getEspecialidad() +
                " - Disponible: " + medico.isDisponible()));
    }

    public void actualizarDisponibilidad(String idMedico, boolean disponible) {
        buscarMedicoPorId(idMedico).ifPresent(medico -> medico.setDisponible(disponible));
    }

    public void programarCita(Cita cita) {
        if (cita.getPacienteAsignado() == null) {
            throw new RuntimeException("El paciente asignado a la cita no puede ser nulo.");
        }
        if (cita.getMedicoAsignado() == null) {
            throw new RuntimeException("El médico asignado a la cita no puede ser nulo.");
        }

        if (!cita.getMedicoAsignado().isDisponible()) {
            throw new RuntimeException("El médico " + cita.getMedicoAsignado().getNombre() + " no está disponible.");
        }

        citas.add(cita);
        cita.getMedicoAsignado().setDisponible(false);

        System.out.println("Cita programada para el paciente " + cita.getPacienteAsignado().getNombre() +
                " con el médico " + cita.getMedicoAsignado().getNombre() +
                " el " + cita.getFecha() + " a las " + cita.getHora() + ".");
    }

    public boolean cancelarCita(String fecha, String hora) {
        Optional<Cita> citaOpt = citas.stream()
                .filter(c -> c.getFecha().equals(fecha) && c.getHora().equals(hora))
                .findFirst();

        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            Medico medico = cita.getMedicoAsignado();

            citas.remove(cita);
           
            medico.setDisponible(true);

            System.out.println("Cita cancelada con éxito.");
            return true;
        } else {
            System.out.println("Cita no encontrada.");
            return false;
        }
    }

    public Cita buscarCita(String fecha, String hora) {
        return citas.stream()
                .filter(c -> c.getFecha().equals(fecha) && c.getHora().equals(hora))
                .findFirst()
                .orElse(null);
    }

    public void listarCitas() {
        System.out.println("\n--- Lista de Citas ---");
        citas.forEach(cita -> System.out.println("Fecha: " + cita.getFecha() + " - Hora: " + cita.getHora() +
                " - Paciente: " + cita.getPacienteAsignado().getNombre() +
                " - Médico: " + cita.getMedicoAsignado().getNombre()));
    }
    
    public List<Cita> getCitas() {
        return citas;
    }
   
    public List<Medico> getMedicos() {
        return medicos;
    }
}
