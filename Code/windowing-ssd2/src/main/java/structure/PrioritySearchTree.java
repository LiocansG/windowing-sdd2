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

    public ArrayList<Segment> query(int minX, int maxX, int minY, int maxY) {
        ArrayList<Segment> result = new ArrayList<>();
        query(root, minX, maxX, minY, maxY, result);
        return result;
    }

    private void query(PSTNode node, int minX, int maxX, int minY, int maxY, ArrayList<Segment> result) {
        if (node == null || node.getMaxYLeft() < minY || node.getMinYLeft() > maxY) {
            return;
        }
        if (node.getMedian() >= minX && node.getSegment().getX() <= maxX && node.getSegment().getY() <= maxY && node.getSegment().getyPrime() >= minY) {
            result.add(node.getSegment());
        }
        if (node.getLeftChild() != null && node.getMaxYLeft() >= minY) {
            query(node.getLeftChild(), minX, maxX, minY, maxY, result);
        }
        if (node.getRightChild() != null && node.getMinXRight() <= maxY) {
            query(node.getRightChild(), minX, maxX, minY, maxY, result);
        }
    }

    public PSTNode getRoot() {
        return root;
    }

    static ArrayList<Segment> opposeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> opposedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            opposedSegments.add(segments.get(i).clone());
            opposedSegments.get(i).oppose();
        }
        return opposedSegments;
    }

    static ArrayList<Segment> exchangeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> exchangedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            exchangedSegments.add(segments.get(i).clone());
            exchangedSegments.get(i).exchange();
        }
        return exchangedSegments;
    }
}
