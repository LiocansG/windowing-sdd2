import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import structure.PSTNode;
import structure.PrioritySearchTree;

public class PrioritySearchTreeTest {

    PrioritySearchTree prioritySearchTree = new PrioritySearchTree("src/main/resources/data_test/1000.txt");

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



}
