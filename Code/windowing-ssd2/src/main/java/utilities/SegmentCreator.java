package utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SegmentCreator {
    public static void main(String[] args) {
        Random r = new Random();
        int[] iterations = {10, 100, 1000, 10000, 100000} ;
        for (int i: iterations) {
            writeSegmentsFile(r.nextInt(1000 - 10) + 10, i);
        }
    }

    private static void writeSegmentsFile(int bound, int iterations) {

        String path = "src/main/resources/random_data_test/"+ iterations + "_segments.txt";
        try(FileWriter fw = new FileWriter(path)){
            fw.write("-"+ bound + " " + bound + " -" + bound + " " + bound +"\n");
            for(int i = 0; i < iterations; i++){
                int[] segment = segmentRandomCreation(bound);
                for (int coord: segment) {
                    fw.write(coord +" ");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int[] segmentRandomCreation(int bound){
        int[] segment;
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
        segment = new int[]{x, y, x_prime, y_prime};
        return segment;
    }
}
