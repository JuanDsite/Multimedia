package model;

/**
 * Clase que representa la relación entre un usuario y una canción en el sistema.
 * Almacena los identificadores del usuario y de la canción asociada.
 */
public class SongUserVO {

    private final int idUser;  // Identificador único del usuario
    private final int idSong;  // Identificador único de la canción

    /**
     * Constructor de la clase SongUserVO.
     * Inicializa los identificadores del usuario y de la canción.
     * 
     * @param idUser El ID del usuario.
     * @param idSong El ID de la canción.
     */
    public SongUserVO(int idUser, int idSong){
        this.idSong = idSong;
        this.idUser = idUser;
    }

    /**
     * Obtiene el ID del usuario.
     * 
     * @return El ID del usuario.
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Obtiene el ID de la canción.
     * 
     * @return El ID de la canción.
     */
    public int getIdSong() {
        return idSong;
    }
}