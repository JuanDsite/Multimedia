package model;

import java.io.Serializable;

/**
 * Clase que representa una canción en el sistema. Esta clase es un Value Object
 * que almacena la información relacionada con una canción, como su
 * identificador, nombre y URL de acceso. Implementa la interfaz
 * {@link Serializable} para permitir la serialización de objetos de esta clase.
 */
public class SongVO implements Serializable {

    private final int songId;  // Identificador único de la canción
    private final String songName;  // Nombre de la canción
    private final String url;  // URL donde está disponible la canción

    /**
     * Constructor de la clase SongVO. Inicializa los valores del ID de la
     * canción, el nombre y la URL.
     *
     * @param songId El ID de la canción.
     * @param songName El nombre de la canción.
     * @param url La URL donde se puede acceder a la canción.
     */
    public SongVO(int songId, String songName, String url) {
        this.songId = songId;
        this.songName = songName;
        this.url = url;
    }

    /**
     * Obtiene el ID de la canción.
     *
     * @return El ID de la canción.
     */
    public int getId() {
        return songId;
    }

    /**
     * Obtiene el nombre de la canción.
     *
     * @return El nombre de la canción.
     */
    public String getSongName() {
        return songName;
    }

    /**
     * Obtiene la URL de la canción.
     *
     * @return La URL de la canción.
     */
    public String getUrl() {
        return url;
    }
}
