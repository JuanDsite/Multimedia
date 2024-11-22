package controller;

/**
 * Interfaz para validar y gestionar operaciones relacionadas con los usuarios.
 * Define los métodos necesarios para la validación de credenciales, balance,
 * pagos y obtención de información del usuario actual.
 */
public interface IValidatorUser {

    /**
     * Valida las credenciales de un usuario en el sistema.
     *
     * @param user Nombre de usuario que se desea validar.
     * @param password Contraseña asociada al usuario.
     * @return {@code true} si las credenciales son válidas, {@code false} en
     * caso contrario.
     */
    public boolean validateCredentials(String user, String password);

    /**
     * Verifica si el balance del usuario es válido para realizar una operación.
     *
     * @return {@code true} si el balance es válido, {@code false} en caso
     * contrario.
     */
    public boolean validateBalance();

    /**
     * Valida si el usuario puede realizar un pago en función de su estado y
     * balance.
     *
     * @return {@code true} si el pago es posible, {@code false} en caso
     * contrario.
     */
    public boolean validatePayment();

    /**
     * Obtiene el balance actual del usuario.
     *
     * @return El balance del usuario como un valor de tipo {@code double}.
     */
    public double getBalance();

    /**
     * Comprueba si el balance del usuario es suficiente para una operación.
     *
     * @return {@code true} si el balance es suficiente, {@code false} en caso
     * contrario.
     */
    public boolean checkBalance();

    /**
     * Obtiene el ID del usuario actualmente conectado.
     *
     * @return El ID del usuario conectado como un valor de tipo {@code int}.
     */
    public int getCurrentLoggedUser();

}
