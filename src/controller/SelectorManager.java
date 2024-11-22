package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import model.SongVO;
import services.Client;
import view.SelectorView;

public class SelectorManager {

    private final SelectorView selectorView;
    /**
     * Controlador que maneja las operaciones relacionadas con perros
     */
    private final Client client;
    private final PlayerManager player;
    /**
     * Lista que almacena los resultados de la consulta de perros
     */
    private ArrayList<SongVO> songsList;
    /**
     * Constructor de GestorConsultaVista.
     *
     * @param client Controlador que se utilizará para realizar consultas sobre
     * perros.
     */
    public SelectorManager(Client client) {
        this.selectorView = new SelectorView(this);
        this.client = client;
        player = new PlayerManager(this.client);
    }

    public final void showSongs() {
        selectorView.setVisible(true);
        songsList = client.getArraySong();
        ArrayList<Object[]> results = new ArrayList<>();
        for (SongVO song : songsList) {
            results.add(new Object[]{
                song.getId(),
                song.getSongName(),
                song.getUrl()
            });
        }
        if (songsList != null && !songsList.isEmpty()) {
            selectorView.showResults(results);
        } else {
            selectorView.invalidArrayMessage();
        }
    }

    /**
     * Inicia el manejo de la vista de consulta.
     *
     * Muestra la vista y configura los eventos para los botones de consulta y
     * cancelación.
     */
    public void manageQuery() {
        // Botón para realizar la consulta
        selectorView.getBtnDownload().addActionListener((ActionEvent e) -> {

            // Obtener el tipo de consulta
            String queryValue = selectorView.getQueryValue();// Obtener el valor ingresado

            if (!queryValue.isEmpty()) {
                int songId = Integer.parseInt(queryValue);
                SongVO song = songsList.get(songId - 1);
                client.songDownloading(song.getSongName());

            } else {
                selectorView.invalidQueryMessage();
            }
        });

        selectorView.getBtnPay().addActionListener((ActionEvent e) -> {

            client.payment();

        });

        selectorView.getBtnPlayer().addActionListener((ActionEvent e) -> {
            playerView();

        });

        selectorView.getExitOption().addActionListener((ActionEvent e) -> {
            client.endOfSession();
        });
    }

    public void updateTable(List<Object[]> resultados) {
        for (Object[] fila : resultados) {
            selectorView.getTableModel().addRow(fila); // Asumiendo que tienes un método para obtener el modelo de la tabla
        }
    }

    public void receiveDownloadedSongs(ArrayList<SongVO> downloadedSongs) {
        ArrayList<String> tmpUrl = new ArrayList<>();
        ArrayList<String> tmpNames = new ArrayList<>();

        for (SongVO s : downloadedSongs) {
            tmpUrl.add(s.getUrl());
        }
        player.setUrlArray(tmpUrl);

        for (SongVO s : downloadedSongs) {
            tmpNames.add(s.getSongName());
        }
        player.setNamesArray(tmpNames);
    }

    public void showDueMessage(double balanceDue) {
        selectorView.balanceDueMessage(balanceDue);
    }

    public void showDownloadMessage() {
        selectorView.showDownloadMessage();
    }

    private void playerView() {
        client.songRequest();
        player.getPlayerView().setVisible(true);
        selectorView.setVisible(false);
    }

    public void finishSession(String balance) {
        selectorView.exit(balance);
    }

    public SelectorView getView() {
        return selectorView;
    }

    public void showSongAlreadyDownloaded() {
        selectorView.songDownloadMessage();
    }

    public void noSongDownloaded() {
        selectorView.noSongDonwloadedError();
    }

    public void showPaymentMessage() {
        selectorView.paymentSuccess();
    }
}
