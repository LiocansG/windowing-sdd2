package structure;

import java.util.ArrayList;

/**
 * Exchanged Priority Search Tree
 */
public class PrioritySearchTreeE extends PrioritySearchTree{
    public PrioritySearchTreeE(ArrayList<Segment> segments) {
        super(exchangeArray(segments));
    }

}
