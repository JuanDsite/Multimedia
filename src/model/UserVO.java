package model;

/**
 * Clase que representa a un usuario en el sistema.
 * Contiene información sobre el nombre de usuario, la contraseña, el saldo adeudado y el ID del usuario.
 */
public class UserVO {

    private final String user;  // Nombre de usuario
    private final String password;  // Contraseña del usuario
    private double balanceDue;  // Saldo adeudado por el usuario
    private final int id;  // ID único del usuario

    /**
     * Constructor de la clase UserVO.
     * Inicializa los atributos del usuario con los valores proporcionados.
     * 
     * @param user El nombre de usuario.
     * @param password La contraseña del usuario.
     * @param balanceDue El saldo adeudado por el usuario.
     * @param id El ID único del usuario.
     */
    public UserVO(String user, String password, double balanceDue, int id) {
        this.user = user;
        this.password = password;
        this.balanceDue = balanceDue;
        this.id = id;
    }

    /**
     * Calcula el saldo adeudado por el usuario según la cantidad de canciones descargadas y el valor de cada canción.
     * 
     * @param downloadedSongs La cantidad de canciones descargadas.
     * @param valueSong El valor de cada canción.
     * @return El saldo total adeudado por el usuario.
     */
    public double balance(int downloadedSongs, double valueSong) {
        balanceDue = valueSong * downloadedSongs;  // Calcula el saldo adeudado
        return balanceDue;  // Retorna el saldo calculado
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return El nombre de usuario.
     */
    public String getUser() {
        return user;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el saldo adeudado por el usuario.
     * 
     * @return El saldo adeudado por el usuario.
     */
    public double getBalanceDue() {
        return balanceDue;
    }

    /**
     * Obtiene el ID único del usuario.
     * 
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el saldo adeudado por el usuario.
     * 
     * @param balanceDue El nuevo saldo adeudado por el usuario.
     */
    public void setBalanceDue(double balanceDue) {
        this.balanceDue = balanceDue;
    }
}
