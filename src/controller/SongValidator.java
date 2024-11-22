package controller;

import java.util.ArrayList;
import model.SongDAO;
import model.SongUserDAO;
import model.SongUserVO;
import model.SongVO;

/**
 * Clase que valida y gestiona las operaciones relacionadas con las canciones y
 * los usuarios. Implementa la interfaz {@link IValidatorSong}.
 */
public class SongValidator implements IValidatorSong {

    private final SongDAO songDAO;
    private final SongUserDAO songUserDAO;
    private ArrayList<SongVO> songList;
    private ArrayList<SongUserVO> songUserList;
    private ArrayList<SongVO> songListStored;
    private int id;

    /**
     * Constructor de la clase SongValidator. Inicializa los objetos DAO
     * necesarios para acceder a los datos de canciones y relaciones
     * usuario-canción.
     */
    public SongValidator() {
        this.songUserDAO = new SongUserDAO();
        this.songDAO = new SongDAO();
    }

    /**
     * Asocia una canción con un usuario en la base de datos.
     *
     * @param songName El nombre de la canción que será asociada.
     * @param idUser El ID del usuario al que se asociará la canción.
     */
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

    /**
     * Obtiene el ID de una canción dado su nombre.
     *
     * @param songName El nombre de la canción.
     * @return El ID de la canción como un entero.
     */
    @Override
    public int getId(String songName) {
        songList = songDAO.getSongs();
        for (SongVO s : songList) {
            if (s.getSongName().equals(songName)) {
                id = s.getId();
            }
        }
        return id;
    }

    /**
     * Verifica si un usuario ya descargó una canción específica.
     *
     * @param idUser El ID del usuario.
     * @param idSong El ID de la canción.
     * @return {@code true} si el usuario ya descargó la canción, {@code false}
     * en caso contrario.
     */
    @Override
    public boolean checkDoubleDownload(int idUser, int idSong) {
        songUserList = songUserDAO.getComparisonTabel(idUser, idSong);
        for (SongUserVO s : songUserList) {
            if (s.getIdSong() == idSong && s.getIdUser() == idUser) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inserta un registro en la tabla que relaciona usuarios con canciones.
     *
     * @param idSong El ID de la canción.
     * @param idUser El ID del usuario.
     */
    @Override
    public void insert(int idSong, int idUser) {
        songUserDAO.register(idUser, idSong);
    }

    /**
     * Retorna la información de las canciones descargadas por un usuario
     * específico.
     *
     * @param idUser El ID del usuario.
     * @return Un {@code ArrayList<SongVO>} con las canciones descargadas por el
     * usuario.
     */
    @Override
    public ArrayList<SongVO> returnSongDownloadedInfo(int idUser) {
        songListStored = songUserDAO.getDownloadedSongsByUser(idUser);
        return songListStored;
    }

    /**
     * Obtiene la lista de canciones disponibles.
     *
     * @return Un {@code ArrayList<SongVO>} con las canciones disponibles.
     */
    @Override
    public ArrayList<SongVO> getSongList() {
        return songList;
    }
}
