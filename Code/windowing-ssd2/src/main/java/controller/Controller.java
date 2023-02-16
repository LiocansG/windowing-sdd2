package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class Controller {

    String path = "src/main/resources/data";
    @FXML
    ComboBox directoryComboBox;
    @FXML
    Pane pnlCanvas;
    @FXML
    Canvas canvas;


    @FXML
    public void initialize() throws IOException {
        File[] files = new File(path).listFiles();
        ArrayList<String> listFiles = new ArrayList<String>();
        for (File f : files) {
            listFiles.add(f.getName());
        }
        directoryComboBox.getItems().addAll(listFiles);
        directoryComboBox.setValue(listFiles.get(0));
    }

    public void draw(ActionEvent event) throws IOException {
        event.consume();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        FileReader fileR = new FileReader(path + "/" + directoryComboBox.getValue());
        BufferedReader br = new BufferedReader(fileR);
        String line;
        String[] temp = new String[0];
        Double[] tab;
        while ((line = br.readLine()) != null) {
            temp = line.split(" ");
            tab = new Double[4];
            for (int i = 0; i < 4; i++) {
                tab[i] = Double.parseDouble(temp[i]);
            }
            gc.setFill(Color.BLUE);
            gc.strokeLine(tab[0], tab[1], tab[2], tab[3]);

        }
    }

    public void clearCanvas(ActionEvent event) throws IOException{
        event.consume();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
