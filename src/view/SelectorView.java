package view;

import controller.SelectorManager;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 * Vista principal para la selección de canciones en un sistema de descarga.
 * Esta clase proporciona la interfaz gráfica de usuario para interactuar con el
 * sistema, mostrando una tabla con los resultados, y botones para descargar
 * canciones, ver en el reproductor y pagar.
 */
public class SelectorView extends JFrame {

    private final JButton btnDownload; // Botón para realizar la consulta
    private final JButton btnPlayer;
    private final JTable resultTable; // Tabla para mostrar los resultados de la consulta
    private final DefaultTableModel tableModel; // Modelo de la tabla
    private final JComboBox<String> consultFilter; // ComboBox para seleccionar el tipo de consulta
    private final JTextField valorConsulta; // Campo de texto para ingresar el valor de consulta
    private final SelectorManager selectorManager;
    private final JButton btnPay;
    private final JMenuBar optionBar;
    private final JMenu helpOption;
    private final JMenu exitOption;
    private final JMenuItem help;
    private final JMenuItem exit;

    /**
     * Constructor de la clase ConsultaVistas. Inicializa la ventana y sus
     * componentes.
     *
     * @param selectorManager
     */
    public SelectorView(SelectorManager selectorManager) {

        this.selectorManager = selectorManager;
        setTitle("Selector de canciones");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new GridLayout(2, 2));
        panelSuperior.add(new JLabel("          Canciones Disponibles"));
        consultFilter = new JComboBox<>(new String[]{"Ingrese el id de la cancion"});
        panelSuperior.add(consultFilter);

        optionBar = new JMenuBar();
        this.setJMenuBar(optionBar);

        helpOption = new JMenu("Acerca de");
        optionBar.add(helpOption);

        exitOption = new JMenu("Ajustes");
        optionBar.add(exitOption);

        help = new JMenuItem("Cómo descargar una canción?");
        helpOption.add(help);

        exit = new JMenuItem("Cerrar sesión");
        exitOption.add(exit);

        panelSuperior.add(new JLabel());
        valorConsulta = new JTextField();
        panelSuperior.add(valorConsulta);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnDownload = new JButton("Descargar");
        btnPlayer = new JButton("Ver en reproductor");
        btnPay = new JButton("Pagar"); // Botón para pagar
        panelBotones.add(btnDownload);
        panelBotones.add(btnPlayer);
        panelBotones.add(btnPay); // Agregar botón de pago al panel

        String[] columnas = {"Id", "Nombre", "Referencia"};
        tableModel = new DefaultTableModel(columnas, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        help.addActionListener(e -> showHelpInfo());
    }

    /**
     * Obtiene el tipo de consulta seleccionado.
     *
     * @return Tipo de consulta seleccionado como String.
     */
    public String getTipoConsultaSeleccionada() {
        return (String) consultFilter.getSelectedItem();
    }

    /**
     * Obtiene el valor de consulta ingresado en el campo de texto.
     *
     * @return Valor de consulta como String.
     */
    public String getQueryValue() {
        return valorConsulta.getText();
    }

    /**
     * Obtiene el botón de consultar.
     *
     * @return JButton para realizar la consulta.
     */
    public JButton getBtnDownload() {
        return btnDownload;
    }

    /**
     * Obtiene el botón de ver en el reproductor.
     *
     * @return JButton para realizar la consulta.
     */
    public JButton getBtnPlayer() {
        return btnPlayer;
    }

    public JMenuItem getExitOption() {
        return exit;
    }

    /**
     * Obtiene el botón de pagar.
     *
     * @return JButton para realizar el pago.
     */
    public JButton getBtnPay() {
        return btnPay;
    }

    /**
     * Muestra los resultados en la tabla.
     *
     * @param resultados Lista de resultados a mostrar, donde cada fila es un
     * arreglo de objetos.
     */
    public void showResults(java.util.List<Object[]> resultados) {
        tableModel.setRowCount(0); // Limpia la tabla antes de mostrar nuevos resultados
        selectorManager.updateTable(resultados);
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param mensaje Mensaje de error a mostrar.
     */
    public void showErrorMessage(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje indicando que el tipo de consulta es inválido.
     */
    public void invalidQueryTypeMessage() {
        JOptionPane.showMessageDialog(null, "Ingrese una consulta correcta");
    }

    /**
     * Muestra un mensaje indicando que el valor de consulta es inválido.
     */
    public void invalidQueryMessage() {
        JOptionPane.showMessageDialog(null, "Ingrese un valor de consulta válido");
    }

    /**
     * Muestra un mensaje indicando que no se encontraron resultados.
     */
    public void invalidArrayMessage() {
        JOptionPane.showMessageDialog(null, "No se encontraron resultados.");
    }

    /**
     * Muestra un mensaje indicando que el usuario debe dinero.
     *
     * @param balanceDue
     */
    public void balanceDueMessage(double balanceDue) {
        JOptionPane.showMessageDialog(null, "Realice primero el pago por el valor de " + balanceDue);
    }

    /*
     * Muestra el mensaje de descrga de canción
     * 
     */
    public void showDownloadMessage() {
        JOptionPane.showMessageDialog(null, "Canción descargada ");
    }

    /**
     * Agrega un ActionListener al botón de consultar.
     *
     * @param listener ActionListener a agregar.
     */
    public void downloadButton(ActionListener listener) {
        btnDownload.addActionListener(listener);
    }

    /**
     * Agrega un ActionListener al botón de consultar.
     *
     * @param listener ActionListener a agregar.
     */
    public void playerListener(ActionListener listener) {
        btnPlayer.addActionListener(listener);
    }

    public void helpListener(ActionListener listener) {
        exit.addActionListener(listener);
    }

    /**
     * Obtiene el modelo de la tabla utilizado para gestionar los datos de la
     * tabla.
     *
     * @return El modelo de tabla {@link DefaultTableModel} que contiene los
     * datos y la estructura de la tabla.
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    
    
    /**
     * Acción para cerrar la sesión del usuario, mostrando su saldo.
     *
     * @param balance Saldo restante del usuario.
     */
    public void exit(String balance) {
        JOptionPane.showMessageDialog(null, "Se ha cerrado sesión exitosamente. Su saldo quedó con un total de $" + balance);
        System.exit(0);
    }


    /**
     * Muestra un mensaje indicando que la canción ya fue descargada.
     */
    public void songDownloadMessage() {
        JOptionPane.showMessageDialog(null, "Esta canción ya fue descargada anteriormente");
    }

    /**
     * Muestra la información de ayuda para el usuario.
     */
    public void showHelpInfo() {
        JOptionPane.showMessageDialog(null, """
                                            Para descargar una cancion, debes seleccionarla por su id correspondiente en el campo
                                            de consulta.
                                            En caso de que exista una deuda o saldo pendiente, hay que pagar dicha cantidad
                                            para poder continuar con las demas descargas.""");
    }
    
    
    /**
     * Muestra un mensaje de error cuando no hay canciones descargadas.
     */
    public void noSongDonwloadedError() {
        JOptionPane.showMessageDialog(null, "No has descargado ninguna Canción.");

    }

    /**
     * Muestra un mensaje indicando que el pago fue exitoso.
     */
    public void paymentSuccess() {
        JOptionPane.showMessageDialog(null, "El pago se hizo exitosamente");
    }
}