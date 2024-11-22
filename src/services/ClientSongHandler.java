package services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SongVO;

/**
 * Clase encargada de manejar las canciones recibidas del servidor en el
 * cliente. Proporciona métodos para leer y almacenar una lista de canciones
 * enviadas desde el servidor.
 */
public class ClientSongHandler {

    private ObjectInputStream inputStream;  // Flujo de entrada para recibir datos del servidor
    private ArrayList<SongVO> dataList;  // Lista de canciones recibidas
    private Socket socket;  // Socket de conexión con el servidor

    /**
     * Constructor de la clase ClientSongHandler. Inicializa la conexión al
     * servidor mediante un socket y configura el flujo de entrada para recibir
     * datos.
     *
     * @param socket El socket de conexión con el servidor.
     */
    public ClientSongHandler(Socket socket) {
        this.socket = socket;
        try {
            // Crea un ObjectInputStream para leer objetos desde el socket
            inputStream = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            // Manejo de excepciones si ocurre un error al crear el flujo de entrada
            System.out.println("Problemas creando un nuevo input" + e.getMessage());
        }
    }

    /**
     * Lee una lista de canciones del servidor a través del ObjectInputStream.
     *
     * @return Una lista de objetos {@link SongVO} que representan las canciones
     * recibidas del servidor.
     */
    public ArrayList<SongVO> readSongList() {
        try {
            // Lee un objeto y lo convierte en una lista de canciones
            dataList = (ArrayList<SongVO>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            // Manejo de excepciones si ocurre un error al leer el objeto
            Logger.getLogger(ClientSongHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataList;  // Devuelve la lista de canciones
    }

    /**
     * Establece la lista de canciones recibida.
     *
     * @param dataList La lista de canciones a establecer.
     */
    public void setDataList(ArrayList<SongVO> dataList) {
        this.dataList = dataList;
    }

    /**
     * Obtiene la lista de canciones almacenada.
     *
     * @return La lista de canciones almacenada en el cliente.
     */
    public ArrayList<SongVO> getDataList() {
        return dataList;
    }
}
