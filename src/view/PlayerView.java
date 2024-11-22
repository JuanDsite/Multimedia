package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * La vista principal del reproductor de música, que gestiona la interfaz
 * gráfica. Esta clase permite al usuario interactuar con los botones de control
 * del reproductor y visualizar la lista de canciones disponibles.
 */
public class PlayerView extends JFrame {

    private final JButton playButton; // Botón de reproducción
    private final JButton stopButton; // Botón de detener
    private final JButton nextButton; // Botón de siguiente canción
    private final JButton prevButton; // Botón de canción anterior
    private final JButton backButton; // Botón para mostrar la selección de canciones
    private final JPanel songListPanel; // Panel para mostrar la lista de canciones
    private final JMenuBar bar; // Barra de menú
    private final JMenu menu; // Menú principal
    private final JMenuItem item; // Opción "Acerca de" del menú

    /**
     * Constructor de la clase PlayerView. Inicializa la ventana principal del
     * reproductor de música y configura los componentes gráficos necesarios.
     */
    public PlayerView() {
        // Configuración del JFrame
        setTitle("Reproductor de Música Simple");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Crear botones con iconos
        playButton = createButtonWithIcon("media/Icons/repeat.png", 50, 50);
        stopButton = createButtonWithIcon("media/Icons/stop.png", 50, 50);
        nextButton = createButtonWithIcon("media/Icons/next.png", 50, 50);
        prevButton = createButtonWithIcon("media/Icons/prev.png", 50, 50);
        backButton = new JButton("Mostrar selección");

        // Configuración de la barra de menú
        bar = new JMenuBar();
        this.setJMenuBar(bar);
        menu = new JMenu("AYUDA");
        bar.add(menu);
        item = new JMenuItem("Acerca de");
        menu.add(item);

        // Crear y configurar el panel de botones
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(prevButton);
        buttonsPanel.add(playButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.add(backButton);

        // Agregar el panel de botones a la ventana
        this.add(buttonsPanel, BorderLayout.SOUTH);

        // Configuración del panel para la lista de canciones
        songListPanel = new JPanel();
        songListPanel.setLayout(new BoxLayout(songListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(songListPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Listener para la opción "Acerca de"
        item.addActionListener(e -> showInfo());
    }

    /**
     * Crea un botón con un icono personalizado.
     *
     * @param iconPath Ruta del icono.
     * @param width Ancho del botón.
     * @param height Alto del botón.
     * @return Instancia de {@link JButton} configurada con el icono.
     */
    private JButton createButtonWithIcon(String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Actualiza la lista de canciones mostrada en la interfaz.
     *
     * @param songNames Lista de nombres de canciones.
     * @param currentSongIndex Índice de la canción actualmente seleccionada.
     */
    public void updateSongList(ArrayList<String> songNames, int currentSongIndex) {
        songListPanel.removeAll(); // Limpiar el panel
        for (int i = 0; i < songNames.size(); i++) {
            JLabel songLabel = new JLabel(); // Crear una nueva etiqueta
            if (i == currentSongIndex) {
                songLabel.setText("<html><u><b>" + songNames.get(i) + "</b></u></html>"); // Resaltar canción actual
            } else {
                songLabel.setText(songNames.get(i)); // Mostrar otras canciones
            }
            setHorizontalPosition(songLabel); // Configurar alineación
            songListPanel.add(songLabel); // Agregar la etiqueta al panel
        }
        songListPanel.revalidate(); // Actualizar el diseño
        songListPanel.repaint();    // Volver a pintar
    }

    /**
     * Configura la posición horizontal de una etiqueta.
     *
     * @param jlabel Etiqueta a configurar.
     */
    public void setHorizontalPosition(JLabel jlabel) {
        jlabel.setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     * Devuelve el panel que contiene la lista de canciones.
     *
     * @return Panel de lista de canciones.
     */
    public JPanel getSongListPanel() {
        return songListPanel;
    }

    /**
     * Devuelve el botón de reproducción.
     *
     * @return Botón de reproducción.
     */
    public JButton getPlayButton() {
        return playButton;
    }

    /**
     * Devuelve el botón de detener.
     *
     * @return Botón de detener.
     */
    public JButton getStopButton() {
        return stopButton;
    }

    /**
     * Devuelve el botón de siguiente canción.
     *
     * @return Botón de siguiente canción.
     */
    public JButton getNextButton() {
        return nextButton;
    }

    /**
     * Devuelve el botón de canción anterior.
     *
     * @return Botón de canción anterior.
     */
    public JButton getPrevButton() {
        return prevButton;
    }

    /**
     * Devuelve el botón para mostrar la selección.
     *
     * @return Botón para mostrar la selección.
     */
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * Muestra un mensaje de información al usuario.
     */
    private void showInfo() {
        JOptionPane.showMessageDialog(null, "Presiona el botón de reproducción para visualizar las canciones descargadas");
    }
}
