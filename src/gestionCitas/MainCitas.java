package gestionCitas;

import gestionCitas.servicios.CitaService;
import gestionCitas.servicios.MedicoService;
import gestionCitas.ui.MenuCitasMedicas;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.servicios.PacienteService;

public class MainCitas {
    public static void main(String[] args) {
        // Crear instancias de los servicios necesarios
        MedicoService medicoService = new MedicoService();
        PacienteService pacienteService = new PacienteService();
        MedicamentoService medicamentoService = new MedicamentoService();
        CitaService citaService = new CitaService(medicoService, pacienteService, medicamentoService);

        // Crear y mostrar el menú de citas médicas
        MenuCitasMedicas menu = new MenuCitasMedicas(citaService, medicoService, pacienteService, medicamentoService);
        menu.mostrarMenu();
    }
}
