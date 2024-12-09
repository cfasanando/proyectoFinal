package SaludVital;

import gestionCitas.servicios.CitaService;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionMedicamentos.ui.MenuMedicamentos;
import gestionPacientes.ui.MenuPacientes;
import gestionCitas.ui.MenuCitasMedicas;
import gestionPacientes.servicios.PacienteService;
import gestionReportes.servicios.ReporteService;
import gestionReportes.ui.MenuReportesEstadisticas;
import conexion.ConexionDB;
import gestionCitas.servicios.MedicoService;

import java.util.Scanner;

public class SaludVital {

    public static void main(String[] args) {
        mostrarLogo();
        mostrarMenuPrincipal();

        // Cerrar la conexión al salir del sistema
        ConexionDB.closeConnection();
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
                System.out.println("Entrada no valida. Por favor, ingrese un numero entre 1 y 5.");
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
        MedicamentoService medicamentoService = new MedicamentoService();
        MenuMedicamentos menuMedicamentos = new MenuMedicamentos(medicamentoService);
        menuMedicamentos.mostrarMenu();
    }

    private static void iniciarModuloPacientes() {        
        PacienteService pacienteService = new PacienteService();        
        MenuPacientes menuPacientes = new MenuPacientes(pacienteService);      
        menuPacientes.mostrarMenu();
    }

    private static void iniciarModuloCitasMedicas() {
        MedicoService medicoService = new MedicoService();
        PacienteService pacienteService = new PacienteService();
        MedicamentoService medicamentoService = new MedicamentoService();
        CitaService citaService = new CitaService(medicoService, pacienteService, medicamentoService);
        MenuCitasMedicas menu = new MenuCitasMedicas(citaService, medicoService, pacienteService, medicamentoService);
        menu.mostrarMenu();
    }

    private static void iniciarModuloReportesEstadisticas() {
        ReporteService reporteService = new ReporteService();        
        MenuReportesEstadisticas menuReportesEstadisticas = new MenuReportesEstadisticas(reporteService);      
        menuReportesEstadisticas.mostrarMenu();
    }
}
