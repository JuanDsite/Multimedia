package controller;

import java.util.ArrayList;
import model.SongVO;

/**
 * Interfaz para validar y gestionar operaciones relacionadas con canciones y
 * usuarios. Define los métodos necesarios para la gestión de descargas de
 * canciones, comprobaciones y recuperación de información.
 */
public interface IValidatorSong {

    /**
     * Gestiona la asociación de una canción con un usuario específico.
     *
     * @param songName Nombre de la canción a gestionar.
     * @param idUser ID del usuario que realiza la acción.
     */
    public void manageSongUser(String songName, int idUser);

    /**
     * Inserta una nueva relación entre una canción y un usuario.
     *
     * @param idSong ID de la canción a asociar.
     * @param idUser ID del usuario que realiza la descarga.
     */
    public void insert(int idSong, int idUser);

    /**
     * Obtiene el ID de una canción a partir de su nombre.
     *
     * @param songName Nombre de la canción.
     * @return El ID de la canción si existe, o -1 si no se encuentra.
     */
    public int getId(String songName);

    /**
     * Verifica si un usuario ya descargó una canción específica.
     *
     * @param idUser ID del usuario a verificar.
     * @param idSong ID de la canción a verificar.
     * @return {@code true} si el usuario ya descargó la canción, {@code false}
     * en caso contrario.
     */
    public boolean checkDoubleDownload(int idUser, int idSong);

    /**
     * Retorna la información de las canciones descargadas por un usuario.
     *
     * @param idUser ID del usuario cuya información de descargas se desea
     * recuperar.
     * @return Una lista de objetos {@link SongVO} que representan las canciones
     * descargadas.
     */
    public ArrayList<SongVO> returnSongDownloadedInfo(int idUser);

    /**
     * Obtiene la lista de todas las canciones disponibles.
     *
     * @return Una lista de objetos {@link SongVO} que representan las canciones
     * disponibles.
     */
    public ArrayList<SongVO> getSongList();
}
