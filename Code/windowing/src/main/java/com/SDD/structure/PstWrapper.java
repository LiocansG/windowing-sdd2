package com.SDD.structure;

import com.SDD.utility.Utility;

import java.util.ArrayList;

/**
 * Wrapper class for Priority Search Trees that provides efficient windowing for specific types of windows.
 */
public class PstWrapper {
    private final PrioritySearchTree original;
    private final PrioritySearchTreeO opposed;
    private final PrioritySearchTreeE exchanged;
    private final PrioritySearchTreeOE opposedExchanged;

    /**
     * Constructs a PstWrapper with the given list of segments, which initializes several Priority Search Trees with different segment orders to provide efficient windowing for specific types of windows.
     *
     * @param segments the list of segments to construct the Priority Search Trees with
     */
    public PstWrapper(ArrayList<Segment> segments){
        original = new PrioritySearchTree(segments);
        opposed = new PrioritySearchTreeO(segments);
        exchanged = new PrioritySearchTreeE(segments);
        opposedExchanged = new PrioritySearchTreeOE(segments);
    }


    /**
     * Returns a list of segments that intersect the given window. Uses the most efficient Priority Search Tree for the type of window.
     *
     * @param window the window to find segments that intersect it
     * @param windowSize a list containing the size of the window in the following order: [left bound, right bound, bottom bound, top bound]
     * @return a list of segments that intersect the given window
     */
    public ArrayList<Segment> getWindow(Segment window, ArrayList<Double> windowSize){

        if(window.getX() == windowSize.get(0) && window.getxPrime() == windowSize.get(1) && window.getY() == windowSize.get(2) && window.getyPrime() == windowSize.get(3)){
            return original.getAllSegments();
        }

        //[-∞, Y] x [X', Y']
        if(window.getX() == windowSize.get(0)) {
            return original.windowing(window);
        }

        //[X, Y] x [+∞, Y']
        if(window.getxPrime() == windowSize.get(1)) {
            ArrayList<Segment> segments = opposed.windowing(Utility.oppose(window));
            return Utility.opposeArray(segments);
        }

        //[X, -∞] x [X', Y']
        if(window.getY() == windowSize.get(2)) {
            ArrayList<Segment> segments = exchanged.windowing(Utility.exchange(window));
            return Utility.exchangeArray(segments);
        }

        //[X, Y] x [X', +∞]
        if(window.getyPrime() == windowSize.get(3)) {
            ArrayList<Segment> segments = opposedExchanged.windowing(Utility.oppose(Utility.exchange(window)));
            return  Utility.opposeArray(Utility.exchangeArray(segments));
        }

        //[X, Y] x [X', Y']
        ArrayList<Segment> segments = original.windowing(window);
        segments.addAll(Utility.exchangeArray(exchanged.windowing(Utility.exchange(window))));
        return segments;
    }

    /**
     * Return the Original PST
     *
     * @return Return the Original PST
     */
    public PrioritySearchTree getOriginal() {
        return original;
    }

    /**
     * Return the opposed PST
     *
     * @return Return the Original PST
     */
    public PrioritySearchTreeO getOpposed() {
        return opposed;
    }

    /**
     * Return the exchanged PST
     *
     * @return Return the Original PST
     */
    public PrioritySearchTreeE getExchanged() {
        return exchanged;
    }

    /**
     * Return the Opposed and Exchanged PST
     *
     * @return Return the Original PST
     */
    public PrioritySearchTreeOE getOpposedExchanged() {
        return opposedExchanged;
    }

}
