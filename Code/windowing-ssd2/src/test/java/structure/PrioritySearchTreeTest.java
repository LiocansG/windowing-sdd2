package structure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PrioritySearchTreeTest {

    static ArrayList<Segment> segments;
    static ArrayList<Segment> segments_opposed;
    static ArrayList<Segment> segments_exchanged;
    static Segment segmentOpposed;

    @BeforeAll
    static public void setUp(){
        segments = new ArrayList<>();
        segments_opposed = new ArrayList<>();
        segments_exchanged = new ArrayList<>();

        segmentOpposed = new Segment(1,2,3,4);
        segmentOpposed.setX(-1);
        segmentOpposed.setY(-2);
        segmentOpposed.setxPrime(-3);
        segmentOpposed.setyPrime(-4);

        segments.add(new Segment(1,2,3,4));
        segments_exchanged.add(new Segment(2,1,4,3));
        segments_opposed.add(segmentOpposed);

    }

    @Test
    void opposeArray() {
        assertEquals(segments_opposed.toString(), PrioritySearchTree.opposeArray(segments).toString());

    }

    @Test
    void exchangeArray() {
        assertEquals(segments_exchanged.toString(), PrioritySearchTree.exchangeArray(segments).toString());
    }
}