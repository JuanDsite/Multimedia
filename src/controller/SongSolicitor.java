package controller;

import java.util.ArrayList;
import model.SongDAO;
import model.SongVO;

/**
 * Clase que actúa como intermediario para gestionar la solicitud de canciones.
 * Proporciona métodos para obtener la lista de canciones, la URL de una canción
 * específica, y el nombre de una canción según su coincidencia.
 */
public class SongSolicitor {

    private final SongDAO songs;
    private ArrayList<SongVO> songList;
    private String songUrl;

    /**
     * Constructor de la clase SongSolicitor. Inicializa el objeto de acceso a
     * datos (DAO) para gestionar las canciones.
     */
    public SongSolicitor() {
        this.songs = new SongDAO();
    }

    /**
     * Retorna la lista de canciones almacenadas en la base de datos.
     *
     * @return Un {@code ArrayList<SongVO>} con los objetos de tipo canción.
     */
    public ArrayList<SongVO> returnSongs() {
        songList = songs.getSongs();
        return songList;
    }

    /**
     * Obtiene la URL de una canción específica según su nombre.
     *
     * @param songName El nombre de la canción cuya URL se desea obtener.
     * @return La URL de la canción como {@code String}, o {@code null} si no se
     * encuentra.
     */
    public String getSongURL(String songName) {
        songList = songs.getSongs();
        for (SongVO song : songList) {
            if (songName.equals(song.getSongName())) {
                songUrl = song.getUrl();
            }
        }
        return songUrl;
    }

    /**
     * Obtiene el nombre de una canción específica según su coincidencia en la
     * lista.
     *
     * @param songName El nombre de la canción que se desea verificar.
     * @return El nombre de la canción como {@code String}, o {@code null} si no
     * se encuentra.
     */
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
