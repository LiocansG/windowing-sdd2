package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import graphic.MainApplication;
import structure.PrioritySearchTree;
import structure.Segment;

import java.io.*;
import java.util.*;

public class Controller {

    private String path = "src/main/resources/data";
    @FXML
    private ComboBox directoryComboBox;
    @FXML
    private Canvas canvas;

    private PrioritySearchTree PST;

    @FXML
    public void initialize() throws IOException {
        fillComboBoxItem();
        loadingDataFromFile();
    }

    public void fillComboBoxItem(){
        directoryComboBox.getItems().removeAll(directoryComboBox.getItems());
        File[] files = new File(path).listFiles();
        ArrayList<String> listFiles = new ArrayList<>();
        for (File f : files) {
            listFiles.add(f.getName());
        }

        Collections.sort(listFiles, Collections.reverseOrder());

        directoryComboBox.getItems().addAll(listFiles);
        directoryComboBox.setValue(listFiles.get(0));
    }

    public void draw() throws IOException {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        FileReader fileR = new FileReader(path + "/" + directoryComboBox.getValue());
        BufferedReader br = new BufferedReader(fileR);
        String line;
        String[] temp;
        Double[] tab;
        br.readLine();
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

    public void clearCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void changeDirectory(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File defaultDirectory = new File(path);
        directoryChooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = directoryChooser.showDialog(MainApplication.getStage());
        if (selectedDirectory != null) {
            path = selectedDirectory.getAbsolutePath();
            fillComboBoxItem();
        }
    }

    public void loadingDataFromFile() throws IOException {
        FileReader fileR = new FileReader(path + "/" + directoryComboBox.getValue());
        BufferedReader br = new BufferedReader(fileR);
        String line;
        String[] temp;
        Double[] tab;
        ArrayList<Segment> segments = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            temp = line.split(" ");
            tab = new Double[4];
            for (int i = 0; i < 4; i++) {
                tab[i] = Double.parseDouble(temp[i]);
            }
            segments.add(new Segment(tab[0], tab[1], tab[2], tab[3]));
        }
        PST = new PrioritySearchTree(segments);
    }
}
