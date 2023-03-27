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
        Heap.heapify(segments);
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

        if(segments.size() > 0){
            // Know were is the median
            int median = segments.size() / 2;
            node.setMedian((int) segments.get(median).getY());
            node.setLeftChild(buildPST(new ArrayList<>(segments.subList(0, median))));
            node.setRightChild(buildPST(new ArrayList<>(segments.subList(median, segments.size()))));
        }

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

    public ArrayList<Segment> windowing(Segment window){
        ArrayList<Segment> segments = new ArrayList<>();
        windowing(root, window, segments);
        return segments;
    }

    public void windowing(PSTNode node, Segment window, ArrayList<Segment> segments){
        if(node == null) return;

        Segment segment = node.getSegment();
        if(checkIn(segment, window) || checkCrossLeft(segment, window) || checkCrossBottom(segment, window)){
            segments.add(segment);
        }

        // we can avoid this if we don't look for segments under window
        if(node.getMedian() >= window.getY()) {
            windowing(node.getLeftChild(), window, segments);
        }

        // only go to right when needed
        if(node.getMedian() <= window.getyPrime()) {
            windowing(node.getRightChild(), window, segments);
        }
    }

    /**
     * Check if the starting point is inside the window
     * @param segment
     * @param window
     * @return
     */
    private boolean checkIn(Segment segment, Segment window) {

        return ((segment.getY() >= window.getY() && segment.getY() <= window.getyPrime()) && (segment.getX() >= window.getX()  && segment.getX() <= window.getxPrime()));
    }

    /**
     * Check if the segment is crossing the bottom border
     * @param segment
     * @param window
     * @return
     */
    private boolean checkCrossBottom(Segment segment, Segment window){

        return ((segment.getX() >= window.getX() && segment.getX() <= window.getxPrime()) && (segment.getY() < window.getY() && segment.getyPrime() >= window.getY()));
    }

    /**
     * Check if the segment is crossing the left border
     * @param segment
     * @param window
     * @return
     */
    private boolean checkCrossLeft(Segment segment, Segment window){

        return ((segment.getY() >= window.getY() && segment.getY() <= window.getyPrime()) && (segment.getX() < window.getX() && segment.getxPrime() >= window.getX()));
    }

}
