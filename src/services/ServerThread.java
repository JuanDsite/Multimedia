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
 * Esta clase implementa un hilo de servidor que maneja la comunicación con un
 * cliente. El hilo establece un protocolo de comunicación en el que se
 * gestionan las solicitudes de canciones del cliente y se envían respuestas al
 * mismo.
 *
 * La clase maneja la entrada y salida de datos del cliente a través de un
 * socket, y se asegura de cerrar adecuadamente las conexiones al finalizar.
 */
public class ServerThread implements Runnable {

    private final Socket clientHandling;
    private final UserValidator validatorUser;
    private final SongValidator validatorSong;
    private SongSolicitor songSolicitor;
    private PrintWriter output = null;
    private BufferedReader input = null;
    private ObjectOutputStream outputStream = null;
    private boolean outstandingBalance;

    public ServerThread(Socket clientHandling, UserValidator validator) {
        this.clientHandling = clientHandling;
        this.validatorUser = validator;
        this.validatorSong = new SongValidator();
    }

    @Override
    public void run() {
        if (getOutputStream() && getInputStream() && getObjectOutputStream()) {
            checkBalance();
            protocol();
        }
    }

    private boolean getInputStream() {
        try {
            input = new BufferedReader(new InputStreamReader(clientHandling.getInputStream()));
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    private boolean getOutputStream() {
        try {
            output = new PrintWriter(clientHandling.getOutputStream(), true);
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    private boolean getObjectOutputStream() {
        try {
            outputStream = new ObjectOutputStream(clientHandling.getOutputStream());
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

// ------------------------------------------------------------------------------------------------------------------------------ \\
    private boolean checkBalance() {
        outstandingBalance = validatorUser.checkBalance();
        return outstandingBalance;
    }

    private void protocol() {
        try {
            sendSongs();  // Envía la lista de canciones
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Error en el protocolo de comunicación", ex);
        }
    }

    private void sendSongs() throws IOException {
        songSolicitor = new SongSolicitor();
        sendObjectResponse(songSolicitor.returnSongs());
        handleRequests();  // Espera y maneja solicitudes específicas del cliente
    }

    /**
     * Método para manejar solicitudes en un bucle de espera.
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
                sendStoredSongs();
                break;
            }
        }
    }

    /**
     * Valida la solicitud de descarga.
     */
    private void validateSongs() {
        String songSended = read();
        int idSong = validatorSong.getId(songSended);
        int idUser = validatorUser.getCurrentLoggedUser();
        if (!songSended.isEmpty() && outstandingBalance != false) {
            if (validatorSong.checkDoubleDownload(idUser, idSong)) {
                writeResponse("song has already been downloaded");
            } else {
                validatorUser.validateBalance(); //Aquí se descarga la canción actualizando el valor del saldo en la base de datos 
                /*
                 * Agrega el id del usuario y el id de la cancion a la tabla temporal 
                 * en la que se almacena la info de la cancion que se ha descargado
                 */
                validatorSong.manageSongUser(songSended, idUser);      
                writeResponse("User with no due balance"); //Se devuelve la respuesta de que el usuario ya no tiene saldo pendiente.}
            }
        } else {
            // En caso de que el saldo pendiente sea true, se responde con la cantidad de saldo que debe el usuario
            double balanceDue = validatorUser.getBalance();
            String balance = Double.toString(balanceDue);
            writeResponse(balance);
        }
        handleRequests();// se vuelve a llamar a HandleRequest para seguir escuchando peticiones
    }

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

    private void validateExit() {
        double balance = validatorUser.getBalance();
        String finalBalance = Double.toString(balance);
        writeResponse(finalBalance);
        finish();
    }
    
    
    private void sendStoredSongs(){
        int idUser = validatorUser.getCurrentLoggedUser();
        sendObjectResponse(validatorSong.returnSongDownloadedInfo(idUser)); // Aquí se procede con la logica de mandar la info de las canciones descargadas     
        handleRequests();
            
    }
// ------------------------------------------------------------------------------------------------------------------------------ \\  

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

    private void writeResponse(String message) {
        output.println(message);
    }

    private void sendObjectResponse(Object object) {
        try {
            outputStream.writeObject(object); // Envía la lista de canciones
            outputStream.flush();
        } catch (IOException exc) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Error en el protocolo de comunicación", exc);
        }
    }

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
