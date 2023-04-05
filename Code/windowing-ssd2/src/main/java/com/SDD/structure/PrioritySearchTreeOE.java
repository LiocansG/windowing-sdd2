package com.SDD.structure;

import com.SDD.utility.Utility;

import java.util.ArrayList;

/**
 * Extension of a priority search tree for which the coordinates of the segments have been swapped and opposed, i.e. the X and Y have been swapped and then opposed.
 */
public class PrioritySearchTreeOE extends PrioritySearchTree{

    /**
     * Constructs a PrioritySearchTreeOE with the given segments, where the order of the segments has been reversed and exchanged
     *
     * @param segments the list of segments to construct the PrioritySearchTreeOE with
     */
    public PrioritySearchTreeOE(ArrayList<Segment> segments) {
        super(Utility.opposeArray(Utility.exchangeArray(segments)));
    }
}
