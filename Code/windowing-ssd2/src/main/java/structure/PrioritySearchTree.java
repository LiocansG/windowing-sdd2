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

}
