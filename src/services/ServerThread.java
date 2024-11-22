package services;

import controller.SongSolicitor;
import controller.SongValidator;
import controller.UserValidator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase ServerThread gestiona la comunicación con un cliente conectado al
 * servidor. Esta clase maneja las solicitudes del cliente, valida pagos,
 * descarga canciones y envía respuestas. Cada instancia de ServerThread se
 * ejecuta en un hilo separado para cada cliente conectado.
 */
public class ServerThread implements Runnable {

    private final Socket clientHandling;  // Socket para la conexión del cliente
    private final UserValidator validatorUser;  // Validador de usuarios
    private final SongValidator validatorSong;  // Validador de canciones
    private SongSolicitor songSolicitor;  // Solicitud de canciones
    private PrintWriter output = null;  // Flujo de salida para enviar datos al cliente
    private BufferedReader input = null;  // Flujo de entrada para recibir datos del cliente
    private ObjectOutputStream outputStream = null;  // Flujo de salida para objetos
    private boolean outstandingBalance;  // Indica si el usuario tiene saldo pendiente

    /**
     * Constructor de la clase ServerThread.
     *
     * @param clientHandling El socket que maneja la conexión con el cliente.
     * @param validator El validador de usuarios.
     */
    public ServerThread(Socket clientHandling, UserValidator validator) {
        this.clientHandling = clientHandling;
        this.validatorUser = validator;
        this.validatorSong = new SongValidator();
    }

    /**
     * Método que se ejecuta al iniciar el hilo. Se encargará de gestionar la
     * comunicación con el cliente.
     */
    @Override
    public void run() {
        if (getOutputStream() && getInputStream() && getObjectOutputStream()) {
            checkBalance();
            protocol();
        }
    }

    /**
     * Establece el flujo de entrada desde el socket del cliente.
     *
     * @return true si se establece correctamente, false en caso contrario.
     */
    private boolean getInputStream() {
        try {
            input = new BufferedReader(new InputStreamReader(clientHandling.getInputStream()));
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    /**
     * Establece el flujo de salida al socket del cliente.
     *
     * @return true si se establece correctamente, false en caso contrario.
     */
    private boolean getOutputStream() {
        try {
            output = new PrintWriter(clientHandling.getOutputStream(), true);
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    /**
     * Establece el flujo de salida de objetos al socket del cliente.
     *
     * @return true si se establece correctamente, false en caso contrario.
     */
    private boolean getObjectOutputStream() {
        try {
            outputStream = new ObjectOutputStream(clientHandling.getOutputStream());
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    // ------------------------------------------------------------------------------------------------------------------------------
    /**
     * Verifica si el usuario tiene saldo pendiente.
     *
     * @return true si el usuario tiene saldo pendiente, false en caso
     * contrario.
     */
    private boolean checkBalance() {
        outstandingBalance = validatorUser.checkBalance();
        return outstandingBalance;
    }

    /**
     * Maneja el protocolo de comunicación con el cliente. Envía la lista de
     * canciones disponibles y maneja las solicitudes del cliente.
     */
    private void protocol() {
        try {
            sendSongs();  // Envía la lista de canciones
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Error en el protocolo de comunicación", ex);
        }
    }

    /**
     * Envía la lista de canciones disponibles al cliente.
     *
     * @throws IOException Si ocurre un error al enviar los datos.
     */
    private void sendSongs() throws IOException {
        songSolicitor = new SongSolicitor();
        sendObjectResponse(songSolicitor.returnSongs());
        handleRequests();  // Espera y maneja solicitudes específicas del cliente
    }

    /**
     * Maneja las solicitudes del cliente en un bucle.
     */
    private void handleRequests() {
        String request;
        while (true) {
            request = read();
            if ("download request".equals(request)) {
                validateSongs();
                break;
            }
            if ("payment request".equals(request)) {
                validatePayment();
                break;
            }
            if ("log out".equals(request)) {
                validateExit();
                break;
            }
            if ("show songs".equals(request)) {
                sendDownloadedSongs();
                break;
            }
        }
    }

    /**
     * Valida y maneja la solicitud de descarga de canciones.
     */
    private void validateSongs() {
        String songSended = read();
        int idSong = validatorSong.getId(songSended);
        int idUser = validatorUser.getCurrentLoggedUser();
        if (!songSended.isEmpty() && outstandingBalance != false) {
            if (validatorSong.checkDoubleDownload(idUser, idSong)) {
                writeResponse("song has already been downloaded");
            } else {
                validatorUser.validateBalance(); // Actualiza el saldo en la base de datos al descargar la canción
                validatorSong.manageSongUser(songSended, idUser);  // Inserta en la tabla intermedia de canciones descargadas
                writeResponse("User with no due balance");
            }
        } else {
            double balanceDue = validatorUser.getBalance();
            String balance = Double.toString(balanceDue);
            writeResponse(balance);
        }
        handleRequests();
    }

    /**
     * Valida y procesa la solicitud de pago.
     */
    private void validatePayment() {
        if (validatorUser.validatePayment()) {
            writeResponse("processing payment");
            outstandingBalance = true;
        } else {
            double balanceDuePayment = validatorUser.getBalance();
            String balance = Double.toString(balanceDuePayment);
            writeResponse(balance);
        }
        handleRequests();
    }

    /**
     * Valida y maneja la solicitud de cierre de sesión.
     */
    private void validateExit() {
        double balance = validatorUser.getBalance();
        String finalBalance = Double.toString(balance);
        writeResponse(finalBalance);
        finish();
    }

    /**
     * Envía las canciones descargadas del usuario al cliente.
     */
    private void sendDownloadedSongs() {
        int idUser = validatorUser.getCurrentLoggedUser();
        sendObjectResponse(validatorSong.returnSongDownloadedInfo(idUser));  // Envía la información de las canciones descargadas
        handleRequests();
    }

    // ------------------------------------------------------------------------------------------------------------------------------
    /**
     * Lee una línea de la entrada del cliente.
     *
     * @return La línea leída del cliente.
     */
    @SuppressWarnings("empty-statement")
    private String read() {
        String temp = null;
        try {
            while ((temp = input.readLine()) == null);
        } catch (IOException e) {
            finish();
        }
        return temp;
    }

    /**
     * Escribe una respuesta en el flujo de salida al cliente.
     *
     * @param message El mensaje a enviar al cliente.
     */
    private void writeResponse(String message) {
        output.println(message);
    }

    /**
     * Envía un objeto como respuesta al cliente.
     *
     * @param object El objeto a enviar al cliente.
     */
    private void sendObjectResponse(Object object) {
        try {
            outputStream.writeObject(object);  // Envía el objeto al cliente
            outputStream.flush();
        } catch (IOException exc) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Error en el protocolo de comunicación", exc);
        }
    }

    /**
     * Finaliza la conexión con el cliente y cierra los flujos de entrada y
     * salida.
     *
     * @return true si se cierra correctamente, false en caso contrario.
     */
    private boolean finish() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (!clientHandling.isClosed()) {
                clientHandling.close();
            }
        } catch (IOException exc) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Error en el protocolo de comunicación", exc);
            return false;
        }
        return true;
    }
}
