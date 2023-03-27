package structure;

import utilities.SegmentCreator;
import utilities.Utility;

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

    public ArrayList<Segment> getWindow(Segment window, ArrayList<Double> windowSize){
        //[-∞, Y] x X', Y']
        if(window.getX() == windowSize.get(0)) {
            return original.windowing(window);
        }

        //[X, Y] x [+∞, Y']
        if(window.getxPrime() == windowSize.get(1)) {
            // oppose coordinates to be able to use efficient windowing
            ArrayList<Segment> segments = opposed.windowing(Utility.oppose(window));
            // recover coordinates to original state
            return Utility.opposeArray(segments);
        }

        //[X, -∞] x [X', Y']
        if(window.getY() == windowSize.get(2)) {
            // exchange coordinates to be able to use efficient windowing
            ArrayList<Segment> segments = exchanged.windowing(Utility.exchange(window));
            // recover coordinates to original state
            return Utility.exchangeArray(segments);
        }

        //[X, Y] x [X', +∞]
        if(window.getyPrime() == windowSize.get(3)) {
            // oppose and exchange coordinates to be able to use efficient windowing
            ArrayList<Segment> segments = opposed_exchanged.windowing(Utility.oppose(Utility.exchange(window)));
            // recover coordinates to original state
            return  Utility.opposeArray(Utility.exchangeArray(segments));
        }

        return null;
    }
}
