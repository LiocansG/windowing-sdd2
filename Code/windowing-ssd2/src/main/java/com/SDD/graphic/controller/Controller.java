package com.SDD.graphic.controller;

import com.SDD.graphic.MainApplication;
import com.SDD.structure.PstWrapper;
import com.SDD.structure.Segment;
import com.SDD.utility.Alert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Controller class is responsible for handling user input and managing the display of
 * segments on a canvas. It uses JavaFX for the user interface and PstWrapper to process
 * segment data from a file.
 */
public class Controller {

    @FXML
    private ComboBox<String> directoryComboBox;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField xField, xPrimeField, yField, yPrimeField;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Text numberSegment;

    private String path = "src/main/resources/data_test";
    private PstWrapper pstWrapper;
    private ArrayList<Double> windowSize;
    private Double ratio;
    private GraphicsContext gc;
    private Segment window;
    private double canvasX, canvasY, mouseX, mouseY = 0;
    private ArrayList<Segment> segments;

    /**
     * Initializes the Controller instance by setting up the user interface and loading the
     * initial segment data.
     *
     * @throws IOException if there is an error reading the segment data from file.
     */
    @FXML
    public void initialize() throws IOException {
        canvasInit();
        fillComboBoxItem();
        loadingSegmentFromFile();
        window = new Segment(windowSize.get(0), windowSize.get(2), windowSize.get(1), windowSize.get(3));
        resetCoordinates();
    }

    //----------------------Directory--------------------------//

    /**
     * Changes the directory path used for loading segment data files. Shows a directory chooser
     * dialog to allow the user to select a new directory.
     */
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

