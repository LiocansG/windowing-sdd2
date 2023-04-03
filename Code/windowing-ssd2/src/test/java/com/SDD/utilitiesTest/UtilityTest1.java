package com.SDD.utilitiesTest;

import com.SDD.structure.Heap;
import com.SDD.structure.Segment;
import com.SDD.utilities.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing of the class {@link Utility}
 */
public class UtilityTest1 {

    static ArrayList<Segment> segments;
    static ArrayList<Segment> segments_opposed;
    static ArrayList<Segment> segments_exchanged;
    static Segment segmentOpposed;

    @BeforeAll
    static public void setUp(){
        segments = new ArrayList<>();
        segments_opposed = new ArrayList<>();
        segments_exchanged = new ArrayList<>();

        segmentOpposed = new Segment(-1,-2,-3,-4);

        segments.add(new Segment(1,2,3,4));
        segments_exchanged.add(new Segment(2,1,4,3));
        segments_opposed.add(segmentOpposed);

    }

    /**
     * {@link Utility#opposeArray}
     */
    @Test
    public void opposeArray() {
        assertEquals(segments_opposed.toString(), Utility.opposeArray(segments).toString());

    }

    /**
     * {@link Utility#exchangeArray}
     */
    @Test
    public void exchangeArray() {
        assertEquals(segments_exchanged.toString(), Utility.exchangeArray(segments).toString());
    }
}