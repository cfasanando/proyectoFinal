package gestionCitas.dao;

import conexion.ConexionDB;
import gestionCitas.modelos.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // Insertar médico
    public boolean insertarMedico(Medico medico) {
        String sql = "{CALL InsertarMedico(?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, medico.getCodigo());
            stmt.setString(2, medico.getNombre());
            stmt.setString(3, medico.getEspecialidad());
            stmt.setBoolean(4, medico.isDisponible());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los médicos
    public List<Medico> obtenerMedicos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "{CALL ObtenerMedicos()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo_medico");
                String nombre = rs.getString("nombre");
                String especialidad = rs.getString("especialidad");
                boolean disponible = rs.getBoolean("disponible");

                Medico medico = new Medico(codigo, nombre, especialidad);
                medico.setDisponible(disponible);
                medicos.add(medico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener médicos.");
        }

        return medicos;
    }
    
    // Verificar si el médico ya existe por código
    public boolean existeMedico(String codigoMedico) {
        String sql = "{CALL BuscarMedicoPorCodigo(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoMedico);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Devuelve true si hay un resultado, es decir, el médico existe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Buscar médico por código
    public Medico buscarMedicoPorCodigo(String codigo) {
        String sql = "{CALL BuscarMedicoPorCodigo(?)}";
        Medico medico = null;

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String especialidad = rs.getString("especialidad");
                boolean disponible = rs.getBoolean("disponible");

                medico = new Medico(codigo, nombre, especialidad);
                medico.setDisponible(disponible);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medico;
    }

    // Actualizar disponibilidad del médico
    public boolean actualizarDisponibilidad(String codigo, boolean disponible) {
        String sql = "{CALL ActualizarDisponibilidadMedico(?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigo);
            stmt.setBoolean(2, disponible);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }        
}
