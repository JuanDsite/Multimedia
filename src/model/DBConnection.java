package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    private static Connection cn = null; // Conexión a la base de datos
    private static final String URLBD = "jdbc:mysql://localhost/mp3"; // URL de la base de datos
    private static final String usuario = "root"; // Nombre de usuario para la conexión
    private static final String contrasena = ""; // Contraseña para la conexión
    
    
    public static Connection getConnection() {
        
        try {
            cn = DriverManager.getConnection(URLBD, usuario, contrasena);
        } catch (SQLException ex) {
            System.out.println("No se puede cargar el controlador o establecer la conexión");
        }
        return cn;
    }
    
     public static void disconnect() {
        cn = null;
    }
}
