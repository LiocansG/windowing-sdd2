package com.SDD.utilityTest;

import com.SDD.structure.Segment;
import com.SDD.utility.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing of the class {@link Utility}
 */
public class UtiltyTest2 {

    Segment segment;
    Segment segmentExchange;
    Segment segmentOpposed;

    @BeforeEach
    public void setUp(){
        segment = new Segment(1,2,3,4);
        segmentExchange = new Segment(2,1,4,3);
        segmentOpposed = new Segment(-1,-2,-3,-4);
    }

    /**
     * {@link Utility#oppose}
     */
    @Test
    public void oppose() {
        assertEquals(segmentOpposed.toString(), Utility.oppose(segment).toString());
    }

    /**
     * {@link Utility#exchange}
     */
    @Test
    public void exchange() {
        assertEquals(segmentExchange.toString(), Utility.exchange(segment).toString());
    }
}