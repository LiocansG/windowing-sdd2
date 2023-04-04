package com.SDD.structureTest;

import com.SDD.structure.Segment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing of the class {@link Segment}
 */
public class SegmentTest {

    private static ArrayList<Segment> segments;

    @BeforeAll
    public static void setUp(){
        segments = new ArrayList<>();
        segments.add(new Segment(652.0,652.0, -804.0,798.0));
        segments.add(new Segment(-49.0, -724.0, 211.0, 211.0));
        segments.add(new Segment(748.0, 777.0, -636.0, -636.0));
        segments.add(new Segment(518.0,518.0, -500.0,-770.0));
        segments.add(new Segment(-376.0, -376.0, -892.0,-129.0));
        segments.add(new Segment(125.0, 125.0, -616.0, 11.0));
        segments.add(new Segment(-564.0,381.0, -222.0, -222.0));
        segments.add(new Segment(58.0, 58.0,  356.0, 266.0));
        segments.add(new Segment(244.0,645.0, -690.0, -690.0));
        segments.add(new Segment(-338.0,-338.0, -554.0, -906.0));
    }

    @Test
    public void isSegmentGood(){
        boolean isGood = true;
        for (Segment segment : segments) {
            if (isGood && segment.getX() > segment.getxPrime() && segment.getY() > segment.getyPrime()) {
                isGood = false;
                break;
            }
        }
        assertTrue(isGood);
    }
}
