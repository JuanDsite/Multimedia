package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private Connection con;  // Conexión a la base de datos
    private PreparedStatement ps;  // Preparación de la consulta SQL
    private ResultSet rs;

    public UserDAO() {

        con = null;
        ps = null;
        rs = null;
    }

    public ArrayList<UserVO> getUsersList() {
        ArrayList<UserVO> userList = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                UserVO user = new UserVO(
                        rs.getString("nombreUsuario"),
                        rs.getString("clave"),
                        rs.getDouble("saldo"),
                        rs.getInt("idUsuario")
                );
                userList.add(user);
            }
        } catch (SQLException exc) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exc);
        } finally {
            closeResources();
        }

        return userList;
    }

    public void modifyDebtValue(UserVO modifiedUser) {
        String query = "UPDATE clientes SET saldo = ? WHERE idUsuario = ?";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(query);

            // Corrige los índices para que coincidan con la consulta
            ps.setDouble(1, modifiedUser.getBalanceDue());
            ps.setInt(2, modifiedUser.getId());

            ps.executeUpdate();  // Ejecutar la actualización
        } catch (SQLException exc) {
            // Manejo de excepción
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exc);
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        /**
         * Cierra los recursos utilizados en las operaciones de base de datos.
         * Asegura que el ResultSet, PreparedStatement y Connection se cierren
         * para liberar recursos y evitar fugas de memoria.
         */
        try {
            if (rs != null) {
                rs.close(); // Cierra el ResultSet si no es nulo
            }
            if (ps != null) {
                ps.close(); // Cierra el PreparedStatement si no es nulo
            }
            if (con != null) {
                con.close(); // Cierra la conexión si no es nula
            }
        } catch (SQLException exc) {
            // Manejo de excepciones opcional, como imprimir el error o registrarlo en un log
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exc);
        }
    }
}
