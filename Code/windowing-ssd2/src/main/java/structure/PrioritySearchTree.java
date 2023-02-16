package structure;

public class PrioritySearchTree {
    private PSTNode root;

    public PrioritySearchTree() {
        this.root = null;
    }

    public void insert(int key, int priority) {
        this.root = insertHelper(this.root, key, priority);
    }

    private PSTNode insertHelper(PSTNode node, int key, int priority) {
        if (node == null) {
            return new PSTNode(key, priority);
        }

        if (priority < node.getPriority()) {
            PSTNode newNode = new PSTNode(key, priority);
            newNode.setLeft(node);
            return newNode;
        }

        if (key < node.getKey()) {
            node.setLeft(insertHelper(node.getLeft(), key, priority));
        } else {
            node.setRight(insertHelper(node.getRight(), key, priority));
        }

        return node;
    }

    public void search(int minPriority, int maxKey) {
        searchHelper(this.root, minPriority, maxKey);
    }

    private void searchHelper(PSTNode node, int minPriority, int maxKey) {
        if (node == null) {
            return;
        }

        if (node.getPriority() >= minPriority) {
            if (node.getKey() <= maxKey) {
                // Do something with the node
            }
            searchHelper(node.getLeft(), minPriority, maxKey);
        }

        if (node.getKey() > maxKey) {
            searchHelper(node.getLeft(), minPriority, maxKey);
        }

        searchHelper(node.getRight(), minPriority, maxKey);
    }
}