package com.SDD.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * A class for creating random segments and writing them to files.
 */
public class SegmentCreator {


    /**
     * The main method that creates the random segments and writes them to files for different number of iterations.
     *
     * @param args The arguments passed to the main method.
     */
    public static void main(String[] args) {
        Random r = new Random();
        int[] iterations = {10, 100, 1000, 10000, 100000} ;
        for (int i: iterations) {
            writeSegmentsFile(r.nextInt(1000 - 10) + 10, i);
        }
    }

    /**
     * A method that writes the randomly created segments to a file.
     *
     * @param bound The upper bound for the coordinates of the segment.
     * @param iterations The number of segments to be created.
     */
    private static void writeSegmentsFile(int bound, int iterations) {

        String path = "src/main/resources/random_data_test/"+ iterations + "_segments.txt";
        try(FileWriter fw = new FileWriter(path)){
            fw.write("-"+ bound + " " + bound + " -" + bound + " " + bound +"\n");
            for(int i = 0; i < iterations; i++){
                double[] segment = segmentRandomCreation(bound);
                for (double coord: segment) {
                    fw.write(coord +" ");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that creates a random segment.
     *
     * @param bound The upper bound for the coordinates of the segment.
     * @return A double array containing the coordinates of the segment.
     */
    public static double[] segmentRandomCreation(int bound){
        double[] segment;
        Random rnd = new Random();

        int x, y, x_prime, y_prime;

        int axis_lock = rnd.nextInt(2);
        if(axis_lock == 0){//lock X axis
            x = rnd.nextInt(2) == 0 ? rnd.nextInt(bound) : - rnd.nextInt(bound);
            x_prime = x;
            y = rnd.nextInt(2) == 0 ? rnd.nextInt(bound) : - rnd.nextInt(bound);
            y_prime = rnd.nextInt(2) == 0 ? rnd.nextInt(bound) : - rnd.nextInt(bound);
        }else{//lock Y axis
            x = rnd.nextInt(2) == 0 ? rnd.nextInt(bound) : - rnd.nextInt(bound);
            x_prime = rnd.nextInt(2) == 0 ? rnd.nextInt(bound) : - rnd.nextInt(bound);
            y = rnd.nextInt(2) == 0 ? rnd.nextInt(bound) : - rnd.nextInt(bound);
            y_prime = y;
        }
        segment = new double[]{x, x_prime, y, y_prime};
        return segment;
    }
}
