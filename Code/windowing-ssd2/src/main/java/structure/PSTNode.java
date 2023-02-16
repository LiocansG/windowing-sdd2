package structure;

public class PSTNode {
    private int key;
    private int priority;
    private PSTNode left;
    private PSTNode right;

    public PSTNode(int key, int priority) {
        this.key = key;
        this.priority = priority;
        this.left = null;
        this.right = null;
    }

    public int getKey() {
        return this.key;
    }

    public int getPriority() {
        return this.priority;
    }

    public PSTNode getLeft() {
        return this.left;
    }

    public void setLeft(PSTNode node) {
        this.left = node;
    }

    public PSTNode getRight() {
        return this.right;
    }

    public void setRight(PSTNode node) {
        this.right = node;
    }
}
