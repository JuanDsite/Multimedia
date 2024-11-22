package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SongUserDAO {

    private Connection con;  // Conexión a la base de datos
    private PreparedStatement ps;  // Preparación de la consulta SQL
    private ResultSet rs;

    public SongUserDAO() {
        con = null;
        ps = null;
        rs = null;
    }

    public void register(int idUser, int idSong) {

        String query = "INSERT INTO cancionescliente(idCliente, idCancion) VALUES (?,?) ";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idUser);
            ps.setInt(2, idSong);
            ps.executeUpdate();
        } catch (SQLException ex) {
            // Manejo de excepción          
        } finally {
            closeResources();
        }
    }

    public ArrayList<SongUserVO> getComparisonTabel(int idUser, int idSong) {

        ArrayList<SongUserVO> userSongList = new ArrayList<>();
        String query = "SELECT * FROM cancionescliente";
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                SongUserVO song = new SongUserVO(
                        rs.getInt("idCliente"),
                        rs.getInt("idCancion")
                );
                userSongList.add(song);
            }
        } catch (SQLException exc) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exc);
        } finally {
            closeResources();
        }
        return userSongList;
    }

    public ArrayList<SongVO> getDownloadedSongsByUser(int idUser) {
        ArrayList<SongVO> downloadedSongs = new ArrayList<>();
        String query = """
        SELECT c.idCancion, c.nombreCancion, c.url
        FROM canciones c
        INNER JOIN cancionescliente cc
        ON c.idCancion = cc.idCancion
        WHERE cc.idCliente = ?
    """;
        try {
            con = DBConnection.getConnection(); // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL
            ps.setInt(1, idUser); // Establece el parámetro dinámico (idCliente)
            rs = ps.executeQuery(); // Ejecuta la consulta y obtiene el resultado

            while (rs.next()) {
                // Crea un objeto SongVO usando su constructor
                SongVO song = new SongVO(
                        rs.getInt("idCancion"), // songId
                        rs.getString("nombreCancion"), // songName
                        rs.getString("url") // url
                );
                downloadedSongs.add(song); // Agrega la canción a la lista
            }
        } catch (SQLException exc) {
            // Manejo de excepciones
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, exc);
        } finally {
            closeResources(); // Asegura el cierre de recursos
        }
        return downloadedSongs; // Devuelve la lista de canciones descargadas
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
