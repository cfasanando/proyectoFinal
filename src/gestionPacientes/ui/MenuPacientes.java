package gestionPacientes.ui;

import gestionPacientes.modelos.Paciente;
import gestionPacientes.servicios.PacienteService;
import java.util.Scanner;

public class MenuPacientes {

    private PacienteService pacienteService;

    public MenuPacientes(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Gestión de Pacientes ---");
            System.out.println("1. Agregar Paciente");
            System.out.println("2. Buscar Paciente por ID");
            System.out.println("3. Ver Historial Médico de Paciente");
            System.out.println("4. Listar Pacientes");
            System.out.println("5. Regresar al Menú Principal");

            System.out.print("Seleccione una opción: ");
            int opcion = -1;

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 5.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nombre del paciente: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese edad del paciente: ");
                    int edad = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese ID del paciente: ");
                    String idPaciente = scanner.nextLine();
                    pacienteService.agregarPaciente(new Paciente(nombre, edad, idPaciente));
                    break;
                case 2:
                    System.out.print("Ingrese ID del paciente: ");
                    String idBuscar = scanner.nextLine();
                    Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(idBuscar);
                    if (pacienteEncontrado != null) {
                        System.out.println("Paciente encontrado: " + pacienteEncontrado.getNombre());
                    } else {
                        System.out.println("Paciente no encontrado.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese ID del paciente para ver historial médico: ");
                    String idHistorial = scanner.nextLine();
                    Paciente pacienteHistorial = pacienteService.buscarPacientePorId(idHistorial);
                    if (pacienteHistorial != null) {
                        pacienteHistorial.verHistorialCompleto();
                    } else {
                        System.out.println("Paciente no encontrado.");
                    }
                    break;
                case 4:
                    pacienteService.listarPacientes();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema de Gestión de Pacientes.");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
