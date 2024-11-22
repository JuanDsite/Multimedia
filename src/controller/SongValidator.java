package controller;

import java.util.ArrayList;
import model.SongDAO;
import model.SongUserDAO;
import model.SongUserVO;
import model.SongVO;

public class SongValidator implements IValidatorSong {

    private final SongDAO songDAO;
    private final SongUserDAO songUserDAO;
    private ArrayList<SongVO> songList;
    private ArrayList<SongUserVO> songUserList;
    private ArrayList<SongVO> songListStored;
    private int id;

    public SongValidator() {
        songUserDAO = new SongUserDAO();
        this.songDAO = new SongDAO();
    }

    @Override
    public void manageSongUser(String songName, int idUser) {
        songList = songDAO.getSongs();
        for (SongVO s : songList) {
            if (s.getSongName().equals(songName)) {
                id = s.getId();
                insert(id, idUser);
            }
        }
    }

    // metodo necesario para verificar si el usuario ya descargó una canción
    public int getId(String songName) {
        songList = songDAO.getSongs();
        for (SongVO s : songList) {
            if (s.getSongName().equals(songName)) {
                id = s.getId();
            }
        }
        return id;
    }

    public boolean checkDoubleDownload(int idUser, int idSong) {
        songUserList = songUserDAO.getComparisonTabel(idUser, idSong);
        for (SongUserVO s : songUserList) {
            if (s.getIdSong() == idSong && s.getIdUser() == idUser) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void insert(int idSong, int idUser) {
        songUserDAO.register(idUser, idSong);
    }

    // Info para el reproductor de solo las canciones descargadas 
    public ArrayList<SongVO> returnSongDownloadedInfo(int id) {
        ArrayList<SongVO> songs = songUserDAO.getDownloadedSongsByUser(id);    
        return songs;
    }

    public ArrayList<SongVO> getSongList() {
        return songList;
    }
}
