package gestionCitas.ui;

import gestionCitas.modelos.Cita;
import gestionCitas.modelos.Medico;
import gestionCitas.servicios.CitaService;
import gestionMedicamentos.modelos.Medicamento;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.servicios.PacienteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuCitasMedicas {
    private CitaService citaService;
    private PacienteService pacienteService;
    private MedicamentoService medicamentoService;

    public MenuCitasMedicas(CitaService citaService, PacienteService pacienteService, MedicamentoService medicamentoService) {
        this.citaService = citaService;
        this.pacienteService = pacienteService;
        this.medicamentoService = medicamentoService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Programación de Citas Médicas y Gestión de Médicos ---");
            System.out.println("1. Programar Cita");
            System.out.println("2. Cancelar Cita");
            System.out.println("3. Registrar Diagnóstico y Receta");
            System.out.println("4. Listar Citas");
            System.out.println("5. Registrar Médico");
            System.out.println("6. Listar Médicos");
            System.out.println("7. Actualizar Disponibilidad de Médico");
            System.out.println("8. Regresar al Menú Principal");

            System.out.print("Seleccione una opción: ");
            int opcion = -1;

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 8.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    programarCita(scanner);
                    break;
                case 2:
                    cancelarCita(scanner);
                    break;
                case 3:
                    registrarDiagnosticoYReceta(scanner);
                    break;
                case 4:
                    listarCitas();
                    break;
                case 5:
                    registrarMedico(scanner);
                    break;
                case 6:
                    listarMedicos();
                    break;
                case 7:
                    actualizarDisponibilidadMedico(scanner);
                    break;
                case 8:
                    System.out.println("Saliendo del sistema de Citas Médicas.");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void programarCita(Scanner scanner) {
        System.out.print("Ingrese la fecha de la cita (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita (HH:MM): ");
        String hora = scanner.nextLine();
        System.out.print("Ingrese el ID del paciente: ");
        String idPaciente = scanner.nextLine();
        System.out.print("Ingrese el ID del médico: ");
        String idMedico = scanner.nextLine();

        var paciente = pacienteService.buscarPacientePorId(idPaciente);
        var medicoOpt = citaService.buscarMedicoPorId(idMedico);

        if (paciente != null && medicoOpt.isPresent()) {
            Medico medico = medicoOpt.get();
            if (medico.isDisponible()) {
                Cita cita = new Cita(fecha, hora, medico, paciente);
                citaService.programarCita(cita);
                pacienteService.agregarCitaAlHistorialDePaciente(idPaciente, cita);
                medico.setDisponible(false);
            } else {
                System.out.println("El médico no está disponible en este momento.");
            }
        } else {
            System.out.println("Paciente o Médico no encontrado.");
        }
    }

    private void cancelarCita(Scanner scanner) {
        System.out.print("Ingrese la fecha de la cita a cancelar (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita a cancelar (HH:MM): ");
        String hora = scanner.nextLine();

        if (citaService.cancelarCita(fecha, hora)) {
            System.out.println("Cita cancelada con éxito.");
        } else {
            System.out.println("Cita no encontrada.");
        }
    }

    private void registrarDiagnosticoYReceta(Scanner scanner) {
        System.out.print("Ingrese la fecha de la cita (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita (HH:MM): ");
        String hora = scanner.nextLine();

        Cita cita = citaService.buscarCita(fecha, hora);

        if (cita != null) {
            System.out.print("Ingrese el diagnóstico: ");
            String diagnostico = scanner.nextLine();
            cita.registrarDiagnostico(diagnostico);

            List<Medicamento> medicamentos = new ArrayList<>();
            List<Integer> cantidades = new ArrayList<>();

            while (true) {
                System.out.print("Ingrese el código del medicamento (o 'fin' para terminar): ");
                String codigo = scanner.nextLine();
                if (codigo.equalsIgnoreCase("fin")) break;

                Medicamento medicamento = medicamentoService.buscarPorCodigo(codigo);
                if (medicamento != null) {
                    System.out.print("Ingrese la cantidad de " + medicamento.getNombre() + ": ");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();

                    medicamentos.add(medicamento);
                    cantidades.add(cantidad);
                } else {
                    System.out.println("Medicamento no encontrado.");
                }
            }
           
            citaService.registrarReceta(cita, medicamentos, cantidades);
            System.out.println("Diagnóstico y receta registrados para el paciente " + cita.getPacienteAsignado().getNombre());
        } else {
            System.out.println("Cita no encontrada.");
        }
    }

    private void listarCitas() {
        citaService.listarCitas();
    }

    private void registrarMedico(Scanner scanner) {
        System.out.print("Ingrese el ID del médico: ");
        String idMedico = scanner.nextLine();
        System.out.print("Ingrese el nombre del médico: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la especialidad del médico: ");
        String especialidad = scanner.nextLine();

        Medico medico = new Medico(idMedico, nombre, especialidad);
        citaService.registrarMedico(medico);
    }

    private void listarMedicos() {
        citaService.listarMedicos();
    }

    private void actualizarDisponibilidadMedico(Scanner scanner) {
        System.out.print("Ingrese el ID del médico: ");
        String idMedico = scanner.nextLine();
        System.out.print("¿Está disponible? (true/false): ");
        boolean disponible = scanner.nextBoolean();
        scanner.nextLine();

        citaService.actualizarDisponibilidad(idMedico, disponible);
        System.out.println("Disponibilidad actualizada.");
    }
}