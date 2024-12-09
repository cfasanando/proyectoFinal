package gestionReportes.dao;

import conexion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    // Top 5 Medicamentos Más Recetados
    public List<String[]> obtenerTopMedicamentosMasRecetados() {
        List<String[]> resultados = new ArrayList<>();
        String sql = "{CALL TopMedicamentosMasRecetados()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String medicamento = rs.getString("medicamento");
                String totalRecetado = rs.getString("total_recetado");
                resultados.add(new String[]{medicamento, totalRecetado});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    // Cantidad de Citas por Médico
    public List<String[]> obtenerCitasPorMedico() {
        List<String[]> resultados = new ArrayList<>();
        String sql = "{CALL CitasPorMedico()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String medico = rs.getString("medico");
                String totalCitas = rs.getString("total_citas");
                resultados.add(new String[]{medico, totalCitas});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    // Pacientes con Más Citas
    public List<String[]> obtenerPacientesConMasCitas() {
        List<String[]> resultados = new ArrayList<>();
        String sql = "{CALL PacientesConMasCitas()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String paciente = rs.getString("paciente");
                String totalCitas = rs.getString("total_citas");
                resultados.add(new String[]{paciente, totalCitas});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    // Medicamentos con Stock Bajo
    public List<String[]> obtenerMedicamentosConStockBajo(int limiteStock) {
        List<String[]> resultados = new ArrayList<>();
        String sql = "{CALL MedicamentosConStockBajo(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, limiteStock);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String stockActual = rs.getString("stock_actual");
                    resultados.add(new String[]{nombre, stockActual});
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    // Resumen General de Citas
    public String[] obtenerResumenGeneralCitas() {
        String[] resumen = new String[3];
        String sql = "{CALL ResumenGeneralCitas()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                resumen[0] = rs.getString("total_citas");
                resumen[1] = rs.getString("total_diagnosticos");
                resumen[2] = rs.getString("total_recetas");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resumen;
    }
}
