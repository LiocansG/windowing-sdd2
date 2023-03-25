package structure;

import utilities.Utility;

import java.util.ArrayList;

/**
 * Exchanged & Opposed Priority Search Tree
 */
public class PrioritySearchTreeOE extends PrioritySearchTree{

    public PrioritySearchTreeOE(ArrayList<Segment> segments) {
        super(Utility.opposeArray(Utility.exchangeArray(segments)));
    }

}
