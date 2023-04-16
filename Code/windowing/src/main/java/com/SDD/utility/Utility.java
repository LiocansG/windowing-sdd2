package com.SDD.utility;

import com.SDD.structure.Segment;

import java.util.ArrayList;

/**
 * A utility class providing methods for manipulating arrays of {@link Segment} objects.
 */
public abstract class Utility {

    /**
     * Returns a new ArrayList of {@link Segment} objects that are the opposites of those in the specified list.
     *
     * @param segments the ArrayList of segments to oppose
     * @return a new ArrayList of segments that are the opposites of those in the specified list
     */
    public static ArrayList<Segment> opposeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> opposedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            opposedSegments.add(oppose(segments.get(i)));
        }
        return opposedSegments;
    }

    /**
     * Returns a new ArrayList of {@link Segment} objects that are exchanged versions of those in the specified list.
     *
     * @param segments the ArrayList of segments to exchange
     * @return a new ArrayList of segments that are exchanged versions of those in the specified list
     */
    public static ArrayList<Segment> exchangeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> exchangedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            exchangedSegments.add(exchange(segments.get(i)));
        }
        return exchangedSegments;
    }

    /**
     * Returns a new {@link Segment} object that is the opposite of the specified segment.
     *
     * @param segment the segment to oppose
     * @return a new segment that is the opposite of the specified segment
     */
    public static Segment oppose(Segment segment) {
        return new Segment(-segment.getX(), -segment.getY(), -segment.getxPrime(), -segment.getyPrime());
    }

    /**
     * Returns a new {@link Segment} object that is an exchanged version of the specified segment.
     *
     * @param segment the segment to exchange
     * @return a new segment that is an exchanged version of the specified segment
     */
    public static Segment exchange(Segment segment) {
        return new Segment(segment.getY(), segment.getX(), segment.getyPrime(), segment.getxPrime());
    }
}
