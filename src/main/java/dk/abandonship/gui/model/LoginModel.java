package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.LoginManager;

import java.sql.SQLException;

public class LoginModel {

    private final LoginManager loginManager;

    public LoginModel() {
        loginManager = new LoginManager();
    }

    public boolean logIn(String email, String rawPassword) throws SQLException {
        return loginManager.login(email, rawPassword);
    }
}
