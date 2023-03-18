package structure;

import java.util.ArrayList;

public class PstWrapper {
    private PrioritySearchTree original;
    private PrioritySearchTreeO opposed;
    private PrioritySearchTreeE exchanged;
    private PrioritySearchTreeOE opposed_exchanged;

    public PstWrapper(ArrayList<Segment> segments){
        original = new PrioritySearchTree(segments);
        opposed = new PrioritySearchTreeO(segments);
        exchanged = new PrioritySearchTreeE(segments);
        opposed_exchanged = new PrioritySearchTreeOE(segments);
    }
}
