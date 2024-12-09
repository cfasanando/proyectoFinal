package gestionPacientes;

import gestionPacientes.servicios.PacienteService;
import gestionPacientes.ui.MenuPacientes;

public class MainPacientes {
    public static void main(String[] args) {
        // Instancia del servicio de pacientes
        PacienteService pacienteService = new PacienteService();

        // Creación del menú de gestión de pacientes
        MenuPacientes menuPacientes = new MenuPacientes(pacienteService);

        // Mostrar el menú principal
        menuPacientes.mostrarMenu();
    }
}
