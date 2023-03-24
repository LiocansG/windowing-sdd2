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
import structure.Segment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private PrioritySearchTree PST;
    private ArrayList<Double> windowSize;
    private Double ratio;
    private GraphicsContext gc;
    private Segment window;
    private double canvasX, canvasY, mouseX, mouseY = 0;
    @FXML
    public void initialize() throws IOException {
        fillComboBoxItem();
        loadingDataFromFile();
        gc = canvas.getGraphicsContext2D();
        window = new Segment(windowSize.get(0), windowSize.get(2), windowSize.get(1), windowSize.get(3));
        resetCoordinates();
        setScrollPane();
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
    public void draw(){
        clearCanvas();
        drawPst(PST.getRoot());
        drawWindow();
    }

    public void clearCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.save();
    }

    private void drawPst(PSTNode currentNode){

        if(currentNode != null){
            drawPst(currentNode.getLeftChild());
            drawSegment(currentNode.getSegment());
            drawPst(currentNode.getRightChild());
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
        window.setX(xField.getText().contains("\u221E") ? windowSize.get(0): Double.parseDouble(xField.getText()));
        window.setY(yField.getText().contains("\u221E") ? windowSize.get(2) : Double.parseDouble(yField.getText()));
        window.setxPrime(xPrimeField.getText().contains("\u221E") ? windowSize.get(1) : Double.parseDouble(xPrimeField.getText()));
        window.setyPrime(yPrimeField.getText().contains("\u221E") ? windowSize.get(3) : Double.parseDouble(yPrimeField.getText()));
    }

    private void drawWindow(){
        changeWindow();
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        if(window.getX() < window.getxPrime() && window.getY() < window.getyPrime()){
            System.out.println(applyRatio(window.getX()));
            System.out.println(applyRatio(window.getY()));
            System.out.println(applyRatio(window.getxPrime()) - applyRatio(window.getX()));
            System.out.println(applyRatio(window.getyPrime()) - applyRatio(window.getY()));
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
        }else{
            clearCanvas();
            MainApplication.getAlert().show();
        }
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

    //----------------------Canvas Control--------------------//
    private void setScrollPane(){
    scrollPane.setHmax(canvas.getWidth());
    scrollPane.setVmax(canvas.getHeight());
}

    public void setOnScroll(ScrollEvent event){
        double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
        double currentScale = canvas.getScaleX();
        double newScale = currentScale * zoomFactor;
        canvas.setScaleX(newScale);
        canvas.setScaleY(newScale);
        event.consume();
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

        // Consume the event to prevent other handlers from processing it
        event.consume();
    }
}
