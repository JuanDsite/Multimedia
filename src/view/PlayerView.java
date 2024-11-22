package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerView extends JFrame {

    private final JButton playButton;
    private final JButton stopButton;
    private final JButton nextButton;
    private final JButton prevButton;
    private final JButton backButton;
    private final JPanel songListPanel;
    private final JMenuBar bar;
    private final JMenu menu;
    private final JMenuItem item;

    public PlayerView() {
        // CONFIGURACIÓN DEL FRAME
        setTitle("Reproductor de Música Simple");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Crear botones con imágenes
        playButton = createButtonWithIcon("media/iconos/repeat.png", 50, 50);
        stopButton = createButtonWithIcon("media/iconos/stop.png", 50, 50);
        nextButton = createButtonWithIcon("media/iconos/next.png", 50, 50);
        prevButton = createButtonWithIcon("media/iconos/prev.png", 50, 50);
        backButton = new JButton("Mostrar selección");
        
        
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

        this.add(buttonsPanel, BorderLayout.SOUTH);

        // Panel para la lista de canciones
        songListPanel = new JPanel();
        songListPanel.setLayout(new BoxLayout(songListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(songListPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        
        item.addActionListener(e -> showInfo());
    }

    private JButton createButtonWithIcon(String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(width, height));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    public void updateSongList(ArrayList<String> songNames, int currentSongIndex) {
        songListPanel.removeAll(); // Limpiar el panel
        for (int i = 0; i < songNames.size(); i++) {
            JLabel songLabel = new JLabel(); // Crear una nueva etiqueta
            if (i == currentSongIndex) {
                songLabel.setText("<html><u><b>" + songNames.get(i) + "</b></u></html>"); // Resaltar canción actual
            } else {
                songLabel.setText(songNames.get(i)); // Mostrar otras canciones
            }
            setHorizontalPosition(songLabel); // Configurar alineación si es necesario
            songListPanel.add(songLabel); // Agregar la etiqueta al panel
        }
        songListPanel.revalidate(); // Actualizar el diseño
        songListPanel.repaint();    // Volver a pintar
    }

    public void setHorizontalPosition(JLabel jlabel) {
        jlabel.setHorizontalAlignment(JLabel.CENTER);
    }

    public JPanel getSongListPanel() {
        return songListPanel;
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
    
    private void showInfo(){
        JOptionPane.showMessageDialog(null, "Presiona el botón de reproducción para visualizar las canciones descargadas");
    }
}
