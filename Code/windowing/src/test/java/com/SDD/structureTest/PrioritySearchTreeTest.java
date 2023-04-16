package com.SDD.structureTest;

import com.SDD.graphic.controller.SegmentController;
import com.SDD.structure.PSTNode;
import com.SDD.structure.PstWrapper;
import com.SDD.structure.Segment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing of the class {@link PstWrapper}
 */
public class PrioritySearchTreeTest {

    private static PstWrapper pstWrapper;

    @BeforeAll
    public static void setUp() throws IOException {
        pstWrapper = loadingSegmentFromFile("100000.txt");
    }

    @Test
    public void isBST(){
        assertTrue(isBST(pstWrapper.getOriginal().getRoot()));
        assertTrue(isBST(pstWrapper.getExchanged().getRoot()));
        assertTrue(isBST(pstWrapper.getOpposed().getRoot()));
        assertTrue(isBST(pstWrapper.getOpposedExchanged().getRoot()));
    }

    public boolean isBST(PSTNode node) {
        if (node == null) {
            return true;
        }

        if(node.getLeftChild() != null && node.getMedian() < node.getLeftChild().getSpecialMedian(false)){
            return false;
        }

        if(node.getRightChild() != null  && node.getMedian() > node.getRightChild().getSpecialMedian(true)){
            return false;
        }

        return isBST(node.getLeftChild()) && isBST(node.getRightChild());
    }

    @Test
    public void isWellBalanced() {
        assertTrue(isWellBalanced(pstWrapper.getOriginal().getRoot()));
        assertTrue(isWellBalanced(pstWrapper.getExchanged().getRoot()));
        assertTrue(isWellBalanced(pstWrapper.getOpposed().getRoot()));
        assertTrue(isWellBalanced(pstWrapper.getOpposedExchanged().getRoot()));
    }

    private boolean isWellBalanced(PSTNode node) {
        if (node == null) {
            return true;
        }
        int leftHeight = height(node.getLeftChild());
        int rightHeight = height(node.getRightChild());
        int balanceFactor = leftHeight - rightHeight;
        if (balanceFactor < -1 || balanceFactor > 1) {
            return false;
        }
        return isWellBalanced(node.getLeftChild()) && isWellBalanced(node.getRightChild());
    }

    private int height(PSTNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    public static PstWrapper loadingSegmentFromFile(String file) throws IOException {
        FileReader fileR = new FileReader("src/main/resources/data_test/" + file);
        BufferedReader br = new BufferedReader(fileR);
        String line;
        String[] temp;
        Double[] tab;
        ArrayList<Segment> segments = new ArrayList<>();
        br.readLine();

        while ((line = br.readLine()) != null) {
            temp = line.split(" ");
            tab = new Double[4];
            for (int i = 0; i < 4; i++) {
                tab[i] = Double.parseDouble(temp[i]);
            }
            segments.add(new Segment(tab[0], tab[1], tab[2], tab[3]));
        }
        return new PstWrapper(segments);
    }
}
