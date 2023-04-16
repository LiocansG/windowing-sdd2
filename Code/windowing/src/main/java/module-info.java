module com.SDD {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.SDD.graphic to javafx.fxml;
    exports com.SDD.graphic;

    opens com.SDD.graphic.controller to javafx.fxml;
    exports com.SDD.graphic.controller;

    opens com.SDD to javafx.fxml;
    exports com.SDD;
}