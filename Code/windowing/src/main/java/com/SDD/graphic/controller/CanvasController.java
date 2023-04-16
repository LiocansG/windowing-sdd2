package com.SDD.graphic.controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * An abstract class for controlling a canvas.
 */
public abstract class CanvasController {

    private static GraphicsContext gc;

    /**
     * Resets the canvas zoom and position to their default values.
     *
     * @param canvas The canvas to reset.
     */
    public static void resetZoomAndPosition(Canvas canvas){
        // Reset canvas position
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        // Reset zoom
        canvas.setScaleX(1);
        canvas.setScaleY(1);
        canvas.setTranslateZ(0);
    }

    /**
     * Initializes the canvas by getting the graphics context, scaling it, translating it, and setting the canvas width.
     *
     * @param canvas The canvas to initialize.
     */
    public static void canvasInit(Canvas canvas){
        gc = canvas.getGraphicsContext2D();
        gc.scale(1, -1);
        gc.translate(0, -canvas.getHeight());
        SegmentController.setCanvasWidth(canvas.getWidth());
    }

    /**
     * Changes the background color of a given canvas to beige.
     *
     * @param canvas The canvas to change the background color of.
     */
    public static void changeCanvasColor(Canvas canvas){
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Returns the graphics context for the canvas.
     *
     * @return The graphics context for the canvas.
     */
    public static GraphicsContext getGc() {
        return gc;
    }
}
