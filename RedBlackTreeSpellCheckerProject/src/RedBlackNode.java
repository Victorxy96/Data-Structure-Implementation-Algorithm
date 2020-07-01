/**
 * Construct a RedBlackNode with data, color, parent pointer, left child
 * pointer and right child pointer.
 *
 * @author: Yan Xing
 * @create: 2020-02-10 20:23
 **/
public class RedBlackNode {
    public static final int BLACK = 0;
    public static final int RED = 1;
    public int color;
    private String data;
    private RedBlackNode lc;
    private RedBlackNode rc;
    private RedBlackNode p;

    /**
     * Construct a RedBlackNode with data, color, parent pointer, left child pointer and right child pointer.
     * @param color color
     * @param data data
     * @param lc left child
     * @param rc right child
     * @param p parent
     */
    public RedBlackNode(int color, String data, RedBlackNode lc, RedBlackNode rc, RedBlackNode p) {
        this.color = color;
        this.data = data;
        this.lc = lc;
        this.rc = rc;
        this.p = p;
    }

    /**
     * The getColor() method returns RED or BLACK.
     * @return color
     */
    public int getColor() {
        return color == BLACK ? BLACK : RED;
    }

    /**
     * The setColor() method sets the color of the RedBlackNode.
     * @param color color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * The getData() method returns the data in the node.
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * The setData() method sets the data or key of the RedBlackNode.
     * @param data data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * The getLc() method sets the left child of the RedBlackNode.
     * @return left child node
     */
    public RedBlackNode getLc() {
        return lc;
    }

    /**
     * The setLc() method sets the left child of the RedBlackNode.
     * @param lc left child node
     */
    public void setLc(RedBlackNode lc) {
        this.lc = lc;
    }

    /**
     * The getRc() method returns the right child of the RedBlackNode.
     * @return right child node
     */
    public RedBlackNode getRc() {
        return rc;
    }

    /**
     * The setRc() method sets the right child of the RedBlackNode.
     * @param rc right child node
     */
    public void setRc(RedBlackNode rc) {
        this.rc = rc;
    }

    /**
     * The getP() method returns the parent of the RedBlackNode.
     * @return parent
     */
    public RedBlackNode getP() {
        return p;
    }

    /**
     * The setP() method sets the parent of the RedBlackNode.
     * @param p parent
     */
    public void setP(RedBlackNode p) {
        this.p = p;
    }

    /**
     * The toString() methods returns a string representation of the RedBlackNode.
     * @return to string
     */
    @Override
    public String toString() {
        String data = getData();
        String color = getColor() == 0 ? "Black" : "Red";
        String parent = getP() == null ? "-1": getP().getData();
        String lcString = getLc() == null ? "-1" : getLc().getData();
        String rcString = getRc() == null ? "-1" : getRc().getData();
        return "[data = "+data+":Color = "+color+
                ":Parent = "+parent+":LC = "+lcString+":RC = "+rcString+
                "]";
    }
}
