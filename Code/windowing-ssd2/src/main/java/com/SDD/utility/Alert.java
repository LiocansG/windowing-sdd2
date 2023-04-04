package com.SDD.utility;

/**
 * An abstract class for displaying alert messages.
 */
public abstract class Alert {

    /**
     * Displays an alert message with the given content text.
     *
     * @param alert The Alert object to display.
     */
    public static void alertDisplay(javafx.scene.control.Alert alert, String text){
        alert.setContentText(text);
        alert.show();
    }
}
