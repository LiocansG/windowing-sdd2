package com.SDD.structure;

/**
 * A class representing a node in a priority search tree.
 * A PSTNode contains a Segment object, a median value, and pointers to its left and right children.
 */

public class PSTNode {
    private Segment segment;
    private int median;
    private PSTNode leftChild;
    private PSTNode rightChild;

    /**
     * Constructs a PSTNode with the given Segment object, left child node, and right child node.
     *
     * @param segment the Segment object to be stored in this node
     * @param leftChild the left child node of this node
     * @param rightChild the right child node of this node
     */
    public PSTNode(Segment segment, PSTNode leftChild, PSTNode rightChild) {
        this.segment = segment;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Constructs a PSTNode with the given Segment object and null left and right child nodes.
     *
     * @param segment the Segment object to be stored in this node
     */
    public PSTNode(Segment segment) {
        this(segment, null, null);
    }

    /**
     * Returns the Segment object stored in this node.
     *
     * @return the Segment object stored in this node
     */
    public Segment getSegment() {
        return segment;
    }

    /**
     * Returns the median value of the Segment's y-coordinates.
     *
     * @return the median value of the Segment's y-coordinates
     */
    public int getMedian() {
        return median;
    }

    /**
     * Returns the median value of the Segment's y-coordinates.
     *
     * @return the median value of the Segment's y-coordinates
     */
    public int getSpecialMedian(Boolean isRight) {
        if(isLeaf()){
            if (isRight){
                return Integer.MAX_VALUE;
            }
            return Integer.MIN_VALUE;
        }
        return median;
    }

    /**
     * Sets the median value of the Segment's y-coordinates.
     *
     * @param median the new median value to be set
     */
    public void setMedian(int median) {
        this.median = median;
    }

    /**
     * Returns the left child node of this node.
     *
     * @return the left child node of this node
     */
    public PSTNode getLeftChild() {
        return leftChild;
    }

    /**
     * Sets the left child node of this node.
     *
     * @param node the new left child node to be set
     */
    public void setLeftChild(PSTNode node) {
        this.leftChild = node;
    }

    /**
     * Returns the right child node of this node.
     *
     * @return the right child node of this node
     */
    public PSTNode getRightChild() {
        return rightChild;
    }

    /**
     * Sets the right child node of this node.
     *
     * @param node the new right child node to be set
     */
    public void setRightChild(PSTNode node) {
        rightChild = node;
    }

    /**
     * Returns whether this node is a leaf node (i.e., has no children).
     *
     * @return true if this node is a leaf node, false otherwise
     */
    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }
}