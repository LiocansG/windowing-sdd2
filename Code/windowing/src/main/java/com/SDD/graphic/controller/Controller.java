package com.SDD.graphic.controller;

import com.SDD.graphic.MainApplication;
import com.SDD.structure.PstWrapper;
import com.SDD.structure.Segment;
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
import java.net.URISyntaxException;
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
    private Segment window;
    private double canvasX, canvasY, mouseX, mouseY = 0;

    /**
     * Initializes the Controller instance by setting up the user interface and loading the
     * initial segment data.
     *
     * @throws IOException if there is an error reading the segment data from file.
     */
    @FXML
    public void initialize() throws IOException {
        CanvasController.canvasInit(canvas);
        ComboBoxController.fillComboBoxItem(directoryComboBox, path);
        loadingSegmentFromFile();
        window = new Segment(windowSize.get(0), windowSize.get(2), windowSize.get(1), windowSize.get(3));
        resetCoordinates();
    }

    //------------------------FILE------------------------------//
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

        SegmentController.setRatio(Math.min(canvas.getWidth() / (Math.abs(windowSize.get(0)) + Math.abs(windowSize.get(2))), canvas.getHeight() / (Math.abs(windowSize.get(1)) + Math.abs(windowSize.get(3)))));

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
            ComboBoxController.fillComboBoxItem(directoryComboBox, path);
        }
    }


    //---------------------Drawing-----------------------------//

    /**
     * Clears the canvas and resets the zoom and position. Draws the window and all segments
     * that are contained within it.
     */
    public void clearCanvas(){
        CanvasController.getGc().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Clears the canvas by filling it with a transparent color and saves the current state
     * of the graphics context.
     */
    public void draw(){
        clearCanvas();
        CanvasController.resetZoomAndPosition(canvas);
        WindowController.changeWindow(window, windowSize, xField, yField, xPrimeField, yPrimeField);
        if(WindowController.isWindowGood(window, windowSize)){
            CanvasController.changeCanvasColor(canvas);
            WindowController.drawWindow(CanvasController.getGc(), window);
            SegmentController.drawSegments(window, windowSize, pstWrapper, CanvasController.getGc(), numberSegment);
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


    //----------------------Canvas Control--------------------//

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
        canvasX = canvas.getTranslateX();
        canvasY = canvas.getTranslateY();
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
