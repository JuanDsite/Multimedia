package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar las operaciones relacionadas con las canciones
 * en la base de datos. Proporciona métodos para obtener la lista de canciones y
 * gestionar la conexión con la base de datos.
 */
public class SongDAO {

    private Connection con;  // Conexión a la base de datos
    private PreparedStatement ps;  // Preparación de la consulta SQL
    private ResultSet rs;  // Conjunto de resultados de las consultas

    /**
     * Constructor de la clase SongDAO. Inicializa las variables utilizadas para
     * gestionar la conexión y las consultas a la base de datos.
     */
    public SongDAO() {
        con = null;
        ps = null;
        rs = null;
    }

    /**
     * Obtiene una lista de todas las canciones almacenadas en la base de datos.
     *
     * @return Una lista de objetos {@link SongVO} que representan las canciones
     * disponibles.
     */
    public ArrayList<SongVO> getSongs() {
        ArrayList<SongVO> listaCanciones = new ArrayList<>();
        String query = "SELECT * FROM canciones";

        try {
            con = DBConnection.getConnection(); // Obtiene la conexión a la base de datos
            ps = con.prepareStatement(query);  // Prepara la consulta SQL
            rs = ps.executeQuery();  // Ejecuta la consulta

            // Itera sobre los resultados y crea objetos SongVO
            while (rs.next()) {
                SongVO cancion = new SongVO(
                        rs.getInt("idCancion"), // ID de la canción
                        rs.getString("nombreCancion"), // Nombre de la canción
                        rs.getString("url") // URL de la canción
                );
                listaCanciones.add(cancion); // Agrega la canción a la lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo de excepciones para errores SQL
        } finally {
            closeResources(); // Cierra los recursos utilizados
        }

        return listaCanciones;
    }

    /**
     * Cierra los recursos utilizados en las operaciones de base de datos.
     * Asegura que el {@link ResultSet}, {@link PreparedStatement} y
     * {@link Connection} se cierren para liberar recursos y evitar fugas de
     * memoria.
     */
    private void closeResources() {
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
            ex.printStackTrace();
        }
    }
}
