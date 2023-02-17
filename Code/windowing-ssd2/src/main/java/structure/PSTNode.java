package structure;

public class PSTNode {
    private Segment segment;
    private int median;
    private PSTNode leftChild;
    private PSTNode rightChild;

    public PSTNode(Segment segment, PSTNode leftChild, PSTNode rightChild) {
        this.segment = segment;
        this.leftChild = null;
        this.rightChild = null;
        this.median = Math.max(
                leftChild != null ? leftChild.getMedian() : Integer.MIN_VALUE,
                rightChild != null ? rightChild.getMedian() : Integer.MIN_VALUE
        );
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
}