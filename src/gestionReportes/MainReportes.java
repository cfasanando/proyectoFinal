package gestionReportes;

import gestionCitas.servicios.CitaService;
import gestionMedicamentos.servicios.MedicamentoService;
import gestionPacientes.servicios.PacienteService;
import gestionReportes.ui.MenuReportesEstadisticas;

public class MainReportes {
    public static void main(String[] args) {
        MedicamentoService medicamentoService = MedicamentoService.getInstance();
        CitaService citaService = CitaService.getInstance(medicamentoService);
        PacienteService pacienteService = PacienteService.getInstance();
        MenuReportesEstadisticas menuReportesEstadisticas = new MenuReportesEstadisticas(citaService, pacienteService, medicamentoService);
        menuReportesEstadisticas.mostrarMenu();
    }
}

