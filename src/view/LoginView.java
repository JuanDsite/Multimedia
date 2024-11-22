package view;

import java.awt.*;
import javax.swing.*;

/**
 * Clase que representa la vista de inicio de sesión de la aplicación. Esta
 * clase extiende JFrame y permite al usuario ingresar sus credenciales de
 * acceso. La vista también incluye campos para ingresar parámetros de conexión
 * a una base de datos.
 */
public class LoginView extends JFrame {

    // Componentes de la interfaz de usuario
    private final JTextField userField;
    private final JPasswordField passwordField;
    private final JTextField ipAddressField;
    private final JTextField portField;
    private final JTextField urlField;
    private final JTextField userDBField;
    private final JTextField passwordDBField;
    private final JButton loginButton;
    private final JLabel userLabel;
    private final JLabel passwordLabel;
    private final JLabel messageLabel;
    private final JLabel ipAddressLabel;
    private final JLabel portLabel;
    private final JLabel urlLabel;
    private final JLabel userDBLabel;
    private final JLabel passwordDBLabel;

    /**
     * Constructor de la clase LoginView. Configura la ventana principal, los
     * componentes de la interfaz y sus disposiciones en el layout.
     */
    public LoginView() {
        // Configuración del JFrame
        setTitle("Inicio de Sesión");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new GridBagLayout()); // Usamos GridBagLayout para organizar los componentes

        // Inicializar componentes
        userLabel = new JLabel("Usuario:");
        passwordLabel = new JLabel("Contraseña:");
        ipAddressLabel = new JLabel("Dirección IP del servidor");
        portLabel = new JLabel("Puerto de conexión");
        urlLabel = new JLabel("Url");
        userDBLabel = new JLabel("Usuario base de datos");
        passwordDBLabel = new JLabel("Contraseña base de datos");
        messageLabel = new JLabel(""); // Mensaje de éxito o error
        userField = new JTextField(15);
        passwordField = new JPasswordField(15);
        ipAddressField = new JTextField(15);
        portField = new JTextField(15);
        urlField = new JTextField(15);
        userDBField = new JTextField(15);
        passwordDBField = new JTextField(15);

        loginButton = new JButton("Iniciar Sesión");

        // Configuración del layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Añadir componentes al JFrame
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(ipAddressLabel, gbc);

        gbc.gridx = 1;
        add(ipAddressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(portLabel, gbc);

        gbc.gridx = 1;
        add(portField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(urlLabel, gbc);

        gbc.gridx = 1;
        add(urlField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(userDBLabel, gbc);

        gbc.gridx = 1;
        add(userDBField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(passwordDBLabel, gbc);

        gbc.gridx = 1;
        add(passwordDBField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        add(messageLabel, gbc);
    }

    /**
     * Obtiene el botón de inicio de sesión.
     *
     * @return El botón de inicio de sesión.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Obtiene el campo de texto para el nombre de usuario.
     *
     * @return El campo de texto para el nombre de usuario.
     */
    public JTextField getUserField() {
        return userField;
    }

    /**
     * Obtiene el campo de texto para la contraseña.
     *
     * @return El campo de texto para la contraseña.
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Obtiene el campo de texto para la dirección IP.
     *
     * @return El campo de texto para la dirección IP.
     */
    public JTextField getIpAddressField() {
        return ipAddressField;
    }

    /**
     * Obtiene el campo de texto para el puerto.
     *
     * @return El campo de texto para el puerto.
     */
    public JTextField getPortField() {
        return portField;
    }

    /**
     * Obtiene el campo de texto para la URL.
     *
     * @return El campo de texto para la URL.
     */
    public JTextField getUrl() {
        return urlField;
    }

    /**
     * Obtiene el campo de texto para el usuario de la base de datos.
     *
     * @return El campo de texto para el usuario de la base de datos.
     */
    public JTextField getUserDB() {
        return userDBField;
    }

    /**
     * Obtiene el campo de texto para la contraseña de la base de datos.
     *
     * @return El campo de texto para la contraseña de la base de datos.
     */
    public JTextField getPasswordDB() {
        return passwordDBField;
    }

    /**
     * Muestra un mensaje de éxito de inicio de sesión. Cambia el color del
     * mensaje a verde.
     */
    public void LogInMessage() {
        messageLabel.setText("Inicio de sesión exitoso.");
        messageLabel.setForeground(Color.GREEN);
    }

    /**
     * Muestra un mensaje de error de inicio de sesión. Cambia el color del
     * mensaje a rojo.
     */
    public void LogInMessageError() {
        messageLabel.setText("Usuario o contraseña incorrecta.");
        messageLabel.setForeground(Color.RED);
    }

    /**
     * Muestra un mensaje de error cuando la IP no es válida.
     */
    public void ipErrorMessage() {
        JOptionPane.showMessageDialog(null, "Ingrese un ip valido", "Error de formato", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un mensaje de error cuando los campos de IP o puerto están
     * vacíos.
     */
    public void emptyFieldsMessage() {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese la dirección IP y el puerto.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un mensaje de error cuando el puerto no es un número válido.
     */
    public void errFormatMessage() {
        JOptionPane.showMessageDialog(null, "El puerto debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de error cuando hay campos vacíos o nulos.
     */
    public void emptyMessageErr() {
        JOptionPane.showMessageDialog(null, "No debe dejar campos nulos", "Error de formato", JOptionPane.ERROR_MESSAGE);
    }
}
