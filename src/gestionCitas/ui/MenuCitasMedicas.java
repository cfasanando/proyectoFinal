package gestionCitas.ui;

import gestionCitas.modelos.Cita;
import gestionCitas.modelos.Medico;
import gestionCitas.servicios.MedicoService;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.modelos.Paciente;
import gestionPacientes.servicios.PacienteService;
import gestionCitas.servicios.CitaService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MenuCitasMedicas {
    private CitaService citaService;
    private MedicoService medicoService;
    private PacienteService pacienteService;
    private MedicamentoService medicamentoService;

    public MenuCitasMedicas(CitaService citaService, MedicoService medicoService, PacienteService pacienteService, MedicamentoService medicamentoService) {
        this.citaService = citaService;
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
        this.medicamentoService = medicamentoService;
    }

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Programacion de Citas Medicas y Gestion de Medicos (HECHO POR MANUEL) ---");
            System.out.println("1. Programar Cita");
            System.out.println("2. Cancelar Cita");
            System.out.println("3. Listar Citas");
            System.out.println("4. Registrar Medico");
            System.out.println("5. Listar Medicos");
            System.out.println("6. Actualizar Disponibilidad de Medico");
            System.out.println("7. Registrar Diagnostico y Receta");
            System.out.println("8. Regresar al Menu Principal");

            int opcion = -1;
            boolean entradaValida = false;

            // Validar que el usuario ingrese un número entero válido
            while (!entradaValida) {
                System.out.print("Seleccione una opcion: ");
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    entradaValida = true;
                    scanner.nextLine(); // Limpiar el buffer del scanner
                } else {
                    System.out.println("Entrada no valida. Por favor, ingrese un numero del 1 al 8.");
                    scanner.nextLine(); // Limpiar el buffer del scanner para descartar la entrada incorrecta
                }
            }

            switch (opcion) {
                case 1:
                    programarCita(scanner);
                    break;
                case 2:
                    cancelarCita(scanner);
                    break;
                case 3:
                        citaService.listarCitas();
                    break;
                case 4:
                    registrarMedico(scanner);
                    break;
                case 5:
                    medicoService.listarMedicos();
                    break;
                case 6:
                    actualizarDisponibilidadMedico(scanner);
                    break;
                case 7:
                    registrarDiagnosticoYReceta(scanner);
                    break;
                case 8:
                    System.out.println("Regresando al menu principal...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    private void programarCita(Scanner scanner) {
        System.out.print("Ingrese el codigo de la cita: ");
        String codigoCita = scanner.nextLine();

        // Verificar si la cita ya existe
        Cita citaExistente = citaService.buscarCitaPorCodigo(codigoCita);

        if (citaExistente != null) {
            System.out.println("La cita con el codigo ingresado ya existe.");
            System.out.print("¿Desea actualizar la cita existente? (yes/no): ");
            String respuesta = scanner.nextLine().toLowerCase();

            if (!respuesta.equals("yes")) {
                System.out.println("Operación cancelada. No se realizó ningún cambio.");
                return;
            }

            // Actualizar la cita existente
            actualizarCita(scanner, citaExistente);
            return;
        }

        // Crear una nueva cita si el código no existe
        crearNuevaCita(scanner, codigoCita);
    }

    private void crearNuevaCita(Scanner scanner, String codigoCita) {
        // Validar fecha
        String fecha;
        while (true) {
            System.out.print("Ingrese la fecha de la cita (YYYY-MM-DD) o 'salir' para cancelar: ");
            fecha = scanner.nextLine();
            if (fecha.equalsIgnoreCase("salir")) {
                System.out.println("Proceso cancelado. Regresando al menú principal.");
                return;
            }
            if (fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    LocalDate.parse(fecha);
                    break; // Fecha válida
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Formato de fecha incorrecto. Use el formato YYYY-MM-DD.");
            }
        }

        // Validar hora
        String hora;
        while (true) {
            System.out.print("Ingrese la hora de la cita (HH:MM) o 'salir' para cancelar: ");
            hora = scanner.nextLine();
            if (hora.equalsIgnoreCase("salir")) {
                System.out.println("Proceso cancelado. Regresando al menú principal.");
                return;
            }
            if (hora.matches("\\d{2}:\\d{2}")) {
                try {
                    LocalTime.parse(hora);
                    break; // Hora válida
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de hora incorrecto. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Formato de hora incorrecto. Use el formato HH:MM.");
            }
        }

        // Validar código del paciente
        Paciente paciente;
        while (true) {
            System.out.print("Ingrese el codigo del paciente o 'salir' para cancelar: ");
            String codigoPaciente = scanner.nextLine();
            if (codigoPaciente.equalsIgnoreCase("salir")) {
                System.out.println("Proceso cancelado. Regresando al menú principal.");
                return;
            }
            paciente = pacienteService.buscarPacientePorCodigo(codigoPaciente);
            if (paciente != null) {
                break;
            } else {
                System.out.println("Paciente no encontrado. Por favor, intente de nuevo o escriba 'salir' para cancelar.");
            }
        }

        // Validar código del médico
        Medico medico;
        while (true) {
            System.out.print("Ingrese el codigo del medico o 'salir' para cancelar: ");
            String codigoMedico = scanner.nextLine();
            if (codigoMedico.equalsIgnoreCase("salir")) {
                System.out.println("Proceso cancelado. Regresando al menú principal.");
                return;
            }
            medico = medicoService.buscarMedicoPorCodigo(codigoMedico);
            if (medico != null) {
                if (medico.isDisponible()) {
                    break;
                } else {
                    System.out.println("El medico no está disponible. Por favor, elija otro medico o escriba 'salir' para cancelar.");
                }
            } else {
                System.out.println("Medico no encontrado. Por favor, intente de nuevo o escriba 'salir' para cancelar.");
            }
        }

        // Crear y programar la cita
        Cita nuevaCita = new Cita(codigoCita, fecha, hora, medico, paciente);
        citaService.programarCita(nuevaCita);
    }

    private void actualizarCita(Scanner scanner, Cita citaExistente) {
        System.out.println("Actualizando la cita con el codigo: " + citaExistente.getCodigo());

        // Solicitar y validar nueva fecha
        String nuevaFecha;
        while (true) {
            System.out.print("Ingrese la nueva fecha de la cita (YYYY-MM-DD) [Actual: " + citaExistente.getFecha() + "] o 'salir' para cancelar: ");
            nuevaFecha = scanner.nextLine();
            if (nuevaFecha.equalsIgnoreCase("salir")) {
                System.out.println("Proceso de actualización cancelado. Regresando al menú principal.");
                return;
            }
            if (nuevaFecha.isEmpty()) {
                break; // Mantener la fecha actual si no se ingresa nada
            }
            if (nuevaFecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    LocalDate.parse(nuevaFecha);
                    citaExistente.setFecha(nuevaFecha);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha incorrecto. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Formato de fecha incorrecto. Use el formato YYYY-MM-DD.");
            }
        }

        // Solicitar y validar nueva hora
        String nuevaHora;
        while (true) {
            System.out.print("Ingrese la nueva hora de la cita (HH:MM) [Actual: " + citaExistente.getHora() + "] o 'salir' para cancelar: ");
            nuevaHora = scanner.nextLine();
            if (nuevaHora.equalsIgnoreCase("salir")) {
                System.out.println("Proceso de actualización cancelado. Regresando al menú principal.");
                return;
            }
            if (nuevaHora.isEmpty()) {
                break; // Mantener la hora actual si no se ingresa nada
            }
            if (nuevaHora.matches("\\d{2}:\\d{2}")) {
                try {
                    LocalTime.parse(nuevaHora);
                    citaExistente.setHora(nuevaHora);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de hora incorrecto. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("Formato de hora incorrecto. Use el formato HH:MM.");
            }
        }

        // Actualizar la cita
        citaService.actualizarCita(citaExistente);
    }

    private void cancelarCita(Scanner scanner) {
        System.out.print("Ingrese el codigo de la cita a cancelar: ");
        String codigoCita = scanner.nextLine();
        citaService.cancelarCita(codigoCita);
    }

   private void registrarDiagnosticoYReceta(Scanner scanner) {
        String codigoCita;

        // Validar que la cita exista sin bloquear el proceso
        while (true) {
            System.out.print("Ingrese el codigo de la cita: ");
            codigoCita = scanner.nextLine();
            Cita cita = citaService.buscarCitaPorCodigo(codigoCita);

            if (cita != null) {
                // Verificar si la cita ya tiene un diagnóstico
                if (citaService.tieneDiagnostico(codigoCita)) {
                    System.out.println("Esta cita ya tiene un diagnóstico.");
                    System.out.print("¿Desea actualizar el diagnóstico y las recetas? (yes/no): ");
                    String respuesta = scanner.nextLine();
                    if (!respuesta.equalsIgnoreCase("yes")) {
                        return;
                    }
                }
                break;
            } else {
                System.out.println("Cita no encontrada. Por favor, intente de nuevo o escriba 'salir' para regresar al menú.");
                String opcion = scanner.nextLine();
                if (opcion.equalsIgnoreCase("salir")) {
                    return;
                }
            }
        }

        // Ingresar diagnóstico
        System.out.print("Ingrese el diagnóstico: ");
        String diagnostico = scanner.nextLine();

        // Ingresar recetas
        boolean agregarMas = true;
        while (agregarMas) {
            String codigoMedicamento;
            int cantidad;

            // Validar el código del medicamento
            while (true) {
                System.out.print("Ingrese el código del medicamento: ");
                codigoMedicamento = scanner.nextLine();
                if (medicamentoService.existeMedicamento(codigoMedicamento)) {
                    break;
                } else {
                    System.out.println("Medicamento no encontrado. Por favor, intente de nuevo.");
                }
            }

            // Validar la cantidad del medicamento
            while (true) {
                System.out.print("Ingrese la cantidad del medicamento: ");
                if (scanner.hasNextInt()) {
                    cantidad = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    break;
                } else {
                    System.out.println("Cantidad no válida. Ingrese un número entero.");
                    scanner.nextLine(); // Limpiar el buffer
                }
            }

            // Registrar diagnóstico y receta
            boolean exito = citaService.registrarDiagnosticoYReceta(codigoCita, diagnostico, codigoMedicamento, cantidad, true);
            if (!exito) {
                System.out.println("Error al registrar el diagnóstico y la receta. Finalizando el proceso.");
                return;
            }

            // Preguntar si desea agregar otro medicamento
            System.out.print("¿Desea agregar otro medicamento a esta receta? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("no")) {
                agregarMas = false;
            }
        }

        System.out.println("Diagnóstico y recetas registrados correctamente.");
    }

    private void registrarMedico(Scanner scanner) {
        System.out.print("Ingrese el codigo del medico: ");
        String codigoMedico = scanner.nextLine();
        System.out.print("Ingrese el nombre del medico: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la especialidad del medico: ");
        String especialidad = scanner.nextLine();

        Medico medico = new Medico(codigoMedico, nombre, especialidad);
        medicoService.registrarMedico(medico);
    }

    private void actualizarDisponibilidadMedico(Scanner scanner) {
        System.out.print("Ingrese el codigo del medico: ");
        String codigoMedico = scanner.nextLine();

        Medico medico = medicoService.buscarMedicoPorCodigo(codigoMedico);

        if (medico == null) {
            System.out.println("Medico no encontrado. Por favor, intente con otro codigo.");
            return;
        }

        System.out.print("¿Esta disponible? (true/false): ");
        boolean disponible = false;
        while (true) {
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("true")) {
                disponible = true;
                break;
            } else if (input.equals("false")) {
                disponible = false;
                break;
            } else {
                System.out.println("Entrada no valida. Por favor, ingrese 'true' o 'false': ");
            }
        }

        medicoService.actualizarDisponibilidadMedico(codigoMedico, disponible);

        // Confirmar el estado actualizado del médico
        System.out.println("La disponibilidad del medico " + medico.getNombre() + " se ha actualizado a: " + (disponible ? "Disponible" : "No Disponible"));
    }
}
