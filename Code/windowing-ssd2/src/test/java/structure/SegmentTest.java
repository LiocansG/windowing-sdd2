package structure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SegmentTest {

    Segment segment;
    Segment segmentExchange;
    Segment segmentOpposed;

    @BeforeEach
    public void setUp(){
        segment = new Segment(1,2,3,4);
        segmentExchange = new Segment(2,1,4,3);
        segmentOpposed = new Segment(1,2,3,4);
        segmentOpposed.setX(-1);
        segmentOpposed.setY(-2);
        segmentOpposed.setxPrime(-3);
        segmentOpposed.setyPrime(-4);
    }
    @Test
    void oppose() {
        segment.oppose();
        assertEquals(segmentOpposed.toString(), segment.toString());
    }

    @Test
    void exchange() {
        segment.exchange();
        assertEquals(segmentExchange.toString(), segment.toString());
    }
}