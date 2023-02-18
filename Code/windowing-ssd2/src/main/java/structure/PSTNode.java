package structure;

public class PSTNode {
    private Segment segment;
    private int median;
    private PSTNode leftChild;
    private PSTNode rightChild;

    public PSTNode(Segment segment, PSTNode leftChild, PSTNode rightChild) {
        this.segment = segment;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.median = 0;
    }

    public PSTNode(Segment segment) {
        this(segment, null, null);
    }

    public Segment getSegment() {
        return this.segment;
    }

    public int getMedian() {
        return this.median;
    }

    public void setMedian(int median) {
        this.median = median;
    }

    public PSTNode getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(PSTNode node) {
        this.leftChild = node;
    }

    public PSTNode getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(PSTNode node) {
        this.rightChild = node;
    }

    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }

    public double getMaxYLeft(){
        return getLeftChild().getSegment().getyPrime();
    }

    public double getMinYLeft(){
        return getLeftChild().getSegment().getY();
    }

    public double getMaxXLeft(){
        return getLeftChild().getSegment().getxPrime();
    }

    public double getMinXLeft(){
        return getLeftChild().getSegment().getX();
    }

    public double getMaxYRight(){
        return getRightChild().getSegment().getyPrime();
    }

    public double getMinYRight(){
        return getRightChild().getSegment().getY();
    }

    public double getMaxXRight(){
        return getRightChild().getSegment().getxPrime();
    }

    public double getMinXRight(){
        return getRightChild().getSegment().getX();
    }
}