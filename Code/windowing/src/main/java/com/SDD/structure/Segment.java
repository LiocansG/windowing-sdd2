package com.SDD.structure;

/**
 * A class representing a line segment with a start point (x,y) and an end point (xPrime, yPrime).
 */
public class Segment{

    private double x;
    private double y;
    private double xPrime;
    private double yPrime;

    /**
     * Creates a new segment with the given coordinates.
     *
     * @param x the x-coordinate of the start point
     * @param y the y-coordinate of the start point
     * @param xPrime the x-coordinate of the end point
     * @param yPrime the y-coordinate of the end point
     */
    public Segment(double x, double y, double xPrime, double yPrime) {
        if (y < yPrime || (y == yPrime && x <= xPrime)) {
            this.x = x;
            this.y = y;
            this.xPrime = xPrime;
            this.yPrime = yPrime;
        } else {
            this.x = xPrime;
            this.y = yPrime;
            this.xPrime = x;
            this.yPrime = y;
        }
    }

    /**
     * Returns the x-coordinate of the start point of this segment.
     *
     * @return the x-coordinate of the start point
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the start point of this segment.
     *
     * @param x the new x-coordinate of the start point
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the start point of this segment.
     *
     * @return the y-coordinate of the start point
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the start point of this segment.
     *
     * @param y the new y-coordinate of the start point
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the x-coordinate of the end point of this segment.
     *
     * @return the x-coordinate of the end point
     */
    public double getxPrime() {
        return xPrime;
    }

    /**
     * Sets the x-coordinate of the end point of this segment.
     *
     * @param xPrime the new x-coordinate of the end point
     */
    public void setxPrime(double xPrime) {
        this.xPrime = xPrime;
    }

    /**
     * Returns the y-coordinate of the end point of this segment.
     *
     * @return the y-coordinate of the end point
     */
    public double getyPrime() {
        return yPrime;
    }

    /**
     * Sets the y-coordinate of the end point of this segment.
     *
     * @param yPrime the new y-coordinate of the end point
     */
    public void setyPrime(double yPrime) {
        this.yPrime = yPrime;
    }

    /**
     * Creates and returns a copy of this segment.
     *
     * @return a new Segment object with the same start and end points as this segment
     */
    public Segment clone() {
        return new Segment(x, y, xPrime, yPrime);
    }

    /**
     * Returns a string representation of this segment.
     *
     * @return a string representation of this segment
     */
    @Override
    public String toString() {
        return "Segment{" +
                "x=" + x +
                ", y=" + y +
                ", xPrime=" + xPrime +
                ", yPrime=" + yPrime +
                '}';
    }
}

