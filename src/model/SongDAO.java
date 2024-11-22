package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SongDAO {

    private Connection con;  // Conexión a la base de datos
    private PreparedStatement ps;  // Preparación de la consulta SQL
    private ResultSet rs;

    public SongDAO() {
        con = null;
        ps = null;
        rs = null;
    }

    public ArrayList<SongVO> getSongs() {

        ArrayList<SongVO> listaCanciones = new ArrayList<>();
        String query = "SELECT * FROM canciones";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                SongVO cancion = new SongVO(
                        rs.getInt("idCancion"),
                        rs.getString("nombreCancion"),
                        rs.getString("url")
                );
                listaCanciones.add(cancion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeResources();
        }
        return listaCanciones;
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
        } catch (SQLException ex) {
            // Manejo de excepciones opcional, como imprimir el error o registrarlo en un log
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
