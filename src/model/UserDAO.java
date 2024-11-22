package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que gestiona las operaciones relacionadas con los usuarios en la base
 * de datos. Proporciona métodos para obtener la lista de usuarios y modificar
 * el saldo de un usuario. Utiliza la clase {@link DBConnection} para obtener la
 * conexión a la base de datos.
 */
public class UserDAO {

    private Connection con;  // Conexión a la base de datos
    private PreparedStatement ps;  // Preparación de la consulta SQL
    private ResultSet rs;  // Resultados de la consulta SQL

    /**
     * Constructor de la clase UserDAO. Inicializa los recursos de la base de
     * datos (conexión, prepared statement y result set).
     */
    public UserDAO() {
        con = null;
        ps = null;
        rs = null;
    }

    /**
     * Obtiene la lista de usuarios desde la base de datos.
     *
     * @return Una lista de objetos {@link UserVO} que representan los usuarios
     * en la base de datos.
     */
    public ArrayList<UserVO> getUsersList() {
        ArrayList<UserVO> userList = new ArrayList<>();
        String query = "SELECT * FROM clientes";  // Consulta para obtener todos los usuarios
        try {
            con = DBConnection.getConnection();  // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL
            rs = ps.executeQuery();  // Ejecuta la consulta y obtiene los resultados

            // Recorre el ResultSet y crea objetos UserVO con la información obtenida
            while (rs.next()) {
                UserVO user = new UserVO(
                        rs.getString("nombreUsuario"), // Obtiene el nombre de usuario
                        rs.getString("clave"), // Obtiene la clave del usuario
                        rs.getDouble("saldo"), // Obtiene el saldo del usuario
                        rs.getInt("idUsuario") // Obtiene el ID del usuario
                );
                userList.add(user);  // Agrega el objeto UserVO a la lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de excepciones
        } finally {
            closeResources();  // Cierra los recursos utilizados en la operación
        }

        return userList;  // Devuelve la lista de usuarios
    }

    /**
     * Modifica el valor del saldo de un usuario en la base de datos.
     *
     * @param modifiedUser El objeto {@link UserVO} con el saldo modificado.
     */
    public void modifyDebtValue(UserVO modifiedUser) {
        String query = "UPDATE clientes SET saldo = ? WHERE idUsuario = ?";  // Consulta para actualizar el saldo
        try {
            con = DBConnection.getConnection();  // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL

            // Establece los valores del PreparedStatement con los datos del usuario
            ps.setDouble(1, modifiedUser.getBalanceDue());  // Establece el saldo
            ps.setInt(2, modifiedUser.getId());  // Establece el ID del usuario

            ps.executeUpdate();  // Ejecuta la actualización
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de excepciones
        } finally {
            closeResources();  // Cierra los recursos utilizados en la operación
        }
    }

    /**
     * Cierra los recursos utilizados en las operaciones de base de datos.
     * Asegura que el ResultSet, PreparedStatement y Connection se cierren para
     * liberar recursos y evitar fugas de memoria.
     */
    private void closeResources() {
        try {
            if (rs != null) {
                rs.close();  // Cierra el ResultSet si no es nulo
            }
            if (ps != null) {
                ps.close();  // Cierra el PreparedStatement si no es nulo
            }
            if (con != null) {
                con.close();  // Cierra la conexión si no es nula
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de excepciones opcional
        }
    }
}
