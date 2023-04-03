package com.SDD.structure;

import com.SDD.utilities.Utility;

import java.util.ArrayList;

/**
 * Extension of the Priority Search Tree that exchanges the order of the segments to prioritize the y-coordinate over the x-coordinate.
 */
public class PrioritySearchTreeE extends PrioritySearchTree{

    /**
     * Constructs a PrioritySearchTreeE with the given segments, where the order of the segments has been exchanged to prioritize the y-coordinate over the x-coordinate.
     *
     * @param segments the list of segments to construct the PrioritySearchTreeE with
     */
    public PrioritySearchTreeE(ArrayList<Segment> segments) {
        super(Utility.exchangeArray(segments));
    }

}
