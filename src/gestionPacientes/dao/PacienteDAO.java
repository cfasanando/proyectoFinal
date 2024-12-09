package gestionPacientes.dao;

import conexion.ConexionDB;
import gestionPacientes.modelos.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Insertar paciente
    public boolean insertarPaciente(Paciente paciente) {
        String sql = "{CALL InsertarPaciente(?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, paciente.getCodigoPaciente());
            stmt.setString(2, paciente.getNombre());
            stmt.setInt(3, paciente.getEdad());
            stmt.setString(4, paciente.getGenero());           

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar paciente
    public boolean actualizarPaciente(Paciente paciente) {
        String sql = "{CALL ActualizarPaciente(?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, paciente.getCodigoPaciente());
            stmt.setString(2, paciente.getNombre());
            stmt.setInt(3, paciente.getEdad());
            stmt.setString(4, paciente.getGenero());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Eliminar paciente
    public boolean eliminarPaciente(String codigo) {
        String sql = "{CALL EliminarPaciente(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigo);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Buscar paciente por código
    public Paciente buscarPacientePorCodigo(String codigo) {
        String sql = "{CALL BuscarPacientePorCodigo(?)}";
        Paciente paciente = null;

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                paciente = new Paciente(codigo, edad, nombre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paciente;
    }

    // Método para búsqueda general de pacientes
    public List<Paciente> buscarPacientesPorCriterio(String criterio) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "{CALL BuscarPacientes(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, criterio);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String codigo = rs.getString("codigo_paciente");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String genero = rs.getString("genero");

                Paciente paciente = new Paciente(codigo, edad, nombre);
                paciente.setGenero(genero);
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }

    // Obtener todos los pacientes
    public List<Paciente> obtenerPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "{CALL ObtenerPacientes()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo_paciente");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");

                Paciente paciente = new Paciente(codigo, edad, nombre);
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacientes;
    }
    
    // Método para obtener el historial médico de un paciente
    public List<String> obtenerHistorialMedico(String codigoPaciente) {
        List<String> historial = new ArrayList<>();
        String sql = "{CALL ObtenerHistorialMedico(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoPaciente);
            ResultSet rs = stmt.executeQuery();

            String citaActual = "";
            while (rs.next()) {
                String codigoCita = rs.getString("codigo_cita");
                String fecha = rs.getString("fecha");
                String hora = rs.getString("hora");
                String diagnostico = rs.getString("diagnostico");
                String codigoMedicamento = rs.getString("codigo_medicamento");
                int cantidad = rs.getInt("cantidad");

                // Mostrar información de la cita y diagnóstico solo si es una nueva cita
                if (!citaActual.equals(codigoCita)) {
                    if (!citaActual.isEmpty()) {
                        historial.add("--------------------------------------------");
                    }
                    citaActual = codigoCita;
                    historial.add(String.format("Cita: %s | Fecha: %s | Hora: %s | Diagnóstico: %s",
                            codigoCita, fecha, hora, diagnostico));
                    historial.add(String.format("%-20s %-10s", "Medicamento", "Cantidad"));
                    historial.add("--------------------------------------------");
                }

                // Agregar información de los medicamentos
                if (!codigoMedicamento.equals("N/A")) {
                    historial.add(String.format("%-20s %-10d", codigoMedicamento, cantidad));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historial;
    }

}
