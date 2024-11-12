package gestionMedicamentos.ui;

import gestionMedicamentos.modelos.Medicamento;
import gestionMedicamentos.servicios.MedicamentoService;

import java.util.Scanner;
import java.util.Set;

public class MenuMedicamentos {

    private MedicamentoService medicamentoService;

    public MenuMedicamentos(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Gestión de Medicamentos hecho por JEAN MAX ---");
            System.out.println("1. Agregar Medicamento");
            System.out.println("2. Eliminar Medicamento por Código");
            System.out.println("3. Verificar Stock por Código");
            System.out.println("4. Verificar Fecha de Vencimiento por Código");
            System.out.println("5. Listar Medicamentos");
            System.out.println("6. Buscar Medicamentos por Prefijo de Código");
            System.out.println("7. Regresar al Menú Principal");

            System.out.print("Seleccione una opción: ");
            int opcion = -1;

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 7.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nombre del medicamento: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese cantidad disponible: ");
                    int cantidad = scanner.nextInt();
                    System.out.print("Ingrese fecha de vencimiento (YYYY-MM-DD): ");
                    scanner.nextLine();
                    String fechaVencimiento = scanner.nextLine();
                    medicamentoService.agregarMedicamento(new Medicamento(nombre, cantidad, fechaVencimiento));
                    break;
                case 2:
                    System.out.print("Ingrese código del medicamento a eliminar: ");
                    String codigoEliminar = scanner.nextLine();
                    medicamentoService.eliminarMedicamentoPorCodigo(codigoEliminar);
                    break;
                case 3:
                    System.out.print("Ingrese código del medicamento para verificar stock: ");
                    String codigoStock = scanner.nextLine();
                    Medicamento medicamentoStock = medicamentoService.buscarPorCodigo(codigoStock);
                    if (medicamentoStock != null) {
                        medicamentoStock.verificarStock();
                    } else {
                        System.out.println("Medicamento no encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese código del medicamento para verificar fecha de vencimiento: ");
                    String codigoFecha = scanner.nextLine();
                    Medicamento medicamentoFecha = medicamentoService.buscarPorCodigo(codigoFecha);
                    if (medicamentoFecha != null) {
                        medicamentoFecha.verificarFechaVencimiento();
                    } else {
                        System.out.println("Medicamento no encontrado.");
                    }
                    break;
                case 5:
                    medicamentoService.listarMedicamentos();
                    break;
                case 6:
                    System.out.print("Ingrese el prefijo del código para buscar medicamentos: ");
                    String prefijo = scanner.nextLine();
                    Set<Medicamento> resultados = medicamentoService.buscarPorCodigoParcial(prefijo);
                    if (!resultados.isEmpty()) {
                        System.out.println("\nMedicamentos encontrados:");
                        for (Medicamento m : resultados) {
                            System.out.println("Código: " + m.getCodigo() + " - Nombre: " + m.getNombre() +
                                    " - Cantidad: " + m.getCantidadDisponible() + " - Vencimiento: " + m.getFechaVencimiento());
                        }
                    } else {
                        System.out.println("No se encontraron medicamentos con ese prefijo.");
                    }
                    break;
                case 7:
                    System.out.println("Saliendo del sistema de Gestión de Medicamentos.");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
