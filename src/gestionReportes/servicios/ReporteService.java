package gestionReportes.servicios;

import gestionReportes.dao.ReporteDAO;

import java.util.List;

public class ReporteService {

    private final ReporteDAO reporteDAO;

    // Constructor
    public ReporteService() {
        this.reporteDAO = new ReporteDAO();
    }

    // Obtener Top 5 Medicamentos Más Recetados
    public List<String[]> obtenerTopMedicamentosMasRecetados() {
        return reporteDAO.obtenerTopMedicamentosMasRecetados();
    }

    // Obtener la Cantidad de Citas por Médico
    public List<String[]> obtenerCitasPorMedico() {
        return reporteDAO.obtenerCitasPorMedico();
    }

    // Obtener Pacientes con Más Citas
    public List<String[]> obtenerPacientesConMasCitas() {
        return reporteDAO.obtenerPacientesConMasCitas();
    }

    // Obtener Medicamentos con Stock Bajo
    public List<String[]> obtenerMedicamentosConStockBajo(int limiteStock) {
        return reporteDAO.obtenerMedicamentosConStockBajo(limiteStock);
    }

    // Obtener Resumen General de Citas
    public String[] obtenerResumenGeneralCitas() {
        return reporteDAO.obtenerResumenGeneralCitas();
    }
}
