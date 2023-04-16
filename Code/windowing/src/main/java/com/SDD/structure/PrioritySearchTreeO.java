package com.SDD.structure;

import com.SDD.utility.Utility;

import java.util.ArrayList;

/**
 * Extension of a priority search tree for which the coordinates of the segments were all opposite, i.e. X and Y became -X and -Y.
 */
public class PrioritySearchTreeO extends PrioritySearchTree{

    /**
     * Constructs a PrioritySearchTreeO with the given segments, where the order of the segments has been reversed
     *
     * @param segments the list of segments to construct the PrioritySearchTreeO with
     */
    public PrioritySearchTreeO(ArrayList<Segment> segments) {
        super(Utility.opposeArray(segments));
    }
}
