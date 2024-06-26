package com.SDD.structure;
import java.util.ArrayList;

/**
 * Original Priority Search Tree
 */
public class PrioritySearchTree {

    private final PSTNode root;

    /**
     * Builds a Priority Search Tree using a list of segments.
     *
     * @param segments the list of segments to use for building the tree
     */
    public PrioritySearchTree(ArrayList<Segment> segments){

        // Sort the segments by their y-coordinate
        Heap.sort(segments);
        root = buildPST(segments);
    }


    /**
     * Recursively builds the Priority Search Tree by dividing the segments and creating nodes.
     *
     * @param segments the list of segments to use for building the tree
     * @return the root node of the Priority Search Tree
     */
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
            node.setMedian(segments.get(median).getY());
            node.setLeftChild(buildPST(new ArrayList<>(segments.subList(0, median))));
            node.setRightChild(buildPST(new ArrayList<>(segments.subList(median, segments.size()))));
        }

        return node;
    }


    /**
     * Finds the segment with the lowest x-coordinate in the list of segments.
     *
     * @param segments the list of segments to search through
     * @return the segment with the lowest x-coordinate
     */
    private Segment findRoot(ArrayList<Segment> segments) {
        Segment segmentWithLowestX = null;
        for (Segment segment : segments) {
            if (segmentWithLowestX == null || segment.getX() < segmentWithLowestX.getX()) {
                segmentWithLowestX = segment;
            }
        }
        return segmentWithLowestX;
    }

    /**
     * Gets the root node of the Priority Search Tree.
     *
     * @return the root node of the Priority Search Tree
     */
    public PSTNode getRoot() {
        return root;
    }

    /**
     * Get all the segments of the PST
     *
     * @return the arraylist of all the segment of the PST
     */
    public ArrayList<Segment> getAllSegments() {
        return getAllSegments(root);
    }

    /**
     * Get all the segments of the PST
     *
     * @param root root node of the PST
     * @return the arraylist of all the segment of the PST
     */
    private ArrayList<Segment> getAllSegments(PSTNode root) {
        ArrayList<Segment> segments = new ArrayList<>();
        if (root != null) {
            segments.add(root.getSegment());
            segments.addAll(getAllSegments(root.getLeftChild()));
            segments.addAll(getAllSegments(root.getRightChild()));
        }
        return segments;
    }

    /**
     * Returns the list of segments that intersect with the given window.
     *
     * @param window the window to search for intersecting segments
     * @return the list of intersecting segments
     */
    public ArrayList<Segment> windowing(Segment window){
        ArrayList<Segment> segments = new ArrayList<>();
        windowing(root, window, segments);
        return segments;
    }

    /**
     * Recursively searches through the Priority Search Tree to find segments that intersect with the given window.
     *
     * @param node the current node being searched
     * @param window the window to search for intersecting segments
     * @param segments the list of intersecting segments
     */
    private void windowing(PSTNode node, Segment window, ArrayList<Segment> segments){
        if(node == null) return;

        Segment segment = node.getSegment();
        if(checkIn(segment, window) || checkCrossLeft(segment, window) || checkCrossBottom(segment, window)){
            segments.add(segment);
        }

       /* if(node.getMedian() >= window.getY()) {
            windowing(node.getLeftChild(), window, segments);
        }*/

        windowing(node.getLeftChild(), window, segments);

        if(node.getMedian() <= window.getyPrime()) {
            windowing(node.getRightChild(), window, segments);
        }
    }

    /**
     * Check if the starting point is inside the window
     *
     * @param segment the line segment to check
     * @param window the rectangular window to check against
     * @return true if the starting point of the line segment is inside the window, false otherwise
     */
    private boolean checkIn(Segment segment, Segment window) {

        return ((segment.getY() >= window.getY() && segment.getY() <= window.getyPrime()) && (segment.getX() >= window.getX()  && segment.getX() <= window.getxPrime()));
    }


    /**
     * Check if the segment is crossing the bottom border
     *
     * @param segment the line segment to check
     * @param window the rectangular window to check against
     * @return true if the line segment crosses the bottom border of the window, false otherwise
     */
    private boolean checkCrossBottom(Segment segment, Segment window){

        return ((segment.getX() >= window.getX() && segment.getX() <= window.getxPrime()) && (segment.getY() < window.getY() && segment.getyPrime() >= window.getY()));
    }

    /**
     * Check if the segment is crossing the left border
     *
     * @param segment the line segment to check
     * @param window the rectangular window to check against
     * @return true if the line segment crosses the left border of the window, false otherwise
     */
    private boolean checkCrossLeft(Segment segment, Segment window){

        return ((segment.getY() >= window.getY() && segment.getY() <= window.getyPrime()) && (segment.getX() < window.getX() && segment.getxPrime() >= window.getX()));
    }

}
