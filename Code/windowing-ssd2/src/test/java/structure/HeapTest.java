package structure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utilities.SegmentCreator;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeapTest {

    private static ArrayList<Segment> segments;
    @BeforeAll
    public static void setUp(){
        segments = new ArrayList<>();
        double [] values;
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            values = SegmentCreator.segmentRandomCreation(10000);
            segments.add(new Segment(values[0],values[2], values[1], values[3]));
        }
    }

    @Test
    public void heapify(){
        Boolean arraySorted = true;
        Heap.heapify(segments);
        System.out.println(segments);
        // Check if the segments are sorted in non-decreasing order of their Y-coordinates
        for (int i = 1; i < segments.size(); i++) {
            if (segments.get(i).getY() < segments.get(i - 1).getY()) {
                arraySorted = false;
                break;
            }
        }
        assertEquals(true, arraySorted);
    }
}
