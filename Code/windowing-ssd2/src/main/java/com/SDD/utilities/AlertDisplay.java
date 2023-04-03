package com.SDD.utilities;

import javafx.scene.control.Alert;

/**
 * An abstract class for displaying alert messages.
 */
public abstract class AlertDisplay {

    /**
     * Displays an alert message with the given content text.
     *
     * @param alert The Alert object to display.
     */
    public static void alertDisplay(Alert alert, String text){
        alert.setContentText(text);
        alert.show();
    }
}
