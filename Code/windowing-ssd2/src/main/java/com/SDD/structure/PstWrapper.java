package com.SDD.structure;

import com.SDD.utilities.Utility;

import java.util.ArrayList;

/**
 * Wrapper class for Priority Search Trees that provides efficient windowing for specific types of windows.
 */
public class PstWrapper {
    private PrioritySearchTree original;
    private PrioritySearchTreeO opposed;
    private PrioritySearchTreeE exchanged;
    private PrioritySearchTreeOE opposed_exchanged;

    /**
     * Constructs a PstWrapper with the given list of segments, which initializes several Priority Search Trees with different segment orders to provide efficient windowing for specific types of windows.
     *
     * @param segments the list of segments to construct the Priority Search Trees with
     */
    public PstWrapper(ArrayList<Segment> segments){
        original = new PrioritySearchTree(segments);
        opposed = new PrioritySearchTreeO(segments);
        exchanged = new PrioritySearchTreeE(segments);
        opposed_exchanged = new PrioritySearchTreeOE(segments);
    }


    /**
     * Returns a list of segments that intersect the given window. Uses the most efficient Priority Search Tree for the type of window.
     *
     * @param window the window to find segments that intersect it
     * @param windowSize a list containing the size of the window in the following order: [left bound, right bound, bottom bound, top bound]
     * @return a list of segments that intersect the given window
     */
    public ArrayList<Segment> getWindow(Segment window, ArrayList<Double> windowSize){
        //[-∞, Y] x [X', Y']
        if(window.getX() == windowSize.get(0)) {
            return original.windowing(window);
        }

        //[X, Y] x [+∞, Y']
        if(window.getxPrime() == windowSize.get(1)) {
            // oppose coordinates to be able to use efficient windowing
            ArrayList<Segment> segments = opposed.windowing(Utility.oppose(window));
            // recover coordinates to original state
            return Utility.opposeArray(segments);
        }

        //[X, -∞] x [X', Y']
        if(window.getY() == windowSize.get(2)) {
            // exchange coordinates to be able to use efficient windowing
            ArrayList<Segment> segments = exchanged.windowing(Utility.exchange(window));
            // recover coordinates to original state
            return Utility.exchangeArray(segments);
        }

        //[X, Y] x [X', +∞]
        if(window.getyPrime() == windowSize.get(3)) {
            // oppose and exchange coordinates to be able to use efficient windowing
            ArrayList<Segment> segments = opposed_exchanged.windowing(Utility.oppose(Utility.exchange(window)));
            // recover coordinates to original state
            return  Utility.opposeArray(Utility.exchangeArray(segments));
        }
        return null;
    }
}
