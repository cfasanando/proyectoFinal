package gestionReportes.ui;

import gestionCitas.servicios.CitaService;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.servicios.PacienteService;
import gestionCitas.modelos.Cita;
import gestionPacientes.modelos.Paciente;
import gestionMedicamentos.modelos.Medicamento;
import gestionCitas.modelos.Medico;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MenuReportesEstadisticas {

    private final CitaService citaService;
    private final PacienteService pacienteService;
    private final MedicamentoService medicamentoService;

    public MenuReportesEstadisticas(CitaService citaService, PacienteService pacienteService, MedicamentoService medicamentoService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.medicamentoService = medicamentoService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Reportes y Estadisticas ---");
            System.out.println("1. Reporte de Pacientes");
            System.out.println("2. Reporte de Citas");
            System.out.println("3. Reporte de Medicos");
            System.out.println("4. Reporte de Medicamentos");
            System.out.println("5. Resumen Estadistico");
            System.out.println("6. Regresar al Menu Principal");

            System.out.print("Seleccione una opcion: ");
            int opcion = -1;

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 6.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    generarReportePacientes();
                    break;
                case 2:
                    generarReporteCitas();
                    break;
                case 3:
                    generarReporteMedicos();
                    break;
                case 4:
                    generarReporteMedicamentos();
                    break;
                case 5:
                    mostrarResumenEstadistico();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Regresando al Menu Principal...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
            System.out.println("\n--------------------------------------------------\n");
        }
    }

    private void generarReportePacientes() {
        System.out.println("\n--- Reporte de Pacientes ---");
        Set<Paciente> pacientes = pacienteService.getPacientes();
        System.out.printf("%-15s %-20s %-10s\n", "ID", "Nombre", "Edad");
        System.out.println("--------------------------------------------------");
        for (Paciente paciente : pacientes) {
            System.out.printf("%-15s %-20s %-10d\n", paciente.getIdPaciente(), paciente.getNombre(), paciente.getEdad());
        }
    }

    private void generarReporteCitas() {
        System.out.println("\n--- Reporte de Citas ---");
        List<Cita> citas = citaService.getCitas();
        System.out.printf("%-15s %-10s %-20s %-20s\n", "Fecha", "Hora", "Paciente", "Medico");
        System.out.println("--------------------------------------------------");
        for (Cita cita : citas) {
            System.out.printf("%-15s %-10s %-20s %-20s\n", 
                cita.getFecha(), cita.getHora(), 
                cita.getPacienteAsignado().getNombre(), 
                cita.getMedicoAsignado().getNombre());
        }
    }

    private void generarReporteMedicos() {
        System.out.println("\n--- Reporte de Medicos ---");
        List<Medico> medicos = citaService.getMedicos();
        System.out.printf("%-15s %-20s %-20s %-15s\n", "ID", "Nombre", "Especialidad", "Disponibilidad");
        System.out.println("--------------------------------------------------");
        for (Medico medico : medicos) {
            System.out.printf("%-15s %-20s %-20s %-15s\n", 
                medico.getIdMedico(), medico.getNombre(), 
                medico.getEspecialidad(), medico.isDisponible() ? "Disponible" : "Ocupado");
        }
    }

    private void generarReporteMedicamentos() {
        System.out.println("\n--- Reporte de Medicamentos ---");
        Set<Medicamento> medicamentos = medicamentoService.getMedicamentos();
        System.out.printf("%-15s %-20s %-10s %-15s\n", "Codigo", "Nombre", "Cantidad", "Fecha Vencimiento");
        System.out.println("--------------------------------------------------");
        for (Medicamento medicamento : medicamentos) {
            System.out.printf("%-15s %-20s %-10d %-15s\n", 
                medicamento.getCodigo(), medicamento.getNombre(), 
                medicamento.getCantidadDisponible(), medicamento.getFechaVencimiento());
        }
    }

    private void mostrarResumenEstadistico() {
        System.out.println("\n--- Resumen Estadistico ---");
        int totalPacientes = pacienteService.getPacientes().size();
        int totalCitas = citaService.getCitas().size();
        int totalMedicos = citaService.getMedicos().size();
        int totalMedicamentos = medicamentoService.getMedicamentos().size();
        int citasPorPaciente = totalCitas / Math.max(totalPacientes, 1);

        System.out.println("Total de Pacientes: " + totalPacientes);
        System.out.println("Total de Citas: " + totalCitas);
        System.out.println("Total de Medicos: " + totalMedicos);
        System.out.println("Total de Medicamentos: " + totalMedicamentos);
        System.out.println("Promedio de Citas por Paciente: " + citasPorPaciente);
    }
}
