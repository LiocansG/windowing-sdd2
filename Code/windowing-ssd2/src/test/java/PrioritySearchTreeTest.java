import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import structure.PSTNode;
import structure.PrioritySearchTree;
import structure.Segment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class PrioritySearchTreeTest {

    PrioritySearchTree prioritySearchTree = new PrioritySearchTree(creatingSegments("src/main/resources/data/10_segments.txt"));

    @Test
    public void checkTree(){

        Assertions.assertTrue(isPSTValid(prioritySearchTree));
    }

    private boolean isPSTValid(PrioritySearchTree PST){
        if(PST.getRoot().isLeaf()){
            return true;
        }
        return isSubTreeValid(PST.getRoot());
    }

    private boolean isSubTreeValid(PSTNode node){
        if (node.isLeaf())
            return true;

        boolean conform = true;
        PSTNode left = node.getLeftChild();
        PSTNode right = node.getRightChild();
        if (left != null && right != null) {
            conform = left.getMedian() <= right.getMedian();
            conform = conform && isSubTreeValid(left) && isSubTreeValid(right);
        }
        else if (left != null)
            conform = isSubTreeValid(left);

        else if (right != null)
            conform = isSubTreeValid(right);

        return conform;
    }

    private ArrayList<Segment> creatingSegments(String path) {
        try {
            FileReader fileR = new FileReader(path);
            BufferedReader br = new BufferedReader(fileR);
            String line;
            String[] temp;
            Double[] tab;
            br.readLine();
            ArrayList<Segment> segments = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                temp = line.split(" ");
                tab = new Double[4];
                for (int i = 0; i < 4; i++) {
                    tab[i] = Double.parseDouble(temp[i]);
                }
                segments.add(new Segment(tab[0], tab[1], tab[2], tab[3]));
            }
            return segments;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
