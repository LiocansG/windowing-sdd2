package com.SDD.structure;

import com.SDD.utilities.Utility;

import java.util.ArrayList;

/**
 * Extension of the Priority Search Tree that reverses the order of the segments to prioritize segments with higher y-coordinates over those with lower y-coordinates.
 */
public class PrioritySearchTreeO extends PrioritySearchTree{

    /**
     * Constructs a PrioritySearchTreeO with the given segments, where the order of the segments has been reversed to prioritize segments with higher y-coordinates over those with lower y-coordinates.
     *
     * @param segments the list of segments to construct the PrioritySearchTreeO with
     */
    public PrioritySearchTreeO(ArrayList<Segment> segments) {
        super(Utility.opposeArray(segments));
    }

}
