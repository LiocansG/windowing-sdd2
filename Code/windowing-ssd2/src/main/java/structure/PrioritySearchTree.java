package structure;
import java.util.ArrayList;
import java.util.List;

public class PrioritySearchTree {

    private PSTNode root;

    public PrioritySearchTree(List<Segment> segments) {
        this.root = buildPst(segments);
    }

    private PSTNode buildPst(List<Segment> segments) {
        if (segments.isEmpty()) {
            return null;
        }

        // Compute the median segment
        int medianIndex = segments.size() / 2;
        Segment median = findMedianSegment(segments, medianIndex);

        // Split the segments into left and right sets
        List<Segment> leftSegments = new ArrayList<>();
        List<Segment> rightSegments = new ArrayList<>();
        for (Segment segment : segments) {
            if (segment.getY() <= median.getY()) {
                leftSegments.add(segment);
            } else {
                rightSegments.add(segment);
            }
        }

        // Recursively build the subtrees
        PSTNode leftChild = buildPst(leftSegments);
        PSTNode rightChild = buildPst(rightSegments);

        // Create the node for the median segment
        PSTNode node = new PSTNode(median, leftChild, rightChild);
        return node;
    }

    private Segment findMedianSegment(List<Segment> segments, int medianIndex) {
        int size = segments.size();
        if (size == 1) {
            return segments.get(0);
        }

        // Compute the median of the x-coordinates
        List<Integer> xCoordinates = new ArrayList<>();
        for (Segment segment : segments) {
            xCoordinates.add(segment.getX());
            xCoordinates.add(segment.getXPrime());
        }
        int medianX = findMedian(xCoordinates);

        // Partition the segments into left and right sets based on the median x-coordinate
        List<Segment> leftSegments = new ArrayList<>();
        List<Segment> rightSegments = new ArrayList<>();
        for (Segment segment : segments) {
            if (segment.getXPrime() < medianX) {
                leftSegments.add(segment);
            } else if (segment.getX() > medianX) {
                rightSegments.add(segment);
            } else {
                // The segment straddles the median, so count it in both sets
                leftSegments.add(segment);
                rightSegments.add(segment);
            }
        }

        int leftSize = leftSegments.size();
        if (medianIndex < leftSize) {
            // The median is in the left set
            return findMedianSegment(leftSegments, medianIndex);
        } else if (medianIndex > leftSize) {
            // The median is in the right set
            return findMedianSegment(rightSegments, medianIndex - leftSize);
        } else {
            // The median is the medianX-coordinate
            return new Segment(medianX, 0, medianX, Integer.MAX_VALUE);
        }
    }

    private int findMedian(List<Integer> list) {
        int size = list.size();
        int medianIndex = size / 2;
        int left = 0;
        int right = size - 1;
        while (true) {
            int pivotIndex = partition(list, left, right);
            if (pivotIndex == medianIndex) {
                return list.get(pivotIndex);
            } else if (pivotIndex < medianIndex) {
                left = pivotIndex + 1;
            } else {
                right = pivotIndex - 1;
            }
        }
    }

    private int partition(List<Integer> list, int left, int right) {
        int pivotValue = list.get(right);
        int pivotIndex = left;
        for (int i = left; i < right; i++) {
            if (list.get(i) <= pivotValue) {
                swap(list, i, pivotIndex);
                pivotIndex++;
            }
        }
        swap(list, pivotIndex, right);
        return pivotIndex;
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public List<Segment> search(int y) {
        List<Segment> segments = new ArrayList<>();
        search(root, y, segments);
        return segments;
    }

    private void search(PSTNode node, int y, List<Segment> segments) {
        if (node == null) {
            return;
        }
        if (node.getSegment().getY() <= y && node.getSegment().getYPrime() >= y) {
            segments.add(node.getSegment());
        }
        if (node.getLeftChild() != null && node.getLeftChild().getMedian() >= y) {
            search(node.getLeftChild(), y, segments);
        }
        if (node.getRightChild() != null && node.getRightChild().getMedian() >= y) {
            search(node.getRightChild(), y, segments);
        }
    }
}
