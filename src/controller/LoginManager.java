package controller;

import model.DBConnection;
import view.LoginView;
import java.awt.event.ActionEvent;
import services.Client;

/**
 * La clase LoginManager gestiona el flujo de autenticación de usuarios en la
 * aplicación. Proporciona la lógica necesaria para capturar y validar las
 * credenciales ingresadas por el usuario y establecer la conexión al servidor y
 * la base de datos.
 */
public class LoginManager {

    private final LoginView login; // Vista de inicio de sesión
    private final Client client; // Cliente que se conecta al servidor
    private String port; // Puerto para la conexión
    private String ip; // Dirección IP del servidor
    private String url; // URL de la base de datos
    private String userDB; // Usuario de la base de datos
    private String passwordDB; // Contraseña de la base de datos

    /**
     * Constructor de la clase LoginManager. Inicializa la vista de inicio de
     * sesión y configura los listeners necesarios.
     *
     * @param client Instancia del cliente que gestiona la conexión al servidor.
     */
    public LoginManager(Client client) {
        login = new LoginView();
        this.client = client;
        login.setVisible(true);
        addLoginListener();
    }

    /**
     * Agrega un ActionListener al botón de inicio de sesión en la vista.
     * Captura y valida los datos ingresados por el usuario.
     */
    private void addLoginListener() {
        login.getLoginButton().addActionListener((ActionEvent e) -> {
            // Obtén los valores de los campos de texto
            port = login.getPortField().getText();
            ip = login.getIpAddressField().getText();
            url = login.getUrl().getText();
            userDB = login.getUserDB().getText();
            passwordDB = login.getPasswordDB().getText();

            // Validar si los campos requeridos están vacíos
            if (port.isEmpty() || ip.isEmpty()) {
                login.emptyFieldsMessage();
                return;
            }
            
            // Validar campos vacios
            if (!url.equals("jdbc:mysql://localhost/mp3")) {
                login.ipErrorMessage();
                return;
            }
            
             if (!userDB.equals("root")) {
                login.ipErrorMessage();
                return;
            }
             
             if (!passwordDB.equals("")) {
                login.ipErrorMessage();
                return;
            }

            // Validar que la IP sea "localhost"
            if (!ip.equals("localhost") && !ip.equals("127.0.0.0")) {
                login.ipErrorMessage();
                return;
            }

            try {
                // Convertir el puerto a un número entero
                int portNumber = Integer.parseInt(port);
                client.setPort(portNumber);
                client.setIp(ip);
                client.iniciate();

                // Validar credenciales con el servidor
                if (client.credentials()) {
                    try {
                        Thread.sleep(1000); // Simular un retraso antes de proceder
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
                    } finally {
                        login.setVisible(false);
                        client.protocol(); // Proceder al protocolo del cliente
                    }
                } else {
                    login.LogInMessageError(); // Mostrar mensaje de error en caso de credenciales inválidas
                }
            } catch (NumberFormatException ex) {
                login.errFormatMessage(); // Mostrar mensaje de error si el puerto no es válido
            }
        });
    }

    /**
     * Obtiene la vista de inicio de sesión asociada al LoginManager.
     *
     * @return Instancia de {@link LoginView}.
     */
    public LoginView getLogin() {
        return login;
    }
}
