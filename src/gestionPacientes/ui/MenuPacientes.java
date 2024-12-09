package gestionPacientes.ui;

import gestionPacientes.modelos.Paciente;
import gestionPacientes.servicios.PacienteService;
import java.util.List;
import java.util.Scanner;

public class MenuPacientes {

    private PacienteService pacienteService;

    public MenuPacientes(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Gestion de Pacientes ---");
            System.out.println("1. Agregar Paciente");
            System.out.println("2. Actualizar Paciente");
            System.out.println("3. Eliminar Paciente");
            System.out.println("4. Buscar Paciente");
            System.out.println("5. Listar Pacientes");
            System.out.println("6. Ver Historial Medico de Paciente");
            System.out.println("7. Regresar al Menu Principal");

            System.out.print("Seleccione una opcion: ");
            int opcion = -1;

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entre 1 y 7.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarPaciente(scanner);
                    break;
                case 2:
                    actualizarPaciente(scanner);
                    break;
                case 3:
                    eliminarPaciente(scanner);
                    break;
                case 4:
                    buscarPaciente(scanner);
                    break;
                case 5:
                    pacienteService.listarPacientes();
                    break;
                case 6:
                    verHistorialMedico(scanner);
                    break;
                case 7:
                    System.out.println("Saliendo del sistema de gestion de pacientes.");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    private void agregarPaciente(Scanner scanner) {
        System.out.print("Ingrese el codigo del paciente: ");
        String codigo = scanner.nextLine();

        System.out.print("Ingrese el nombre del paciente: ");
        String nombre = scanner.nextLine();

        int edad = -1;
        while (edad < 0) {
            System.out.print("Ingrese la edad del paciente: ");
            if (scanner.hasNextInt()) {
                edad = scanner.nextInt();
                if (edad < 0) {
                    System.out.println("La edad no puede ser negativa. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero.");
            }
            scanner.nextLine(); // Limpiar el buffer del scanner
        }

        String genero;
        while (true) {
            System.out.print("Ingrese el genero del paciente (M para Masculino, F para Femenino): ");
            genero = scanner.nextLine().trim().toUpperCase();
            if (genero.equals("M") || genero.equals("F")) {
                break;
            } else {
                System.out.println("Genero no valido. Por favor, ingrese 'M' para Masculino o 'F' para Femenino.");
            }
        }

        Paciente paciente = new Paciente(codigo, edad, nombre);
        paciente.setGenero(genero);

        boolean agregado = pacienteService.agregarPaciente(paciente);

        if (agregado) {
            System.out.println("Paciente agregado correctamente.");
        } else {
            System.out.println("No se pudo agregar el paciente. Verifique los datos e intente nuevamente.");
        }
    }

    private void actualizarPaciente(Scanner scanner) {
        System.out.print("Ingrese el codigo del paciente a actualizar: ");
        String codigo = scanner.nextLine();
        Paciente pacienteExistente = pacienteService.buscarPacientePorCodigo(codigo);

        if (pacienteExistente != null) {
            System.out.print("Ingrese el nuevo nombre del paciente: ");
            String nombre = scanner.nextLine();

            int edad = -1;
            while (edad < 0) {
                System.out.print("Ingrese la nueva edad del paciente: ");
                if (scanner.hasNextInt()) {
                    edad = scanner.nextInt();
                    if (edad < 0) {
                        System.out.println("La edad no puede ser negativa. Por favor, intente de nuevo.");
                    }
                } else {
                    System.out.println("Entrada no valida. Por favor, ingrese un numero entero.");
                }
                scanner.nextLine(); // Limpiar el buffer del scanner
            }

            String genero;
            while (true) {
                System.out.print("Ingrese el genero del paciente (M para Masculino, F para Femenino): ");
                genero = scanner.nextLine().trim().toUpperCase();
                if (genero.equals("M") || genero.equals("F")) {
                    break;
                } else {
                    System.out.println("Genero no valido. Por favor, ingrese 'M' para Masculino o 'F' para Femenino.");
                }
            }

            // Actualizar los datos del paciente
            pacienteExistente.setNombre(nombre);
            pacienteExistente.setEdad(edad);
            pacienteExistente.setGenero(genero);

            if (pacienteService.actualizarPaciente(pacienteExistente)) {
                System.out.println("Paciente actualizado correctamente.");
            } else {
                System.out.println("Error al actualizar el paciente.");
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }

    private void eliminarPaciente(Scanner scanner) {
        System.out.print("Ingrese el codigo del paciente a eliminar: ");
        String codigo = scanner.nextLine();
        if (pacienteService.eliminarPaciente(codigo)) {
            System.out.println("Paciente eliminado correctamente.");
        } else {
            System.out.println("Error al eliminar el paciente. Verifique el codigo.");
        }
    }

    private void buscarPaciente(Scanner scanner) {
        System.out.print("Ingrese el criterio de busqueda (Codigo, Nombre, Anio de Nacimiento, o Genero): ");
        String criterio = scanner.nextLine();

        List<Paciente> pacientesEncontrados = pacienteService.buscarPacientesPorCriterio(criterio);

        if (pacientesEncontrados.isEmpty()) {
            System.out.println("No se encontraron pacientes con el criterio proporcionado.");
        } else {
            System.out.println("\n--- Pacientes Encontrados ---");
            for (Paciente paciente : pacientesEncontrados) {
                System.out.println("Codigo: " + paciente.getCodigoPaciente() +
                                   " - Nombre: " + paciente.getNombre() +
                                   " - Edad: " + paciente.getEdad() +
                                   " - Genero: " + paciente.getGenero());
            }
        }
    }


    private void verHistorialMedico(Scanner scanner) {
        System.out.print("Ingrese el código del paciente para ver su historial médico: ");
        String codigo = scanner.nextLine();
        Paciente paciente = pacienteService.buscarPacientePorCodigo(codigo);

        if (paciente != null) {
            List<String> historial = pacienteService.obtenerHistorialMedico(codigo);

            if (historial.isEmpty()) {
                System.out.println("No hay historial médico registrado para este paciente.");
            } else {
                System.out.println("\n=== Historial Médico del Paciente ===");
                int citaNumero = 1;

                for (int i = 0; i < historial.size(); i++) {
                    String registro = historial.get(i);

                    // Si es una línea de cita
                    if (registro.startsWith("Cita:")) {
                        System.out.printf("\n[%d] %s\n", citaNumero++, registro);
                    } 
                    // Si es una cabecera de medicamentos
                    else if (registro.contains("Medicamento") && registro.contains("Cantidad")) {
                        System.out.println("\n" + registro);
                        System.out.println("---------------------------------------------");
                    } 
                    // Si es una línea de medicamento
                    else {
                        System.out.println("  " + registro);
                    }
                }

                System.out.println("\n==============================================");
            }
        } else {
            System.out.println("Paciente no encontrado.");
        }
    }


}
