package com.SDD.utilityTest;

import com.SDD.structure.Segment;
import com.SDD.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing of the class {@link Utility}
 */
public class UtilityTest1 {

    static ArrayList<Segment> segments;
    static ArrayList<Segment> segmentsOpposed;
    static ArrayList<Segment> segmentsExchanged;

    @BeforeAll
    static public void setUp(){
        segments = new ArrayList<>();
        segmentsOpposed = new ArrayList<>();
        segmentsExchanged = new ArrayList<>();

        segments.add(new Segment(1,2,3,4));
        segmentsExchanged.add(new Segment(2,1,4,3));
        segmentsOpposed.add(new Segment(-1,-2,-3,-4));

    }

    /**
     * {@link Utility#opposeArray}
     */
    @Test
    public void opposeArray() {
        assertEquals(segmentsOpposed.toString(), Utility.opposeArray(segments).toString());

    }

    /**
     * {@link Utility#exchangeArray}
     */
    @Test
    public void exchangeArray() {
        assertEquals(segmentsExchanged.toString(), Utility.exchangeArray(segments).toString());
    }
}