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

    public ArrayList<Segment> getWindow(Segment window, ArrayList<Double> windowsSize) {
        window = window.clone();
        //System.out.println("selected window "+window);

        if(window.getX() == windowsSize.get(0)) {
            //System.out.println("case [-∞, X']x[Y, Y']");
            return getLeftWindow(window);
        }

        if(window.getxPrime() == windowsSize.get(1)) {
            //System.out.println("case case [X, +∞]x[Y, Y']");
            return getRightWindow(window);
        }

        if(window.getY() == windowsSize.get(2)) {
            //System.out.println("case [X, X']x[-∞, Y']");
            return getDownWindow(window);
        }

        if(window.getyPrime() == windowsSize.get(3)) {
            //System.out.println("case [X, X']x[Y, +∞]");
            return getUpWindow(window);
        }

        //System.out.println("case [X, X']x[Y, Y']");
        return getClosedWindow(window);
    }

    private ArrayList<Segment> getLeftWindow(Segment window) {
        // this one is always efficient using windowing
        return original.windowing(window, true, true);
    }

    private ArrayList<Segment> getRightWindow(Segment window) {
        // oppose coordinates to be able to use efficient windowing
        ArrayList<Segment> segments = opposed.windowing(window.oppose(), true, true);
        // recover coordinates to original state
        return PrioritySearchTree.opposeArray(segments);
    }

    private ArrayList<Segment> getDownWindow(Segment window) {
        // exchange coordinates to be able to use efficient windowing
        ArrayList<Segment> segments = exchanged.windowing(window.exchange(), true, true);
        // recover coordinates to original state
        return PrioritySearchTree.exchangeArray(segments);
    }

    private ArrayList<Segment> getUpWindow(Segment window) {
        // oppose and exchange coordinates to be able to use efficient windowing
        ArrayList<Segment> segments = opposed_exchanged.windowing(window.oppose().exchange(), true, true);
        // recover coordinates to original state

        return PrioritySearchTree.exchangeArray(PrioritySearchTree.opposeArray(segments));
    }

    private ArrayList<Segment> getClosedWindow(Segment window) {
        // get original left and center but not down to remove left children
        ArrayList<Segment> response = original.windowing(window, false, true);

        // get down with left of exchanged window
        ArrayList<Segment> down = exchanged.windowing(window.exchange(), false, false);
        // recover coordinates to original state
        response.addAll(PrioritySearchTree.exchangeArray(down));
        return response;
    }
}
