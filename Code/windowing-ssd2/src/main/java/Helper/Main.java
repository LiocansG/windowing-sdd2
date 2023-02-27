package Helper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int[] bounds = {790, 570}; //limit size of the window
        int[] iterations = {10, 100, 1000, 10000, 100000} ;
        for (int i: iterations) {
            writeSegmentsFile(bounds, i);
        }
    }

    private static void writeSegmentsFile(int[] bounds, int iterations) {

        String path = "src/main/resources/random_data_test/"+ iterations + "_segments.txt";
        try(FileWriter fw = new FileWriter(path)){
            fw.write("0 "+ bounds[0] +" 0 "+ bounds[1] +"\n");
            for(int i = 0; i < iterations; i++){
                int[] segment = segmentRandomCreation(bounds);
                for (int coord: segment) {
                    fw.write(coord +" ");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int[] segmentRandomCreation(int[] bounds){
        int[] segment;
        Random rnd = new Random();

        int x, y, x_prime, y_prime;

        int axis_lock = rnd.nextInt(2);
        if(axis_lock == 0){//lock X axis
            x = rnd.nextInt(bounds[0]);
            x_prime = x;
            y = rnd.nextInt(bounds[1]);
            y_prime = rnd.nextInt(bounds[1]);
        }else{//lock Y axis
            x = rnd.nextInt(bounds[0]);
            x_prime = rnd.nextInt(bounds[0]);
            y = rnd.nextInt(bounds[1]);
            y_prime = y;
        }
        segment = new int[]{x, y, x_prime, y_prime};
        return segment;
    }
}
