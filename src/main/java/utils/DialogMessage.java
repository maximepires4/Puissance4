package utils;

import javax.swing.*;

public enum DialogMessage {
    NEW_USER_SUCCESS("A new user has been successfully created", "User creation", JOptionPane.INFORMATION_MESSAGE),
    USERNAME_TAKEN("Error. This username is already taken", "User creation", JOptionPane.ERROR_MESSAGE),
    EMAIL_TAKEN("Error. This email is already taken", "User creation", JOptionPane.ERROR_MESSAGE),
    NEW_USER_FAIL("Error. Something went wrong when creating a new user", "User creation", JOptionPane.ERROR_MESSAGE),
    FIELDS_BLANK("Error. Please fill out every text fields", "User creation", JOptionPane.ERROR_MESSAGE),
    SIGN_IN_SUCCESS("Welcome back !", "Sign in", JOptionPane.INFORMATION_MESSAGE),
    SIGN_IN_FAIL("Error. The username or the password is incorrect", "Sign in", JOptionPane.ERROR_MESSAGE),
    SIGN_IN_ALREADY("Error. This user is already connected", "Sign in", JOptionPane.ERROR_MESSAGE),
    UPDATE_SUCCESS("Your infos were successfully updated","User update", JOptionPane.INFORMATION_MESSAGE),
    REMOVE_YOURSELF("Error. You can't remove yourself","User management", JOptionPane.ERROR_MESSAGE),
    REMOVE_CONNECTED("Error. You can't remove a connected user","User management", JOptionPane.ERROR_MESSAGE),
    REMOVE_SUCCESS("User removed successfully","User management", JOptionPane.INFORMATION_MESSAGE),
    LAUNCH_ERROR("Error. Two players must be connected", "Play", JOptionPane.ERROR_MESSAGE),
    DRAW("Game ended in a draw", "End of the game", JOptionPane.INFORMATION_MESSAGE),
    PLAYER("A player won the match!", "End of the game", JOptionPane.INFORMATION_MESSAGE),
    COLORS_SAME("Error. Colors must be different", "Settings", JOptionPane.ERROR_MESSAGE),
    ERROR("Error. Something went wrong", "Error", JOptionPane.ERROR_MESSAGE),
    NO_CONNECTION("Error. No connection to the database detected", "Connection", JOptionPane.ERROR_MESSAGE);

    private final String message;
    private final String title;
    private final int type;

    DialogMessage(String message, String title, int type){
        this.message = message;
        this.title = title;
        this.type = type;
    }

    public String getMessage(){
        return message;
    }

    public String getTitle(){
        return title;
    }

    public int getType() {
        return type;
    }
}
