package SaludVital;

import gestionCitas.servicios.CitaService;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionMedicamentos.ui.MenuMedicamentos;
import gestionPacientes.ui.MenuPacientes;
import gestionCitas.ui.MenuCitasMedicas;
import gestionPacientes.servicios.PacienteService;
import gestionReportes.ui.MenuReportesEstadisticas;

import java.util.Scanner;

public class SaludVital {

    public static void main(String[] args) {
        mostrarLogo();
        mostrarMenuPrincipal();
    }

    private static void mostrarLogo() {
        System.out.println("==================================================");
        System.out.println("                                                  ");
        System.out.println("           Bienvenido a                          ");
        System.out.println("               SALUDVITAL                       ");
        System.out.println("                                                  ");
        System.out.println("        Sistema de Gestion Clinica               ");
        System.out.println("==================================================\n");
    }

    private static void mostrarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("==================================================");
            System.out.println("               Menu Principal                    ");
            System.out.println("==================================================");
            System.out.println(" Seleccione el modulo con el que desea trabajar:");
            System.out.println(" [1] Gestion de Medicamentos");
            System.out.println(" [2] Gestion de Pacientes y su Historial Medico");
            System.out.println(" [3] Programacion de Citas Medicas");
            System.out.println(" [4] Reportes y Estadisticas");
            System.out.println(" [5] Salir");

            System.out.print("\n Ingrese su opcion: ");
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
                    iniciarModuloMedicamentos();
                    break;
                case 2:
                    iniciarModuloPacientes();
                    break;
                case 3:
                    iniciarModuloCitasMedicas();
                    break;
                case 4:
                    iniciarModuloReportesEstadisticas();
                    break;
                case 5:
                    System.out.println("\nGracias por utilizar SaludVital. Hasta pronto!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
            System.out.println("\n--------------------------------------------------\n");
        }
        scanner.close();
    }

    private static void iniciarModuloMedicamentos() {
        MedicamentoService medicamentoService = MedicamentoService.getInstance();
        MenuMedicamentos menu = new MenuMedicamentos(medicamentoService);
        menu.mostrarMenu();
    }

    private static void iniciarModuloPacientes() {
        PacienteService pacienteService = PacienteService.getInstance();
        MenuPacientes menu = new MenuPacientes(pacienteService);
        menu.mostrarMenu();
    }

    private static void iniciarModuloCitasMedicas() {
        MedicamentoService medicamentoService = MedicamentoService.getInstance();       
        CitaService citaService = CitaService.getInstance(medicamentoService);
        PacienteService pacienteService = PacienteService.getInstance();              
        MenuCitasMedicas menu = new MenuCitasMedicas(citaService, pacienteService, medicamentoService);
        menu.mostrarMenu();
    }

    private static void iniciarModuloReportesEstadisticas() {
        MedicamentoService medicamentoService = MedicamentoService.getInstance();       
        CitaService citaService = CitaService.getInstance(medicamentoService);
        PacienteService pacienteService = PacienteService.getInstance();    
        MenuReportesEstadisticas menu = new MenuReportesEstadisticas(citaService, pacienteService, medicamentoService);
        menu.mostrarMenu();
    }

}
