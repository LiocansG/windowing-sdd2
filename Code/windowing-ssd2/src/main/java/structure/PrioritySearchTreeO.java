package structure;

import java.util.ArrayList;

/**
 * Opposed Priority Search Tree
 */
public class PrioritySearchTreeO extends PrioritySearchTree{
    public PrioritySearchTreeO(ArrayList<Segment> segments) {
        super(opposeArray(segments));
    }

}
