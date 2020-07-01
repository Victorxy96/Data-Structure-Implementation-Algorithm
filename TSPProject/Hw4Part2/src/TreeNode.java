/**
 * MST tree node.
 */
public class TreeNode {
    /**
     * The parent node index for the current node.
     */
    private int parentIndex;
    /**
     * The node weight, initial should be max integer
     */
    private double weight;
    /**
     * Whether the node is visited
     */
    private boolean visited;

    /**
     * Constructor method.
     */
    public TreeNode() {
        this.parentIndex = 0;
        this.weight = Double.MAX_VALUE;
        this.visited = false;
    }

    /**
     * Get for index of the parent node.
     * @return the index of the parent node
     */
    public int getParentIndex() {
        return parentIndex;
    }

    /**
     * Set for index of the parent node.
     * @param parentIndex the index of the parent node
     */
    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    /**
     * Get for the weight of the node.
     * @return the weight of the node
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the weight of the node
     * @param weight the weight of the node
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Get whether the node is visited.
     * @return whether the node is visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Set whether the node is visited.
     * @param visited whether the node is visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