    /**
     * Clears the canvas and resets the zoom and position. Draws the window and all segments
     * that are contained within it.
     */
    public void clearCanvas(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Clears the canvas by filling it with a transparent color and saves the current state
     * of the graphics context.
     */
    public void draw(){
        clearCanvas();
        resetZoomAndPosition();
        changeWindow();
        if(isWindowGood()){
            drawWindow();
            drawSegments();
            numberSegment.setText("Number of segment: " + segments.size());
        }
    }

    //---------------------Window------------------------------//

    /**
     * Resets the coordinates displayed in the text fields to negative infinity and positive
     * infinity, respectively.
     */
    public void resetCoordinates(){
        xField.setText("-" + '\u221E');
        xPrimeField.setText("+" + '\u221E');
        yField.setText("-" + '\u221E');
        yPrimeField.setText("+" + '\u221E');
    }

    /**
     * Changes the values of the window based on the current values in the text fields.
     */
    private void changeWindow(){
        window.setX((xField.getText().contains("\u221E") || Double.parseDouble(xField.getText()) < windowSize.get(0)) ? windowSize.get(0): Double.parseDouble(xField.getText()));
        window.setY((yField.getText().contains("\u221E") || Double.parseDouble(yField.getText()) < windowSize.get(2)) ? windowSize.get(2) : Double.parseDouble(yField.getText()));
        window.setxPrime((xPrimeField.getText().contains("\u221E") || Double.parseDouble(xPrimeField.getText()) > windowSize.get(1)) ? windowSize.get(1) : Double.parseDouble(xPrimeField.getText()));
        window.setyPrime((yPrimeField.getText().contains("\u221E") || Double.parseDouble(yPrimeField.getText()) > windowSize.get(3))? windowSize.get(3) : Double.parseDouble(yPrimeField.getText()));
    }

    /**
     * Draws the window on the canvas using a red color and a 2-pixel wide stroke.
     */
    private void drawWindow(){
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeRect(
                applyRatio(window.getX()),
                applyRatio(window.getY()),
                applyRatio(window.getxPrime()) - applyRatio(window.getX()),
                applyRatio(window.getyPrime()) - applyRatio(window.getY())
        );
        gc.setStroke(Color.BLACK);
    }

    /**
     * Checks if the current window is valid, meaning that its x and y values are less
     * than its xPrime and yPrime values, respectively.
     *
     * @return true if the window segment is valid, false otherwise.
     */
    private boolean isWindowGood(){
        boolean isGood = true;
        if(window.getX() > window.getxPrime() || window.getY() > window.getyPrime()){
            Alert.alertDisplay(MainApplication.getAlert(), "The first point (X, Y) must be smaller than (X', Y')");
            isGood = false;
        } else if (!((window.getX() == windowSize.get(0) && window.getxPrime() == windowSize.get(1) && window.getY() == windowSize.get(2) && window.getyPrime() == windowSize.get(3))
                ||(window.getX() == windowSize.get(0) && window.getxPrime() != windowSize.get(1) && window.getY() != windowSize.get(2) && window.getyPrime() != windowSize.get(3))
                ||(window.getX() != windowSize.get(0) && window.getxPrime() == windowSize.get(1) && window.getY() != windowSize.get(2) && window.getyPrime() != windowSize.get(3))
                ||(window.getX() != windowSize.get(0) && window.getxPrime() != windowSize.get(1) && window.getY() == windowSize.get(2) && window.getyPrime() != windowSize.get(3))
                ||(window.getX() != windowSize.get(0) && window.getxPrime() != windowSize.get(1) && window.getY() != windowSize.get(2) && window.getyPrime() == windowSize.get(3))
                ||(window.getX() != windowSize.get(0) && window.getxPrime() != windowSize.get(1) && window.getY() != windowSize.get(2) && window.getyPrime() != windowSize.get(3)))){
            Alert.alertDisplay(MainApplication.getAlert(), "We are only covering the following: \n" +
                    "\t -[X, X'] x [Y, Y']\n" +
                    "\t -[-\u221e, X'] x [Y, Y']\n" +
                    "\t -[X, +\u221e] x [Y, Y']\n" +
                    "\t -[X, X'] x [-\u221e, Y']\n" +
                    "\t -[X, X'] x [Y, +\u221e]");
            isGood = false;
        }
        return isGood;
    }

    //---------------------ComboBox----------------------------//

    /**
     * Fills the combo box with items corresponding to the segment data files in the current
     * directory path.
     */
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

    /**
     * Deactivates the action event of the combo box temporarily to remove all items from it.
     */
    private void desactivateEventComboBox(){
        EventHandler<ActionEvent> handler = directoryComboBox.getOnAction();
        directoryComboBox.setOnAction(null);
        directoryComboBox.getItems().removeAll(directoryComboBox.getItems());
        directoryComboBox.setOnAction(handler);
    }


    //---------------------Segment----------------------------//

    /**
     * Applies the current ratio to the given value and returns the result.
     *
     * @param value the value to apply the ratio to
     * @return the result of applying the ratio to the value
     */
    private double applyRatio(double value){
        return (value * ratio) + (canvas.getWidth()/2);
    }

    /**
     * Draws the given segment on the canvas.
     *
     * @param segment the segment to draw
     */
    private void drawSegment(Segment segment) {
        gc.strokeLine(
                applyRatio(segment.getX()),
                applyRatio(segment.getY()),
                applyRatio(segment.getxPrime()),
                applyRatio(segment.getyPrime())
        );
    }

    /**
     * Draws all the segment on the canvas.
     */
    public void drawSegments(){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        segments = pstWrapper.getWindow(window, windowSize);
        for (Segment segment : segments) {
            drawSegment(segment);
        }
    }

    /**
     * Loads a segment from a file and updates the PST wrapper.
     *
     * @throws IOException if an I/O error occurs
     */
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

    //--------------------------Canvas-------------------------//
    private void canvasInit(){
        gc = canvas.getGraphicsContext2D();
        gc.scale(1, -1);
        gc.translate(0, -canvas.getHeight());
    }

    //----------------------Canvas Control--------------------//

    /**
     * Resets the canvas zoom and position to their default values.
     */
    private void resetZoomAndPosition(){
        // Reset canvas position
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        // Reset zoom
        canvas.setScaleX(1);
        canvas.setScaleY(1);
        canvas.setTranslateZ(0);
    }

    /**
     * Handles the scroll event to zoom in or out of the canvas.
     *
     * @param event the scroll event
     */
    public void setOnScroll(ScrollEvent event){
        double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
        double currentScale = canvas.getScaleX();
        double newScale = currentScale * zoomFactor;
        canvas.setScaleX(newScale);
        canvas.setScaleY(newScale);
        clearCanvas();
        drawWindow();
        drawSegments();
    }

    /**
     * Handles the mouse press event to enable canvas dragging.
     *
     * @param event the mouse press event
     */
    public void setOnMousePressed(MouseEvent event){
        scrollPane.setCursor(Cursor.MOVE);
        scrollPane.setMouseTransparent(true);
        mouseX = event.getSceneX();
        mouseY = event.getSceneY();
        canvasX = canvas.getLayoutX();
        canvasY = canvas.getLayoutY();
    }

    /**
     * Changes the cursor back to default and makes the scroll pane reactive to mouse events again.
     */
    public void setOnMouseReleased(){
        scrollPane.setCursor(Cursor.DEFAULT);
        scrollPane.setMouseTransparent(false);
    }


    /**
     * Updates the canvas position based on the mouse movement.
     *
     * @param event The mouse event that triggered the method.
     */
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
