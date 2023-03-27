package controller;

import graphic.MainApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import structure.PSTNode;
import structure.PrioritySearchTree;
import structure.PstWrapper;
import structure.Segment;
import utilities.AlertDisplay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private ComboBox<String> directoryComboBox;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField xField, xPrimeField, yField, yPrimeField;
    @FXML
    private ScrollPane scrollPane;

    private String path = "src/main/resources/data_test";
    private PstWrapper pstWrapper;
    private ArrayList<Double> windowSize;
    private Double ratio;
    private GraphicsContext gc;
    private Segment window;
    private double canvasX, canvasY, mouseX, mouseY = 0;
    @FXML
    public void initialize() throws IOException {
        fillComboBoxItem();
        loadingSegmentFromFile();
        gc = canvas.getGraphicsContext2D();
        window = new Segment(windowSize.get(0), windowSize.get(2), windowSize.get(1), windowSize.get(3));
        resetCoordinates();
    }

    //----------------------Directory--------------------------//
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


    //---------------------Drawing-----------------------------//
    public void clearCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.save();
    }

    public void draw(){
        clearCanvas();
        resetZoomAndPosition();
        if(isWindowGood()){
            drawWindow();
            for (Segment segment : pstWrapper.getWindow(window, windowSize)) {
                drawSegment(segment);
            }
        }else{
            AlertDisplay.alertDisplay(MainApplication.getAlert());
        }
    }


    //---------------------Window------------------------------//
    public void resetCoordinates(){
        xField.setText("-" + '\u221E');
        xPrimeField.setText("+" + '\u221E');
        yField.setText("-" + '\u221E');
        yPrimeField.setText("+" + '\u221E');
    }

    private void changeWindow(){
        window.setX((xField.getText().contains("\u221E") || Double.parseDouble(xField.getText()) < windowSize.get(0)) ? windowSize.get(0): Double.parseDouble(xField.getText()));
        window.setY((yField.getText().contains("\u221E") || Double.parseDouble(yField.getText()) < windowSize.get(2)) ? windowSize.get(2) : Double.parseDouble(yField.getText()));
        window.setxPrime((xPrimeField.getText().contains("\u221E") || Double.parseDouble(xPrimeField.getText()) > windowSize.get(1)) ? windowSize.get(1) : Double.parseDouble(xPrimeField.getText()));
        window.setyPrime((yPrimeField.getText().contains("\u221E") || Double.parseDouble(yPrimeField.getText()) > windowSize.get(3))? windowSize.get(3) : Double.parseDouble(yPrimeField.getText()));
    }

    private void drawWindow(){
        changeWindow();
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeRect(
                applyRatio(window.getX()),
                applyRatio(window.getY()),
                applyRatio(window.getxPrime()) - applyRatio(window.getX()),
                applyRatio(window.getyPrime()) - applyRatio(window.getY())
        );
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
    }

    private boolean isWindowGood(){
        return(window.getX() < window.getxPrime() && window.getY() < window.getyPrime());
    }


    //---------------------ComboBox----------------------------//
    private void fillComboBoxItem(){
        desactivateEventComboBox();
        File file = new File(path);
        File[] files = file.listFiles();
        ArrayList<String> listFiles = new ArrayList<>();
        assert files != null;
        for (File f : files) {
            listFiles.add(f.getName());
        }

        directoryComboBox.getItems().addAll(listFiles);
        directoryComboBox.setValue(listFiles.get(0));
    }

    private void desactivateEventComboBox(){
        EventHandler<ActionEvent> handler = directoryComboBox.getOnAction();
        directoryComboBox.setOnAction(null);
        directoryComboBox.getItems().removeAll(directoryComboBox.getItems());
        directoryComboBox.setOnAction(handler);
    }


    //---------------------Segment----------------------------//
    private double applyRatio(double value){
        return (value * ratio) + (canvas.getWidth()/2);
    }

    private void drawSegment(Segment segment) {
        gc.setFill(Color.RED);
        gc.strokeLine(
                applyRatio(segment.getX()),
                applyRatio(segment.getY()),
                applyRatio(segment.getxPrime()),
                applyRatio(segment.getyPrime())
        );
    }

    public void loadingSegmentFromFile() throws IOException {
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
        pstWrapper = new PstWrapper(segments);
    }


    //----------------------Canvas Control--------------------//
    private void resetZoomAndPosition(){
        // Reset canvas position
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        // Reset zoom
        canvas.setScaleX(1);
        canvas.setScaleY(1);
        canvas.setTranslateZ(0);
    }

    public void setOnScroll(ScrollEvent event){
        double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
        double currentScale = canvas.getScaleX();
        double newScale = currentScale * zoomFactor;
        canvas.setScaleX(newScale);
        canvas.setScaleY(newScale);
    }

    public void setOnMousePressed(MouseEvent event){
        scrollPane.setCursor(Cursor.MOVE);
        scrollPane.setMouseTransparent(true);
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
        canvasX = canvas.getLayoutX();
        canvasY = canvas.getLayoutY();
    }

    public void setOnMouseReleased(){
        scrollPane.setCursor(Cursor.DEFAULT);
        scrollPane.setMouseTransparent(false);
    }

    public void setOnMouseDragged(MouseEvent event){
        double deltaX = event.getSceneX() - mouseX;
        double deltaY = event.getSceneY() - mouseY;
        double newCanvasX = canvasX + deltaX;
        double newCanvasY = canvasY + deltaY;

        // Update the canvas position
        canvas.setTranslateX(newCanvasX);
        canvas.setTranslateY(newCanvasY);
    }
}
