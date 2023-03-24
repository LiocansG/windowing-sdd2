package structure;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Original Priority Search Tree
 */
public class PrioritySearchTree {

    private PSTNode root;

    public PrioritySearchTree(ArrayList<Segment> segments){

        // Sort the segments by their y-coordinate
        segments.sort(Segment::compareTo);
        root = buildPST(segments);
    }

    private PSTNode buildPST(ArrayList<Segment> segments){

        // If there is nothing in the segments
        if(segments.isEmpty()){
            return null;
        }

        // If there is only one element in the segments
        if(segments.size() == 1){
            return new PSTNode(segments.get(0));
        }

        // find the root (lower x coordinate)
        Segment segmentRoot = findRoot(segments);
        segments.remove(segmentRoot);
        PSTNode node = new PSTNode(segmentRoot);


        // Know were is the median
        int median = segments.size() / 2;

        node.setMedian((int) node.getSegment().getY());
        node.setLeftChild(buildPST(new ArrayList<Segment>(segments.subList(0, median))));
        node.setRightChild(buildPST(new ArrayList<Segment>(segments.subList(median, segments.size()))));

        return node;
    }

    private Segment findRoot(ArrayList<Segment> segments) {
        Segment segmentWithLowestX = null;
        for (Segment segment : segments) {
            if (segmentWithLowestX == null || segment.getX() < segmentWithLowestX.getX()) {
                segmentWithLowestX = segment;
            }
        }
        return segmentWithLowestX;
    }

    public PSTNode getRoot() {
        return root;
    }

    static ArrayList<Segment> opposeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> opposedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            opposedSegments.add(segments.get(i).oppose());
        }
        return opposedSegments;
    }

    static ArrayList<Segment> exchangeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> exchangedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            exchangedSegments.add(segments.get(i).exchange());
        }
        return exchangedSegments;
    }

    public ArrayList<Segment> windowing(Segment window, boolean down, boolean center) {
        window = window.clone();

        ArrayList<Segment> reported = new ArrayList<>();
        subWindowing(root, window, reported, down, center);

        return reported;
    }

    private void subWindowing(PSTNode node, Segment window, ArrayList<Segment> reported, boolean down, boolean center) {
        if(node == null) return;

        Segment s = node.getSegment();

        // if we don't need center, we can cut earlier
        if(!center && s.getX() > window.getX()) return;

        // segment and all child nodes are below window
        if(s.getX() > window.getxPrime()) return;

        // check if this node has to be reported, only one report can match a segment
        if(center && reportCenter(s, window)) reported.add(s);
        if(down && reportDown(s, window)) reported.add(s);
        if(reportLeft(s, window)) reported.add(s);

        // we can avoid this if we don't look for segments under window
        if(down || node.getMedian() >= window.getY()) {
            subWindowing(node.getLeftChild(), window, reported, down, center);
        }

        // only go to right when needed
        if(node.getMedian() <= window.getyPrime()) {
            subWindowing(node.getRightChild(), window, reported, down, center);
        }
    }

    /**
     * Check that segment first point is into window. First point has lowest Y coordinate.
     * @param s Segment to check.
     * @param window Window into which point may be.
     * @return True if segment first point is into window. False otherwise.
     */
    private boolean reportCenter(Segment s, Segment window) {
        // minimum Y in window center
        if(window.getY() <= s.getY() && window.getyPrime() >= s.getY()) {
            // corresponding X in window center
            if(window.getX() <= s.getX() && window.getxPrime() >= s.getX()) {
                return true;
            }
        }

        return false;
    }
    /**
     * Check that segment is crossing window down border (Starting before and stopping on border or ending after).
     * @param s Segment to check.
     * @param window Window that segment may cross.
     * @return True if segment is crossing window down border. False otherwise.
     */
    private boolean reportDown(Segment s, Segment window) {
        // X1 in window
        if(window.getX() <= s.getX() && s.getX() <= window.getxPrime()) {
            // crossing window Y1 and starting before
            if(s.getY() < window.getY() && s.getyPrime() >= window.getY()) {
                return true;
            }
        }

        return false;
    }
    /**
     * Check that segment is crossing window left border (Starting before and stopping on border or ending after).
     * @param s Segment to check.
     * @param window Window that segment may cross.
     * @return True if segment is crossing window left border. False otherwise.
     */
    private boolean reportLeft(Segment s, Segment window) {
        // Y1 in window
        if(window.getY() <= s.getY() && s.getY() <= window.getyPrime()) {
            // crossing window X1 and starting before
            if(s.getX() < window.getX() && s.getxPrime() >= window.getX()) {
                return true;
            }
        }
        return false;
    }
}
