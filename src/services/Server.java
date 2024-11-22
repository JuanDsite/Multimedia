package services;

import java.net.ServerSocket;
import java.net.Socket;
import controller.UserValidator;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private UserValidator validation;
    private final int serverPort;
    private ServerSocket server = null;
    private Socket client = null;
    private PrintWriter salida = null;
    private BufferedReader entrada = null;

    public Server(int port) {
        this.serverPort = port;
    }

    public boolean iniciate() {
        if (!createService()) {
            return false;
        }
        return requestHandler();
    }
// ------------------------------------------------------------------------------------------------------------------------------ \\  

    private boolean createService() {
        try {
            server = new ServerSocket(serverPort);
        } catch (IOException exc) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, exc);
            return false;
        }
        return true;
    }

    private boolean requestHandler() {
        try {
            while (true) {
                client = server.accept();
                validation = new UserValidator();
                entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
                salida = new PrintWriter(client.getOutputStream(), true);

                String user = entrada.readLine();  // Lee usuario
                String pass = entrada.readLine();  // Lee contraseña      

                if (validation.validateCredentials(user, pass)) {
                    salida.println("VALID");
                    ServerThread thread = new ServerThread(client, validation);
                    new Thread(thread).start();
                } else {
                    salida.println("INVALID");
                    
                }
            }
        } catch (IOException exc) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, exc);
            closeServer();
            return false;
        }
    }

    private boolean closeServer() {
        try {
            if (!client.isClosed()) {
                client.close();
            }
            if (!server.isClosed()) {
                server.close();
            }

        } catch (IOException exc) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, exc);
            return false;
        } 
        return true;
    }
// -------------------------------------------------------------------------- \\  

    public static void main(String[] args) {
        Server server = new Server(5500);
        server.iniciate();
    }
}
