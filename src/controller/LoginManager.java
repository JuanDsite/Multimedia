package controller;

import model.DBConnection;
import view.LoginView;
import java.awt.event.ActionEvent;
import services.Client;

public class LoginManager {

    private final LoginView login;
    private final Client client;
    private DBConnection dbConnection;
    private String port;
    private String ip;
    private String url;
    private String userDB;
    private String passwordDB;

    public LoginManager(Client client) {
        login = new LoginView();
        this.client = client;
        login.setVisible(true);
        addLoginListener();
    }

    private void addLoginListener() {
        login.getLoginButton().addActionListener((ActionEvent e) -> {
            // Obtén los valores de los campos
            port = login.getPortField().getText();
            ip = login.getIpAddressField().getText();
            url = login.getUrl().getText();
            userDB = login.getUserDB().getText();
            passwordDB = login.getPasswordDB().getText();

            // Crear la conexión a la base de datos con los valores ingresados
            // Validación de campos vacíos
            if (port.isEmpty() || ip.isEmpty()) {
                login.emptyFieldsMessage();
                return; // Sale del método si algún campo está vacío
            }
            if (!ip.equals("localhost") && !ip.equals("127.0.0.0")) {
                login.ipErrorMessage();
                return;
            }

            try {
                int portNumber = Integer.parseInt(port);
                client.setPort(portNumber);
                client.setIp(ip);
                client.iniciate();

                if (client.credentials()) {
                    login.LogInMessage();
                    login.setVisible(false);
                    client.protocol();
                } else {
                    login.LogInMessageError();
                }
            } catch (NumberFormatException ex) {
                login.errFormatMessage();
            }
        });
    }

    public LoginView getLogin() {
        return login;
    }
}
