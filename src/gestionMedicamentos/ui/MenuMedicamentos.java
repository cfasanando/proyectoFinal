package gestionMedicamentos.ui;

import gestionMedicamentos.modelos.Medicamento;
import gestionMedicamentos.servicios.MedicamentoService;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MenuMedicamentos {

    private final MedicamentoService medicamentoService;

    public MenuMedicamentos(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Gestion de Medicamentos (HECHO POR JEAN MAX) ---");
            System.out.println("1. Agregar Medicamento");
            System.out.println("2. Actualizar Medicamento");
            System.out.println("3. Eliminar Medicamento por Codigo");
            System.out.println("4. Listar Todos los Medicamentos");
            System.out.println("5. Verificar Stock de un Medicamento");
            System.out.println("6. Verificar Fecha de Vencimiento de un Medicamento");
            System.out.println("7. Buscar Medicamentos");
            System.out.println("8. Regresar al Menu Principal");

            System.out.print("Seleccione una opcion: ");
            int opcion = validarEntero(scanner);

            switch (opcion) {
                case 1:
                    agregarMedicamento(scanner);
                    break;
                case 2:
                    actualizarMedicamento(scanner);
                    break;
                case 3:
                    eliminarMedicamento(scanner);
                    break;
                case 4:
                    listarMedicamentos();
                    break;
                case 5:
                    verificarStock(scanner);
                    break;
                case 6:
                    verificarFechaVencimiento(scanner);
                    break;
                case 7:
                    buscarMedicamentos(scanner);
                    break;
                case 8:
                    System.out.println("Regresando al Menu Principal...");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

     private void agregarMedicamento(Scanner scanner) {
        System.out.print("Ingrese nombre del medicamento: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese cantidad disponible: ");
        int cantidad = validarEntero(scanner);
        
        String fechaVencimiento = validarFecha(scanner);

        Medicamento medicamento = new Medicamento(nombre, cantidad, fechaVencimiento);
        medicamentoService.agregarMedicamento(medicamento);
    }

    private void actualizarMedicamento(Scanner scanner) {
        System.out.print("Ingrese codigo del medicamento a actualizar: ");
        String codigo = scanner.nextLine();

        System.out.print("Ingrese nuevo nombre del medicamento: ");
        String nuevoNombre = scanner.nextLine();

        System.out.print("Ingrese nueva cantidad disponible: ");
        int nuevaCantidad = validarEntero(scanner);

        System.out.print("Ingrese nueva fecha de vencimiento (YYYY-MM-DD): ");
        String nuevaFechaVencimiento = scanner.nextLine();

        Medicamento medicamento = new Medicamento(nuevoNombre, nuevaCantidad, nuevaFechaVencimiento);
        medicamento.setCodigo(codigo);
        medicamentoService.actualizarMedicamento(medicamento);
    }

    private void eliminarMedicamento(Scanner scanner) {
        System.out.print("Ingrese codigo del medicamento a eliminar: ");
        String codigo = scanner.nextLine();
        medicamentoService.eliminarMedicamento(codigo);
    }

    private void listarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoService.listarMedicamentos();
        if (medicamentos.isEmpty()) {
            System.out.println("No hay medicamentos registrados.");
        } else {
            System.out.println("\n--- Lista de Medicamentos ---");
            for (Medicamento med : medicamentos) {
                System.out.println("Codigo: " + med.getCodigo() +
                                   " | Nombre: " + med.getNombre() +
                                   " | Cantidad: " + med.getCantidadDisponible() +
                                   " | Fecha de Vencimiento: " + med.getFechaVencimiento());
            }
        }
    }

    private void verificarStock(Scanner scanner) {
        System.out.print("Ingrese codigo del medicamento para verificar el stock: ");
        String codigo = scanner.nextLine();
        medicamentoService.verificarStock(codigo);
    }

    private void verificarFechaVencimiento(Scanner scanner) {
        System.out.print("Ingrese codigo del medicamento para verificar la fecha de vencimiento: ");
        String codigo = scanner.nextLine();
        medicamentoService.verificarFechaVencimiento(codigo);
    }

    private void buscarMedicamentos(Scanner scanner) {
        System.out.print("Ingrese el criterio de busqueda (codigo, nombre o fecha de vencimiento): ");
        String criterio = scanner.nextLine();

        List<Medicamento> medicamentos = medicamentoService.buscarMedicamentos(criterio);

        if (medicamentos.isEmpty()) {
            System.out.println("No se encontraron medicamentos que coincidan con el criterio.");
        } else {
            System.out.println("\n--- Resultados de la Busqueda ---");
            for (Medicamento med : medicamentos) {
                System.out.println("Codigo: " + med.getCodigo() +
                                   " | Nombre: " + med.getNombre() +
                                   " | Cantidad: " + med.getCantidadDisponible() +
                                   " | Fecha de Vencimiento: " + med.getFechaVencimiento());
            }
        }
    }

    private int validarEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada no valida. Ingrese un numero entero: ");
            scanner.nextLine();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        return numero;
    }
    
    private String validarFecha(Scanner scanner) {
        String fecha;
        while (true) {
            System.out.print("Ingrese fecha de vencimiento (YYYY-MM-DD): ");
            fecha = scanner.nextLine();
            if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    Date.valueOf(fecha);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Fecha inválida. Asegúrese de que la fecha exista y sea válida.");
                }
            } else {
                System.out.println("Error: Formato incorrecto. Use el formato YYYY-MM-DD.");
            }
        }
        return fecha;
    }

}
