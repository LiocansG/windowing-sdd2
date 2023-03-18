package structure;

import java.util.ArrayList;

/**
 * Exchanged & Opposed Priority Search Tree
 */
public class PrioritySearchTreeOE extends PrioritySearchTree{

    public PrioritySearchTreeOE(ArrayList<Segment> segments) {
        super(opposeArray(exchangeArray(segments)));
    }

}
