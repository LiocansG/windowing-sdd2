package com.SDD.structure;

import com.SDD.utility.Utility;

import java.util.ArrayList;

/**
 * Extension of a priority search tree for which the coordinates of the segments have been exchanged, i.e. the X and Y have been swapped.
 */
public class PrioritySearchTreeE extends PrioritySearchTree{

    /**
     * Constructs a PrioritySearchTreeE with the given segments, where the order of the segments has been exchanged
     *
     * @param segments the list of segments to construct the PrioritySearchTreeE with
     */
    public PrioritySearchTreeE(ArrayList<Segment> segments) {
        super(Utility.exchangeArray(segments));
    }
}
