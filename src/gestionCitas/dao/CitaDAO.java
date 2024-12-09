package gestionCitas.dao;

import conexion.ConexionDB;
import gestionCitas.modelos.Cita;
import gestionCitas.modelos.Medico;
import gestionPacientes.modelos.Paciente;
import gestionCitas.servicios.MedicoService;
import gestionPacientes.servicios.PacienteService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    
    private MedicoService medicoService;
    private PacienteService pacienteService;
    
    public CitaDAO(MedicoService medicoService, PacienteService pacienteService) {
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
    }

    // Insertar cita
   public boolean insertarCita(Cita cita) {
        String sql = "{CALL InsertarCita(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, cita.getCodigo());
            stmt.setString(2, cita.getFecha());
            stmt.setString(3, cita.getHora());
            stmt.setString(4, cita.getMedicoAsignado().getCodigo());
            stmt.setString(5, cita.getPacienteAsignado().getCodigoPaciente());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   
    // Actualizar cita existente
    public boolean actualizarCita(Cita cita) {
        String sql = "{CALL ActualizarCita(?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, cita.getCodigo());
            stmt.setString(2, cita.getFecha());
            stmt.setString(3, cita.getHora());
            stmt.setString(4, cita.getMedicoAsignado().getCodigo());
            stmt.setString(5, cita.getPacienteAsignado().getCodigoPaciente());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las citas
    public List<Cita> obtenerCitas() {
        List<Cita> citas = new ArrayList<>();
        List<String[]> datosTemporales = new ArrayList<>();
        String sql = "{CALL ObtenerCitas()}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Almacenar los datos del ResultSet en una lista temporal
            while (rs.next()) {
                String codigo = rs.getString("codigo_cita");
                String fecha = rs.getString("fecha");
                String hora = rs.getString("hora");
                String codigoMedico = rs.getString("codigo_medico");
                String codigoPaciente = rs.getString("codigo_paciente");

                datosTemporales.add(new String[]{codigo, fecha, hora, codigoMedico, codigoPaciente});
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener citas.");
            return citas;
        }

        // Procesar los datos temporales después de cerrar el ResultSet
        for (String[] datos : datosTemporales) {
            String codigo = datos[0];
            String fecha = datos[1];
            String hora = datos[2];
            String codigoMedico = datos[3];
            String codigoPaciente = datos[4];

            Medico medico = medicoService.buscarMedicoPorCodigo(codigoMedico);
            Paciente paciente = pacienteService.buscarPacientePorCodigo(codigoPaciente);

            if (medico != null && paciente != null) {
                Cita cita = new Cita(codigo, fecha, hora, medico, paciente);
                citas.add(cita);
            } else {
                System.out.println("Error: Médico o paciente no encontrado para la cita con código: " + codigo);
            }
        }

        return citas;
    }
    
    // Verificar si la cita ya existe por código
    public boolean existeCita(String codigoCita) {
        String sql = "{CALL BuscarCitaPorCodigo(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoCita);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar cita
    public boolean eliminarCita(String codigoCita) {
        String sql = "{CALL EliminarCita(?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoCita);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar cita por fecha y hora
    public Cita buscarCitaPorFechaHora(String fecha, String hora) {
        String sql = "{CALL BuscarCitaPorFechaHora(?, ?)}";
        Cita cita = null;

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, fecha);
            stmt.setString(2, hora);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Datos de la cita
                String codigo = rs.getString("codigo_cita");

                // Datos del médico
                String codigoMedico = rs.getString("codigo_medico");
                String nombreMedico = rs.getString("nombre");
                String especialidadMedico = rs.getString("especialidad_medico");
                boolean disponibleMedico = rs.getBoolean("disponible_medico");
                Medico medico = new Medico(codigoMedico, nombreMedico, especialidadMedico);
                medico.setDisponible(disponibleMedico);

                // Datos del paciente
                String codigoPaciente = rs.getString("codigo_paciente");
                String nombrePaciente = rs.getString("nombre_paciente");
                int edadPaciente = rs.getInt("edad_paciente");
                Paciente paciente = new Paciente(codigoPaciente, edadPaciente, nombrePaciente);

                // Crear la cita con todos los datos completos
                cita = new Cita(codigo, fecha, hora, medico, paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al buscar la cita.");
        }

        return cita;
    }
    
    public Cita buscarCitaPorCodigo(String codigoCita) {
        String sql = "{CALL BuscarCitaPorCodigo(?)}";
        Cita cita = null;

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoCita);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String fecha = rs.getString("fecha");
                String hora = rs.getString("hora");
                String codigoMedico = rs.getString("codigo_medico");
                String codigoPaciente = rs.getString("codigo_paciente");

                Medico medico = medicoService.buscarMedicoPorCodigo(codigoMedico);
                Paciente paciente = pacienteService.buscarPacientePorCodigo(codigoPaciente);

                if (medico != null && paciente != null) {
                    cita = new Cita(codigoCita, fecha, hora, medico, paciente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cita;
    }
    
    public boolean registrarDiagnosticoYReceta(String codigoCita, String diagnostico, String codigoMedicamento, int cantidad) {
        String sql = "{CALL RegistrarDiagnosticoYReceta(?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoCita);
            stmt.setString(2, diagnostico);
            stmt.setString(3, codigoMedicamento);
            stmt.setInt(4, cantidad);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registrarOActualizarReceta(String codigoCita, String codigoMedicamento, int cantidad) {
        String sql = "{CALL RegistrarOActualizarReceta(?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoCita);
            stmt.setString(2, codigoMedicamento);
            stmt.setInt(3, cantidad);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean actualizarDiagnostico(String codigoCita, String diagnostico) {
        String sql = "{CALL ActualizarDiagnostico(?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoCita);
            stmt.setString(2, diagnostico);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
