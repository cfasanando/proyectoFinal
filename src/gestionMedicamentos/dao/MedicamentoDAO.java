package gestionMedicamentos.dao;

import conexion.ConexionDB;
import gestionMedicamentos.modelos.Medicamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAO {

    public void insertarMedicamento(Medicamento medicamento) {
        String query = "{CALL InsertarMedicamento(?, ?, ?, ?)}";
        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query)) {

            cst.setString(1, medicamento.getCodigo());
            cst.setString(2, medicamento.getNombre());
            cst.setInt(3, medicamento.getCantidadDisponible());
            cst.setDate(4, Date.valueOf(medicamento.getFechaVencimiento()));

            cst.execute();
            System.out.println("Medicamento insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medicamento> obtenerMedicamentos() {
        List<Medicamento> lista = new ArrayList<>();
        String query = "{CALL ObtenerMedicamentos()}";

        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query);
             ResultSet rs = cst.executeQuery()) {

            while (rs.next()) {
                Medicamento med = new Medicamento(
                    rs.getString("nombre"),
                    rs.getInt("cantidad_disponible"),
                    rs.getString("fecha_vencimiento")
                );
                med.setCodigo(rs.getString("codigo"));
                lista.add(med);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarMedicamento(Medicamento medicamento) {
        String query = "{CALL ActualizarMedicamento(?, ?, ?, ?)}";
        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query)) {

            cst.setString(1, medicamento.getCodigo());
            cst.setString(2, medicamento.getNombre());
            cst.setInt(3, medicamento.getCantidadDisponible());
            cst.setDate(4, Date.valueOf(medicamento.getFechaVencimiento()));

            cst.execute();
            System.out.println("Medicamento actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarMedicamento(String codigo) {
        String query = "{CALL EliminarMedicamento(?)}";
        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query)) {

            cst.setString(1, codigo);
            cst.execute();
            System.out.println("Medicamento eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public List<Medicamento> buscarMedicamentos(String criterio) {
        List<Medicamento> lista = new ArrayList<>();
        String query = "{CALL BuscarMedicamentos(?)}";

        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query)) {

            cst.setString(1, criterio);
            ResultSet rs = cst.executeQuery();

            while (rs.next()) {
                Medicamento med = new Medicamento(
                    rs.getString("nombre"),
                    rs.getInt("cantidad_disponible"),
                    rs.getString("fecha_vencimiento")
                );
                med.setCodigo(rs.getString("codigo_medicamento"));
                lista.add(med);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
     
    // Método para buscar un medicamento por código usando un procedimiento almacenado
    public Medicamento buscarMedicamentoPorCodigo(String codigoMedicamento) {
        String sql = "{CALL BuscarMedicamentoPorCodigo(?)}";
        Medicamento medicamento = null;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigoMedicamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String codigo = rs.getString("codigo_medicamento");
                String nombre = rs.getString("nombre");
                int cantidadDisponible = rs.getInt("cantidad_disponible");
                String fechaVencimiento = rs.getString("fecha_vencimiento");

                medicamento = new Medicamento(codigo, nombre, cantidadDisponible, fechaVencimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicamento;
    }
    
    // Verificar stock por código
    public int obtenerStock(String codigo) {
        String query = "{CALL VerificarStock(?)}";
        int stock = -1;

        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query)) {

            cst.setString(1, codigo);
            ResultSet rs = cst.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("cantidad_disponible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stock;
    }   
    
    // Método para actualizar el stock del medicamento
    public boolean actualizarStock(String codigoMedicamento, int nuevaCantidad) {
        String sql = "{CALL ActualizarStockMedicamento(?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, codigoMedicamento);
            stmt.setInt(2, nuevaCantidad);

            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verificar fecha de vencimiento por código
    public String obtenerFechaVencimiento(String codigo) {
        String query = "{CALL VerificarFechaVencimiento(?)}";
        String fechaVencimiento = null;

        try (Connection con = ConexionDB.getConnection();
             CallableStatement cst = con.prepareCall(query)) {

            cst.setString(1, codigo);
            ResultSet rs = cst.executeQuery();

            if (rs.next()) {
                fechaVencimiento = rs.getString("fecha_vencimiento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fechaVencimiento;
    }       
}