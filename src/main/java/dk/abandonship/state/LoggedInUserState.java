package dk.abandonship.state;

import dk.abandonship.entities.User;

/**
 * This singleton holds the currently logged-in user.
 */
public class LoggedInUserState {

    private static LoggedInUserState instance;

    private User loggedInUser;

    private LoggedInUserState() {
        loggedInUser = null;
    }

    /**
     * @return The currently logged-in user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Sets the currently logged-in user
     * @param loggedInUser
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public static LoggedInUserState getInstance() {
        if(instance == null) instance = new LoggedInUserState();

        return instance;
    }
}
