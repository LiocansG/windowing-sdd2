package controller;

import graphic.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import structure.PSTNode;
import structure.PrioritySearchTree;
import structure.Segment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Controller {

    @FXML
    private ComboBox directoryComboBox;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField xField;
    @FXML
    private TextField xPrimeField;
    @FXML
    private TextField yField;
    @FXML
    private TextField yPrimeField;

    private String path = "src/main/resources/data_test";
    private PrioritySearchTree PST;
    private ArrayList<Double> windowSize;
    private Double ratio;
    private GraphicsContext gc;

    @FXML
    public void initialize() throws IOException {
        fillComboBoxItem();
        loadingDataFromFile();
        gc = canvas.getGraphicsContext2D();
        resetCoordinates();
    }

    public void draw(){
        clearCanvas();
        drawPst(PST.getRoot());
        drawWindow();
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
        windowSize = new ArrayList<>();
        FileReader fileR = new FileReader(path + "/" + directoryComboBox.getValue());
        BufferedReader br = new BufferedReader(fileR);
        String line;
        String[] temp;
        Double[] tab;
        ArrayList<Segment> segments = new ArrayList<>();
        line = br.readLine();
        temp = line.split(" ");
        for (int i = 0; i < 4; i++) {
            windowSize.add(Double.parseDouble(temp[i]));
        }

        ratio = Math.min(canvas.getWidth() / (Math.abs(windowSize.get(0)) + Math.abs(windowSize.get(2))), canvas.getHeight() / (Math.abs(windowSize.get(1)) + Math.abs(windowSize.get(3))));

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

    public void resetCoordinates(){
        xField.setText("-" + '\u221E');
        xPrimeField.setText("+" + '\u221E');
        yField.setText("-" + '\u221E');
        yPrimeField.setText("+" + '\u221E');
    }

    private void drawPst(PSTNode currentNode){

        if(currentNode != null){
            drawPst(currentNode.getLeftChild());
            drawSegment(currentNode.getSegment());
            drawPst(currentNode.getRightChild());
        }
    }

    private double applyRatio(double value, boolean isX){
        if(isX)
            return (value * ratio) + (canvas.getWidth()/2);
        return (value * ratio) + (canvas.getHeight()/2);
    }

    private void drawWindow(){
        double x = applyRatio(xField.getText().contains("\u221E") ? windowSize.get(0): Double.parseDouble(xField.getText()), true);
        double y = applyRatio(yField.getText().contains("\u221E") ? windowSize.get(2) : Double.parseDouble(yField.getText()), false);
        double xPrime = applyRatio(xPrimeField.getText().contains("\u221E") ? windowSize.get(1) : Double.parseDouble(xPrimeField.getText()), true);
        double yPrime = applyRatio(yPrimeField.getText().contains("\u221E") ? windowSize.get(3) : Double.parseDouble(yPrimeField.getText()), false);

        if(x < xPrime && y < yPrime){
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, xPrime - x, yPrime - y);
            gc.setLineWidth(1);
            gc.setStroke(Color.BLACK);
        }else{
            clearCanvas();
            MainApplication.getAlert().show();
        }
    }

    private void fillComboBoxItem(){
        desactivateEventComboBox();
        File file = new File(path);
        File[] files = file.listFiles();
        ArrayList<String> listFiles = new ArrayList<>();
        for (File f : files) {
            listFiles.add(f.getName());
        }

        Collections.sort(listFiles, Collections.reverseOrder());

        directoryComboBox.getItems().addAll(listFiles);
        directoryComboBox.setValue(listFiles.get(0));
    }

    private void desactivateEventComboBox(){
        EventHandler<ActionEvent> handler = directoryComboBox.getOnAction();
        directoryComboBox.setOnAction(null);
        directoryComboBox.getItems().removeAll(directoryComboBox.getItems());
        directoryComboBox.setOnAction(handler);
    }

    private void drawSegment(Segment segment) {
        gc.setFill(Color.RED);
        gc.strokeLine(
                applyRatio(segment.getX(), true),
                applyRatio(segment.getY(), false),
                applyRatio(segment.getxPrime(), true),
                applyRatio(segment.getyPrime(), false)
        );
    }

}
