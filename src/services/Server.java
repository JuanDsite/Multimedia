package services;

import java.net.ServerSocket;
import java.net.Socket;
import controller.UserValidator;
import java.io.*;

/**
 * La clase Server representa un servidor que acepta conexiones de clientes,
 * valida sus credenciales y gestiona las solicitudes de los clientes mediante
 * hilos. El servidor escucha las solicitudes en un puerto específico y maneja
 * la autenticación y procesamiento de cada cliente.
 */
public class Server {

    private UserValidator validation;  // Validador de usuarios
    private final int serverPort;  // Puerto del servidor
    private ServerSocket server = null;  // Socket del servidor
    private Socket client = null;  // Socket para la conexión con el cliente
    private PrintWriter salida = null;  // Flujo de salida para enviar datos al cliente
    private BufferedReader entrada = null;  // Flujo de entrada para recibir datos del cliente

    /**
     * Constructor de la clase Server.
     *
     * @param port El puerto en el que el servidor escuchará las conexiones
     * entrantes.
     */
    public Server(int port) {
        this.serverPort = port;
    }

    /**
     * Inicia el servicio del servidor. Intenta crear el servicio (puerto de
     * escucha) y manejar las solicitudes de los clientes.
     *
     * @return true si el servidor se inicia correctamente, false en caso
     * contrario.
     */
    public boolean iniciate() {
        if (!createService()) {
            return false;
        }
        return requestHandler();
    }

    /**
     * Crea el servicio de escucha del servidor en el puerto especificado.
     *
     * @return true si el servicio se crea correctamente, false en caso
     * contrario.
     */
    private boolean createService() {
        try {
            server = new ServerSocket(serverPort);
        } catch (IOException exc) {
            // Manejo de excepción sin imprimir en consola
            return false;
        }
        return true;
    }

    /**
     * Maneja las solicitudes entrantes de los clientes. Acepta conexiones,
     * valida las credenciales y gestiona las solicitudes en hilos separados.
     *
     * @return true si se gestionan las solicitudes correctamente, false si
     * ocurre un error.
     */
    private boolean requestHandler() {
        try {
            while (true) {
                client = server.accept();  // Acepta una nueva conexión de un cliente
                validation = new UserValidator();  // Crea el validador de usuarios
                entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));  // Flujo de entrada
                salida = new PrintWriter(client.getOutputStream(), true);  // Flujo de salida

                // Lee las credenciales del cliente
                String user = entrada.readLine();  // Lee el usuario
                String pass = entrada.readLine();  // Lee la contraseña      

                // Valida las credenciales
                if (validation.validateCredentials(user, pass)) {
                    salida.println("VALID");  // Respuesta válida
                    // Crea un hilo para gestionar la solicitud del cliente
                    ServerThread thread = new ServerThread(client, validation);
                    new Thread(thread).start();
                } else {
                    salida.println("INVALID");  // Respuesta inválida
                }
            }
        } catch (IOException exc) {
            // Manejo de excepción sin imprimir en consola
            closeServer();
            return false;
        }
    }

    /**
     * Cierra el servidor y las conexiones abiertas.
     *
     * @return true si se cierra el servidor correctamente, false en caso
     * contrario.
     */
    private boolean closeServer() {
        try {
            if (!client.isClosed()) {
                client.close();
            }
            if (!server.isClosed()) {
                server.close();
            }
        } catch (IOException exc) {
            // Manejo de excepción sin imprimir en consola
            return false;
        }
        // TODO: Implementar un mensaje de confirmación
        return true;
    }

    /**
     * Método principal que inicia el servidor en el puerto 5500.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Server server = new Server(5500);
        server.iniciate();
    }
}
