package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/SaludVital";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection conexion;

    private ConexionDB() {}

    // Obtener la instancia única de la conexión
    public static Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con la base de datos");
        }
        return conexion;
    }

    // Cerrar la conexión al final del sistema
    public static void closeConnection() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("Conexion cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
