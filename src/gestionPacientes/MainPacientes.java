package gestionPacientes;

import gestionPacientes.servicios.PacienteService;
import gestionPacientes.ui.MenuPacientes;

public class MainPacientes {
    public static void main(String[] args) {
        PacienteService pacienteService = PacienteService.getInstance();
        MenuPacientes menuPacientes = new MenuPacientes(pacienteService);
        menuPacientes.mostrarMenu();
    }
}
