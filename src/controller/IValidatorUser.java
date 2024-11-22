package controller;

public interface IValidatorUser {
    
    public boolean validateCredentials(String user, String password);
    public boolean validateBalance();
    public boolean validatePayment();
    public double getBalance();
    public boolean checkBalance();
    public int getCurrentLoggedUser();

}
