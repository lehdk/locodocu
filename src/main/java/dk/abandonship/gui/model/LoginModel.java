package dk.abandonship.gui.model;

import dk.abandonship.businesslogic.LoginManager;

public class LoginModel {

    private LoginManager loginManager;

    public LoginModel() {
        loginManager = new LoginManager();
    }

    public String logIn(String email, String rawPassword) throws Exception {
        boolean success = loginManager.login(email, rawPassword);

        if(success){
            return "valid password";
        }
        else{
            return "Invalid username or password";
        }
    }
}
