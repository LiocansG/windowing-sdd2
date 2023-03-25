package utilities;

import structure.Segment;

import java.util.ArrayList;

public abstract class Utility {

    public static ArrayList<Segment> opposeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> opposedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            opposedSegments.add(oppose(segments.get(i)));
        }
        return opposedSegments;
    }

    public static ArrayList<Segment> exchangeArray(ArrayList<Segment> segments) {
        ArrayList<Segment> exchangedSegments = new ArrayList<>();
        for (int i = 0; i < segments.size(); i++) {
            exchangedSegments.add(exchange(segments.get(i)));
        }
        return exchangedSegments;
    }

    public static Segment oppose(Segment segment) {
        return new Segment(-segment.getX(), -segment.getY(), -segment.getxPrime(), -segment.getyPrime());
    }

    public static Segment exchange(Segment segment) {
        return new Segment(segment.getY(), segment.getX(), segment.getyPrime(), segment.getxPrime());
    }
}
