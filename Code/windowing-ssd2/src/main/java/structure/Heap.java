package structure;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Heap {

    public static void heapify(ArrayList<Segment> segments){
        int size = segments.size();
        for(int i = size / 2 - 1; i >= 0; i--){
            heapify(segments, i, size);
        }
    }

    public static void heapify(ArrayList<Segment> segments, int index, int size) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        // Find largest element among root, left child, and right child
        if (left < size && segments.get(left).getY() > segments.get(largest).getY()) {
            largest = left;
        }

        if (right < size && segments.get(right).getY() > segments.get(largest).getY()) {
            largest = right;
        }

        // Swap root with largest element and recursively heapify the affected subtree
        if (largest != index) {
            Segment temp = segments.get(index);
            segments.set(index, segments.get(largest));
            segments.set(largest, temp);
            heapify(segments, largest, size);
        }

        // Recursively heapify the left and right subtrees
        if (left < size) {
            heapify(segments, left, size);
        }

        if (right < size) {
            heapify(segments, right, size);
        }
    }
}
