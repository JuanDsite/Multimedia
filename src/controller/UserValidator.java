package controller;

import model.UserDAO;
import model.UserVO;
import java.util.ArrayList;

public class UserValidator implements IValidatorUser {

    private final UserDAO users;
    private ArrayList<UserVO> userList;
    private int currentLoggedUser;
    private double balance;
    private final double songsValue = 15000;

    public UserValidator() {
        users = new UserDAO();
    }

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
    
    @Override
    public boolean checkBalance() {
        for(UserVO u : userList){
            if(u.getId() == currentLoggedUser){
                if(u.getBalanceDue() == 0){
                    return true;
                }
            }
        }
        return false;  
    }
    
    @Override
    public boolean validatePayment(){
        for(UserVO u : userList){
            if(u.getId() == currentLoggedUser){
                if(u.getBalanceDue() != 0){
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

    @Override
    public double getBalance(){
        return balance;
    }

    @Override
    public int getCurrentLoggedUser() {
        return currentLoggedUser;
    }

}
