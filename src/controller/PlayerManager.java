package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import services.Client;
import view.PlayerView;

public class PlayerManager {

    private final PlayerView view;
    private final Client client;
    private ArrayList<String> songList;
    private ArrayList<String> songNames;
    private AdvancedPlayer player;
    private int currentSongIndex = 0;
    private boolean isPlaying = false;

    public PlayerManager(Client client) {
        this.client = client;
        this.songList = new ArrayList<>();
        this.songNames = new ArrayList<>();
        this.view = new PlayerView();

        updateSongListDisplay();
        initListeners();
    }

    public void setUrlArray(ArrayList<String> URLS) {
        this.songList = URLS;

    }
    
    public void setNamesArray(ArrayList<String> NAMES){
        this.songNames = NAMES;
    }
    
    private void initListeners() {
        view.getPlayButton().addActionListener(e -> togglePlayPause());
        view.getStopButton().addActionListener(e -> stopSong());
        view.getNextButton().addActionListener(e -> nextSong());
        view.getPrevButton().addActionListener(e -> previousSong());
        view.getBackButton().addActionListener(e -> returnView());
    }

    private void togglePlayPause() {
        if (!isPlaying) {
            playSong();
        } else {
            pauseSong();
        }
    }

    public final void updateSongListDisplay() {
        view.updateSongList(songNames, currentSongIndex);
    }

    private void playSong() {
        try {
            FileInputStream fis = new FileInputStream(songList.get(currentSongIndex));
            player = new AdvancedPlayer(fis);
            isPlaying = true;
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
                isPlaying = false;
            }).start();
            updateSongListDisplay();
        } catch (IOException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private void stopSong() {
        if (player != null) {
            player.close();
            isPlaying = false;
        }
    }

    private void pauseSong() {
        stopSong();
    }

    private void nextSong() {
        if (currentSongIndex < songList.size() - 1) {
            currentSongIndex++;
            stopSong();
            playSong();
        }
    }

    private void previousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
            stopSong();
            playSong();
        }
    }

    public PlayerView getPlayerView() {
        return view;
    }

    public void returnView() {
        client.getSelectorManager().getView().setVisible(true);
        view.dispose();
    }
}
