package model;
import model.UserRole;

public class User{
    private String login;
    private String password;
    private UserRole role;

    public User(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public String toFileString(){
        return login + "," + password + "," + role.name();
    }
}