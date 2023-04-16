package com.SDD.graphic.controller;

import com.SDD.graphic.MainApplication;
import com.SDD.structure.Segment;
import com.SDD.utility.Alert;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * An abstract class for controlling window on a canvas.
 */
public abstract class WindowController {

    /**
     * Changes the values of the window based on the current values in the text fields.
     *
     * @param window the window segment to be updated
     * @param windowSize an ArrayList containing the maximum and minimum x and y values for the window
     * @param xField the text field for the x-coordinate of the window's starting point
     * @param yField the text field for the y-coordinate of the window's starting point
     * @param xPrimeField the text field for the x-coordinate of the window's ending point
     * @param yPrimeField the text field for the y-coordinate of the window's ending point
     */
    public static void changeWindow(Segment window, ArrayList<Double> windowSize, TextField xField, TextField yField, TextField xPrimeField, TextField yPrimeField){
        window.setX((xField.getText().contains("\u221E") || Double.parseDouble(xField.getText()) < windowSize.get(0)) ? windowSize.get(0): Double.parseDouble(xField.getText()));
        window.setY((yField.getText().contains("\u221E") || Double.parseDouble(yField.getText()) < windowSize.get(2)) ? windowSize.get(2) : Double.parseDouble(yField.getText()));
        window.setxPrime((xPrimeField.getText().contains("\u221E") || Double.parseDouble(xPrimeField.getText()) > windowSize.get(1)) ? windowSize.get(1) : Double.parseDouble(xPrimeField.getText()));
        window.setyPrime((yPrimeField.getText().contains("\u221E") || Double.parseDouble(yPrimeField.getText()) > windowSize.get(3))? windowSize.get(3) : Double.parseDouble(yPrimeField.getText()));
    }

    /**
     * Draws the window on the canvas using a red color and a 2-pixel wide stroke.
     *
     * @param gc the graphics context of the canvas on which the window is to be drawn
     * @param window the window segment to be drawn
     */
    public static void drawWindow(GraphicsContext gc, Segment window){
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeRect(
                SegmentController.applyRatio(window.getX()),
                SegmentController.applyRatio(window.getY()),
                SegmentController.applyRatio(window.getxPrime()) - SegmentController.applyRatio(window.getX()),
                SegmentController.applyRatio(window.getyPrime()) - SegmentController.applyRatio(window.getY())
        );
        gc.setStroke(Color.BLACK);
    }

    /**
     * Checks if the current window is valid, meaning that its x and y values are less
     * than its xPrime and yPrime values, respectively.
     *
     * @param window the window segment to be validated
     * @param windowSize an ArrayList containing the maximum and minimum x and y values for the window
     * @return true if the window segment is valid, false otherwise
     */
    public static boolean isWindowGood(Segment window, ArrayList<Double> windowSize){
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
}
