package model;

public class UserVO {

    private final String user;
    private final String password;
    private double balanceDue;
    private final int id;

    public UserVO(String user, String password, double balanceDue, int id) {
        this.user = user;
        this.password = password;
        this.balanceDue = balanceDue;
        this.id = id;
    }

    public double balance(int downloadedSongs, double valueSong) {

        balanceDue = valueSong * downloadedSongs;

        return balanceDue;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public double getBalanceDue() {
        return balanceDue;
    }
    
    public int getId(){
        return id;
    }

    public void setBalanceDue(double balanceDue) {
        this.balanceDue = balanceDue;
    } 

}
