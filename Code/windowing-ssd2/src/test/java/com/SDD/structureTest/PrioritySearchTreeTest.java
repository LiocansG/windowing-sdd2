package com.SDD.structureTest;

import com.SDD.structure.PSTNode;
import com.SDD.structure.PstWrapper;
import com.SDD.structure.Segment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing of the class {@link PstWrapper}
 */
public class PrioritySearchTreeTest {

    private static PstWrapper pstWrapper;

    @BeforeAll
    public static void setUp(){
        ArrayList<Segment> segments = new ArrayList<>();
        segments.add(new Segment(652.0,652.0, -804.0,798.0));
        segments.add(new Segment(-49.0, -724.0, 211.0, 211.0));
        segments.add(new Segment(748.0, 777.0, -636.0, -636.0));
        segments.add(new Segment(518.0,518.0, -500.0,-770.0));
        segments.add(new Segment(-376.0, -376.0, -892.0,-129.0));
        segments.add(new Segment(125.0, 125.0, -616.0, 11.0));
        segments.add(new Segment(-564.0,381.0, -222.0, -222.0));
        segments.add(new Segment(58.0, 58.0,  356.0, 266.0));
        segments.add(new Segment(244.0,645.0, -690.0, -690.0));
        segments.add(new Segment(-338.0,-338.0, -554.0, -906.0));
        pstWrapper = new PstWrapper(segments);
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
}
