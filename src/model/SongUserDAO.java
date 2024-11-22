package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase encargada de manejar las operaciones de base de datos relacionadas con
 * las canciones de los usuarios. Permite registrar descargas de canciones por
 * parte de los usuarios, verificar si una canción ya ha sido descargada, y
 * obtener la lista de canciones descargadas por un usuario específico.
 */
public class SongUserDAO {

    private Connection con;  // Conexión a la base de datos
    private PreparedStatement ps;  // Preparación de la consulta SQL
    private ResultSet rs;  // Resultado de la consulta SQL

    /**
     * Constructor de la clase SongUserDAO. Inicializa las variables de conexión
     * y consulta de la base de datos.
     */
    public SongUserDAO() {
        con = null;
        ps = null;
        rs = null;
    }

    /**
     * Registra una canción descargada por un usuario en la base de datos.
     * Inserta una relación entre el id del usuario y el id de la canción.
     *
     * @param idUser El ID del usuario que descarga la canción.
     * @param idSong El ID de la canción descargada.
     */
    public void register(int idUser, int idSong) {
        String query = "INSERT INTO cancionescliente(idCliente, idCancion) VALUES (?,?) ";
        try {
            con = DBConnection.getConnection();  // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL
            ps.setInt(1, idUser);  // Establece el ID del usuario
            ps.setInt(2, idSong);  // Establece el ID de la canción
            ps.executeUpdate();  // Ejecuta la consulta para insertar los datos
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de excepciones
        } finally {
            closeResources();  // Asegura el cierre de recursos
        }
    }

    /**
     * Obtiene la lista de registros de canciones descargadas por los usuarios.
     *
     * @param idUser El ID del usuario cuya descarga se quiere verificar.
     * @param idSong El ID de la canción cuya descarga se quiere verificar.
     * @return Una lista de objetos {@link SongUserVO} que representan las
     * canciones descargadas por el usuario.
     */
    public ArrayList<SongUserVO> getComparisonTabel(int idUser, int idSong) {
        ArrayList<SongUserVO> userSongList = new ArrayList<>();
        String query = "SELECT * FROM cancionescliente";  // Consulta para obtener todas las relaciones usuario-canción
        try {
            con = DBConnection.getConnection();  // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL
            rs = ps.executeQuery();  // Ejecuta la consulta y obtiene el resultado

            while (rs.next()) {
                // Crea un objeto SongUserVO usando su constructor
                SongUserVO song = new SongUserVO(
                        rs.getInt("idCliente"), // ID del usuario
                        rs.getInt("idCancion") // ID de la canción
                );
                userSongList.add(song);  // Agrega la relación a la lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de excepciones
        } finally {
            closeResources();  // Asegura el cierre de recursos
        }
        return userSongList;  // Devuelve la lista de registros de canciones descargadas
    }

    /**
     * Obtiene la lista de canciones descargadas por un usuario específico.
     *
     * @param idUser El ID del usuario cuya lista de canciones descargadas se
     * quiere obtener.
     * @return Una lista de objetos {@link SongVO} que representan las canciones
     * descargadas por el usuario.
     */
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
            con = DBConnection.getConnection();  // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL
            ps.setInt(1, idUser);  // Establece el parámetro dinámico (idCliente)
            rs = ps.executeQuery();  // Ejecuta la consulta y obtiene el resultado

            while (rs.next()) {
                // Crea un objeto SongVO usando su constructor
                SongVO song = new SongVO(
                        rs.getInt("idCancion"), // ID de la canción
                        rs.getString("nombreCancion"), // Nombre de la canción
                        rs.getString("url") // URL de la canción
                );
                downloadedSongs.add(song);  // Agrega la canción a la lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de excepciones
        } finally {
            closeResources();  // Asegura el cierre de recursos
        }

        return downloadedSongs;  // Devuelve la lista de canciones descargadas
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
            ex.printStackTrace();  // Manejo de excepciones
        }
    }
}
