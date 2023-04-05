package com.SDD.graphic.controller;

import com.SDD.structure.PstWrapper;
import com.SDD.structure.Segment;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * An abstract class for controlling segments on a canvas.
 */
public abstract class SegmentController {

    private static double ratio;
    private static double canvasWidth;

    /**
     * Applies the current ratio to the given value and returns the result.
     *
     * @param value The value to apply the ratio to.
     * @return The result of applying the ratio to the value.
     */
    public static double applyRatio(double value){
        return (value * ratio) + (canvasWidth/2);
    }

    /**
     * Draws the given segment on the canvas using the provided graphics context.
     *
     * @param segment The segment to draw.
     * @param gc The graphics context to use for drawing.
     */
    private static void drawSegment(Segment segment, GraphicsContext gc) {
        gc.strokeLine(
                applyRatio(segment.getX()),
                applyRatio(segment.getY()),
                applyRatio(segment.getxPrime()),
                applyRatio(segment.getyPrime())
        );
    }

    /**
     * Draws all the segments in the given window on the canvas using the provided graphics context and updates the number of segments.
     *
     * @param window The window of segments to draw.
     * @param windowSize The size of the window.
     * @param pstWrapper The PstWrapper containing the segments.
     * @param gc The graphics context to use for drawing.
     * @param numberSegment The Text object representing the number of segments.
     */
    public static void drawSegments(Segment window, ArrayList<Double> windowSize, PstWrapper pstWrapper, GraphicsContext gc, Text numberSegment){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        ArrayList<Segment> segments = pstWrapper.getWindow(window, windowSize);
        for (Segment segment : segments) {
            drawSegment(segment, gc);
        }
        numberSegment.setText("Number of segment: " + segments.size());
    }

    /**
     * Sets the ratio for the segment controller.
     *
     * @param newRatio The new ratio to set.
     */
    public static void setRatio(double newRatio){
        ratio = newRatio;
    }

    /**
     * Sets the canvas width for the segment controller.
     *
     * @param newCanvasWidth The new canvas width to set.
     */
    public static void setCanvasWidth(double newCanvasWidth){
        canvasWidth = newCanvasWidth;
    }
}
