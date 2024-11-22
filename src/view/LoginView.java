package view;

import java.awt.*;
import javax.swing.*;

public class LoginView extends JFrame {

    // interface components
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
    
    
    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUserField() {
        return userField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JTextField getIpAddressField() {
        return ipAddressField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JTextField getUrl() {
        return urlField;
    }

    public JTextField getUserDB() {
        return userDBField;
    }

    public JTextField getPasswordDB() {
        return passwordDBField;
    }
    
    

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

    public void LogInMessage() {
        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
    }

    public void LogInMessageError() {
        messageLabel.setText("Usuario o contraseña incorrecta.");
        messageLabel.setForeground(Color.RED);
    }
    
    public void ipErrorMessage(){
        JOptionPane.showMessageDialog(null, "Ingrese un ip valido", "Error de formato", JOptionPane.WARNING_MESSAGE);
    }
    
    public void emptyFieldsMessage(){
        JOptionPane.showMessageDialog(null, "Por favor, ingrese la dirección IP y el puerto.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
    }
    
    public void errFormatMessage(){
        JOptionPane.showMessageDialog(null, "El puerto debe ser un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
    }
    
    public void emptyMessageErr(){
        JOptionPane.showMessageDialog(null, "No debe dejar campos nulos", "Error de formato", JOptionPane.ERROR_MESSAGE);
    }
}