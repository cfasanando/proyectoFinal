package gestionCitas;

import gestionCitas.servicios.CitaService;
import gestionCitas.ui.MenuCitasMedicas;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.servicios.PacienteService;

public class MainCitas {
    public static void main(String[] args) {
        MedicamentoService medicamentoService = MedicamentoService.getInstance();
        CitaService citaService = CitaService.getInstance(medicamentoService);
        PacienteService pacienteService = PacienteService.getInstance();
        MenuCitasMedicas menuCitasMedicas = new MenuCitasMedicas(citaService, pacienteService, medicamentoService);
        menuCitasMedicas.mostrarMenu();
    }
}

