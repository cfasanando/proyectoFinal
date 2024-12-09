package gestionReportes;

import gestionReportes.servicios.ReporteService;
import gestionReportes.ui.MenuReportesEstadisticas;

public class MainReportes {
    public static void main(String[] args) {
               
        // Crear instancia del ReporteService
        ReporteService reporteService = new ReporteService();
        
        // Crear el menú de reportes y pasarle el ReporteService
        MenuReportesEstadisticas menuReportesEstadisticas = new MenuReportesEstadisticas(reporteService);
        
        // Mostrar el menú
        menuReportesEstadisticas.mostrarMenu();
    }
}
