package utilities;

import javafx.scene.control.Alert;

public abstract class AlertDisplay {

    public static void alertDisplay(Alert alert){
        alert.setContentText("The first point (X, Y) must be greater than (X', Y')");
        alert.show();
    }
}
