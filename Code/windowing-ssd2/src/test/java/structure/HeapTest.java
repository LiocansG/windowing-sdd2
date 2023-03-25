package structure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeapTest {

    private static ArrayList<Segment> segments;
    @BeforeAll
    public static void setUp(){
        Segment segment1 = new Segment(1,2,3,4);
        Segment segment2 = new Segment(2,3,4,5);
        Segment segment3 = new Segment(3,4,5,6);
        Segment segment4 = new Segment(4,5,6,7);
        Segment segment5 = new Segment(5,6,7,8);
        segments = new ArrayList<>();
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        segments.add(segment5);
        Collections.shuffle(segments);
    }

    @Test
    public void heapify(){
        Boolean arraySorted = true;
        System.out.println(segments);
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
