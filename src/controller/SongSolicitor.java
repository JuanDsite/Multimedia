package controller;

import java.util.ArrayList;
import model.SongDAO;
import model.SongVO;

public class SongSolicitor {

    private final SongDAO songs;
    private ArrayList<SongVO> songList;
    private String songUrl;
   
    
    public SongSolicitor() {
        this.songs = new SongDAO();
    }

    public ArrayList<SongVO> returnSongs() {
        songList = songs.getSongs();
        return songList;
    }

    public String getSongURL(String songName) {
        songList = songs.getSongs();
        for (SongVO song : songList) {
            if (songName.equals(song.getSongName())) {
                songUrl = song.getUrl();
            }
        }
        return songUrl;
    }

    public String getSongName(String songName) {
        songList = songs.getSongs();
        for (SongVO song : songList) {
            if (songName.equals(song.getSongName())) {
                songName = song.getSongName();
            }
        }
        return songName;
    }

    
}
