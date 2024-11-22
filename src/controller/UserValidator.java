package controller;

import model.UserDAO;
import model.UserVO;
import java.util.ArrayList;

/**
 * Clase que valida y gestiona las operaciones relacionadas con los usuarios.
 * Implementa la interfaz {@link IValidatorUser}.
 */
public class UserValidator implements IValidatorUser {

    private final UserDAO users;
    private ArrayList<UserVO> userList;
    private int currentLoggedUser;
    private double balance;
    private final double songsValue = 15000; // Valor de una canción.

    /**
     * Constructor de la clase UserValidator. Inicializa el objeto DAO necesario
     * para acceder a los datos de los usuarios.
     */
    public UserValidator() {
        users = new UserDAO();
    }

    /**
     * Valida las credenciales del usuario.
     *
     * @param user El nombre del usuario.
     * @param password La contraseña del usuario.
     * @return {@code true} si las credenciales son válidas, {@code false} en
     * caso contrario.
     */
    @Override
    public boolean validateCredentials(String user, String password) {
        userList = users.getUsersList();
        for (UserVO u : userList) {
            if (u.getUser().equals(user) && u.getPassword().equals(password)) {
                currentLoggedUser = u.getId();
                balance = u.getBalanceDue();
                return true;
            }
            if (u.getUser().equals(user) && !u.getPassword().equals(password)) {
                return false;
            }
            if (!u.getUser().equals(user) && u.getPassword().equals(password)) {
                return false;
            }
        }
        return false;
    }

    /**
     * Valida y actualiza el balance del usuario sumando el costo de una
     * canción.
     *
     * @return {@code true} si se actualiza correctamente, {@code false} en caso
     * contrario.
     */
    @Override
    public boolean validateBalance() {
        for (UserVO u : userList) {
            if (u.getId() == currentLoggedUser) {
                double value = balance + songsValue;
                balance = value;
                u.setBalanceDue(value);
                users.modifyDebtValue(u);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el balance del usuario es cero.
     *
     * @return {@code true} si el balance es cero, {@code false} en caso
     * contrario.
     */
    @Override
    public boolean checkBalance() {
        for (UserVO u : userList) {
            if (u.getId() == currentLoggedUser) {
                if (u.getBalanceDue() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Valida el pago realizado por el usuario. Si el usuario tiene deuda, se
     * establece su balance en cero.
     *
     * @return {@code true} si el pago es válido, {@code false} en caso
     * contrario.
     */
    @Override
    public boolean validatePayment() {
        for (UserVO u : userList) {
            if (u.getId() == currentLoggedUser) {
                if (u.getBalanceDue() != 0) {
                    double value = 0;
                    u.setBalanceDue(value);
                    users.modifyDebtValue(u);
                    balance = 0;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Obtiene el balance actual del usuario logueado.
     *
     * @return El balance actual del usuario como un valor {@code double}.
     */
    @Override
    public double getBalance() {
        return balance;
    }

    /**
     * Obtiene el ID del usuario actualmente logueado.
     *
     * @return El ID del usuario logueado como un entero.
     */
    @Override
    public int getCurrentLoggedUser() {
        return currentLoggedUser;
    }
}
