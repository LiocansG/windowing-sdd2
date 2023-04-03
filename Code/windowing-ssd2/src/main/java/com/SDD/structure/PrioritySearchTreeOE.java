package com.SDD.structure;

import com.SDD.utilities.Utility;

import java.util.ArrayList;

/**
 * Extension of the Priority Search Tree that reverses the order of the segments to prioritize segments with higher y-coordinates over those with lower y-coordinates, and also exchanges the order of the segments to prioritize the y-coordinate over the x-coordinate.
 */
public class PrioritySearchTreeOE extends PrioritySearchTree{

    /**
     * Constructs a PrioritySearchTreeOE with the given segments, where the order of the segments has been reversed to prioritize segments with higher y-coordinates over those with lower y-coordinates, and the order has also been exchanged to prioritize the y-coordinate over the x-coordinate.
     *
     * @param segments the list of segments to construct the PrioritySearchTreeOE with
     */
    public PrioritySearchTreeOE(ArrayList<Segment> segments) {
        super(Utility.opposeArray(Utility.exchangeArray(segments)));
    }

}
