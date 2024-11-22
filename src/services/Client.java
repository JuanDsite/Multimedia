package services;

import controller.LoginManager;
import controller.SelectorManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import model.SongVO;

/**
 * La clase Client representa a un cliente que se conecta a un servidor para
 * gestionar la autenticación, la descarga de canciones y la gestión de pagos.
 * Contiene la lógica para conectar con el servidor, enviar y recibir datos, y
 * manejar la interfaz del usuario.
 */
public class Client {

    private final String host = null;  // El host del servidor
    private int port;  // El puerto de conexión del servidor
    private String ip;  // La dirección IP del servidor
    private Socket socket = null;  // Socket para la conexión con el servidor
    private BufferedReader input = null;  // Flujo de entrada para recibir datos del servidor
    private PrintWriter output = null;  // Flujo de salida para enviar datos al servidor
    private final LoginManager loginManager;  // Gestor de inicio de sesión
    private final SelectorManager selectorManager;  // Gestor de selección de canciones
    private ClientSongHandler songHandler;  // Manejador de canciones del cliente
    private ArrayList<SongVO> arraySong;  // Lista de canciones disponibles en el servidor
    private ArrayList<SongVO> downloadedArray;  // Lista de canciones descargadas

    /**
     * Constructor de la clase Client. Inicializa los objetos necesarios para la
     * gestión de la conexión y la interfaz.
     */
    public Client() {
        this.loginManager = new LoginManager(this);
        this.selectorManager = new SelectorManager(this);
    }

    /**
     * Inicia el proceso de conexión y configuración del cliente.
     *
     * @return true si la conexión y la configuración de los flujos de entrada y
     * salida son exitosas, false en caso contrario.
     */
    public boolean iniciate() {
        if (!connect()) {
            return false;
        }
        return !(!getOutputStream() || !getInputStream());
    }

    /**
     * Establece la conexión con el servidor.
     *
     * @return true si la conexión es exitosa, false en caso contrario.
     */
    private boolean connect() {
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException exc) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Establece el flujo de salida para enviar datos al servidor.
     *
     * @return true si el flujo de salida se establece correctamente, false en
     * caso contrario.
     */
    private boolean getOutputStream() {
        try {
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    /**
     * Establece el flujo de entrada para recibir datos del servidor.
     *
     * @return true si el flujo de entrada se establece correctamente, false en
     * caso contrario.
     */
    private boolean getInputStream() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Envía las credenciales de inicio de sesión al servidor y espera la
     * validación.
     *
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean credentials() {
        output.println(loginManager.getLogin().getUserField().getText());
        output.println(new String(loginManager.getLogin().getPasswordField().getPassword()));
        String response = read();
        return "VALID".equals(response);
    }

    /**
     * Inicia el protocolo de interacción con el servidor, gestionando la
     * descarga de canciones y la interfaz de usuario.
     */
    public void protocol() {
        songHandler = new ClientSongHandler(socket);
        arraySong = new ArrayList<>();
        arraySong = songHandler.readSongList();
        selectorManager.showSongs();
        selectorManager.manageQuery();
    }

    /**
     * Obtiene la lista de canciones disponibles en el servidor.
     *
     * @return Lista de objetos SongVO que representan las canciones
     * disponibles.
     */
    public ArrayList<SongVO> getArraySong() {
        return arraySong;
    }

    /**
     * Gestiona el proceso de descarga de una canción, enviando la solicitud al
     * servidor y recibiendo la respuesta.
     *
     * @param songName El nombre de la canción a descargar.
     */
    public void songDownloading(String songName) {
        output.println("download request");
        output.println(songName);
        output.flush();

        String responseDownload = read();

        switch (responseDownload) {
            case "User with no due balance" ->
                selectorManager.showDownloadMessage();
            case "song has already been downloaded" ->
                selectorManager.showSongAlreadyDownloaded();
            default -> {
                double balanceDue = Double.parseDouble(responseDownload);
                selectorManager.showDueMessage(balanceDue);
            }
        }
    }

    /**
     * Solicita el pago de una deuda al servidor. Si el usuario tiene saldo
     * pendiente, muestra un mensaje indicando el monto a pagar.
     */
    public void payment() {
        output.println("payment request");
        output.flush();

        String responsePay = read();

        if (responsePay.equals("processing payment")) {
            selectorManager.showPaymentMessage();
        } else {
            double balancePayment = Double.parseDouble(responsePay);
            selectorManager.showDueMessage(balancePayment);
        }
    }

    /**
     * Solicita la lista de canciones descargadas al servidor.
     */
    public void songRequest() {
        downloadedArray = new ArrayList<>();
        songHandler.setDataList(downloadedArray);
        output.println("show songs");
        output.flush();
        downloadedArray = songHandler.readSongList();
        selectorManager.receiveDownloadedSongs(downloadedArray);
    }

    /**
     * Finaliza la sesión del cliente, enviando la solicitud de cierre de sesión
     * al servidor.
     */
    public void endOfSession() {
        output.println("log out");
        output.flush();

        String response = read();
        selectorManager.finishSession(response);
    }

    /**
     * Establece el puerto del servidor.
     *
     * @param port El puerto del servidor.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Establece la dirección IP del servidor.
     *
     * @param ip La dirección IP del servidor.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Obtiene la dirección IP del servidor.
     *
     * @return La dirección IP del servidor.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Obtiene el SelectorManager que gestiona la selección de canciones.
     *
     * @return El SelectorManager.
     */
    public SelectorManager getSelectorManager() {
        return selectorManager;
    }

    /**
     * Lee la respuesta del servidor.
     *
     * @return La respuesta leída del servidor.
     */
    @SuppressWarnings("empty-statement")
    private String read() {
        String temp = null;
        try {
            while ((temp = input.readLine()) == null);
        } catch (IOException e) {
            // Manejo de error opcional
        }
        return temp;
    }

    /**
     * Finaliza la conexión cerrando los flujos de entrada, salida y el socket.
     *
     * @return true si la desconexión es exitosa, false en caso contrario.
     */
    public boolean finish() {
        try {
            input.close();
            output.close();
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    /**
     * Método principal que inicia el cliente.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.iniciate();
    }
}
