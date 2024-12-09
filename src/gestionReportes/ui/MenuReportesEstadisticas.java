package gestionReportes.ui;

import gestionReportes.servicios.ReporteService;

import java.util.List;
import java.util.Scanner;

public class MenuReportesEstadisticas {

    private final ReporteService reporteService;

    public MenuReportesEstadisticas(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Reportes y Estadisticas HECHO POR CHRISTIAN ---");
            System.out.println("1. Top 5 Medicamentos Mas Recetados");
            System.out.println("2. Cantidad de Citas por Medico");
            System.out.println("3. Pacientes con Más Citas");
            System.out.println("4. Medicamentos con Stock Bajo");
            System.out.println("5. Resumen General de Citas");
            System.out.println("6. Regresar al Menu Principal");

            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarTopMedicamentosMasRecetados();
                    break;
                case 2:
                    mostrarCitasPorMedico();
                    break;
                case 3:
                    mostrarPacientesConMasCitas();
                    break;
                case 4:
                    mostrarMedicamentosConStockBajo(scanner);
                    break;
                case 5:
                    mostrarResumenGeneralCitas();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Regresando al Menu Principal...");
                    break;
                default:
                    System.out.println("Opcion no válida. Intente nuevamente.");
            }
            System.out.println("\n--------------------------------------------------\n");
        }
    }

    private void mostrarTopMedicamentosMasRecetados() {
        System.out.println("\n--- Top 5 Medicamentos Mas Recetados ---");
        List<String[]> medicamentos = reporteService.obtenerTopMedicamentosMasRecetados();
        System.out.printf("%-20s %-15s\n", "Medicamento", "Total Recetado");
        System.out.println("---------------------------------------------");
        for (String[] medicamento : medicamentos) {
            System.out.printf("%-20s %-15s\n", medicamento[0], medicamento[1]);
        }
    }

    private void mostrarCitasPorMedico() {
        System.out.println("\n--- Cantidad de Citas por Medico ---");
        List<String[]> citasPorMedico = reporteService.obtenerCitasPorMedico();
        System.out.printf("%-20s %-20s\n", "Medico", "Cantidad de Citas");
        System.out.println("---------------------------------------------");
        for (String[] medico : citasPorMedico) {
            System.out.printf("%-20s %-20s\n", medico[0], medico[1]);
        }
    }

    private void mostrarPacientesConMasCitas() {
        System.out.println("\n--- Pacientes con Mas Citas ---");
        List<String[]> pacientes = reporteService.obtenerPacientesConMasCitas();
        System.out.printf("%-20s %-15s\n", "Paciente", "Total de Citas");
        System.out.println("---------------------------------------------");
        for (String[] paciente : pacientes) {
            System.out.printf("%-20s %-15s\n", paciente[0], paciente[1]);
        }
    }

    private void mostrarMedicamentosConStockBajo(Scanner scanner) {
        System.out.print("Ingrese el limite de stock para considerar como bajo: ");
        int limiteStock = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        System.out.println("\n--- Medicamentos con Stock Bajo ---");
        List<String[]> medicamentos = reporteService.obtenerMedicamentosConStockBajo(limiteStock);
        System.out.printf("%-20s %-15s\n", "Medicamento", "Stock Disponible");
        System.out.println("---------------------------------------------");
        for (String[] medicamento : medicamentos) {
            System.out.printf("%-20s %-15s\n", medicamento[0], medicamento[1]);
        }
    }

    private void mostrarResumenGeneralCitas() {
        System.out.println("\n--- Resumen General de Citas ---");
        String[] resumen = reporteService.obtenerResumenGeneralCitas();
        System.out.printf("%-30s %s\n", "Total de Citas:", resumen[0]);
        System.out.printf("%-30s %s\n", "Total de Diagnosticos Registrados:", resumen[1]);
        System.out.printf("%-30s %s\n", "Total de Recetas Emitidas:", resumen[2]);
    }
}
