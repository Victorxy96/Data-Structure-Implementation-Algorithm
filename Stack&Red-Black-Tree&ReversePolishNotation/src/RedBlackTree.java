import java.math.BigInteger;

/**
 * A red-black tree is a binary search tree with an extra bit of storage per node.
 * The extra bit represents the color of the node. It's either red or black.
 * Each node contains the fields: color, key, left, right, and p. Any nil
 * pointers are regarded as pointers to external nodes (leaves) and key bearing
 * nodes are considered as internal nodes of the tree.
 * <p>
 * Red-black tree properties:
 * 1. Every node is either red or black.
 * 2. The root is black.
 * 3. Every leaf (nil) is black.
 * 4. If a node is red then both of its children are black.
 * 5. For each node, all paths from the node to descendant leaves contain the
 * same number of black nodes.
 *
 * @author: Yan Xing
 * @create: 2020-02-13 16:51
 **/
public class RedBlackTree {
    /**
     * Construct a RedBlackNode with data, color, parent pointer, left child
     * pointer and right child pointer.
     */
    static class RedBlackNode {

        public static final int BLACK = 0;
        public static final int RED = 1;
        public int color;
        private String data;
        private RedBlackNode lc;
        private RedBlackNode rc;
        private RedBlackNode p;
        private BigInteger val;

        /**
         * Construct a RedBlackNode with data, color, parent pointer, left child pointer and right child pointer.
         *
         * @param color color
         * @param data  data
         * @param lc    left child
         * @param rc    right child
         * @param p     parent
         */
        public RedBlackNode(int color, String data, BigInteger val, RedBlackNode lc, RedBlackNode rc, RedBlackNode p) {
            this.color = color;
            this.data = data;
            this.val = val;
            this.lc = lc;
            this.rc = rc;
            this.p = p;
        }

        /**
         * The getColor() method returns RED or BLACK.
         *
         * @return color
         */
        public int getColor() {
            return color == BLACK ? BLACK : RED;
        }

        /**
         * The setColor() method sets the color of the RedBlackNode.
         *
         * @param color color
         */
        public void setColor(int color) {
            this.color = color;
        }

        /**
         * The getData() method returns the data in the node.
         *
         * @return data
         */
        public String getData() {
            return data;
        }

        /**
         * The setData() method sets the data or key of the RedBlackNode.
         *
         * @param data data
         */
        public void setData(String data) {
            this.data = data;
        }

        /**
         * The getLc() method sets the left child of the RedBlackNode.
         *
         * @return left child node
         */
        public RedBlackNode getLc() {
            return lc;
        }

        /**
         * The setLc() method sets the left child of the RedBlackNode.
         *
         * @param lc left child node
         */
        public void setLc(RedBlackNode lc) {
            this.lc = lc;
        }

        /**
         * The getRc() method returns the right child of the RedBlackNode.
         *
         * @return right child node
         */
        public RedBlackNode getRc() {
            return rc;
        }

        /**
         * The setRc() method sets the right child of the RedBlackNode.
         *
         * @param rc right child node
         */
        public void setRc(RedBlackNode rc) {
            this.rc = rc;
        }

        /**
         * The getP() method returns the parent of the RedBlackNode.
         *
         * @return parent
         */
        public RedBlackNode getP() {
            return p;
        }

        /**
         * The setP() method sets the parent of the RedBlackNode.
         *
         * @param p parent
         */
        public void setP(RedBlackNode p) {
            this.p = p;
        }

        /**
         * The toString() methods returns a string representation of the RedBlackNode.
         *
         * @return to string
         */
        @Override
        public String toString() {
            String data = getData();
            String color = getColor() == 0 ? "Black" : "Red";
            String parent = getP() == null ? "-1" : getP().getData();
            String lcString = getLc() == null ? "-1" : getLc().getData();
            String rcString = getRc() == null ? "-1" : getRc().getData();
            return "[data = " + data + ":Color = " + color +
                    ":Parent = " + parent + ":LC = " + lcString + ":RC = " + rcString +
                    "]";
        }

        /**
         * Get value.
         *
         * @return value
         */
        public BigInteger getValue() {
            return val;
        }

        /**
         * Set value
         *
         * @param val value
         */
        public void setValue(BigInteger val) {
            this.val = val;
        }
    }

    /**
     * Queue implemented by dynamic stack. One stack is responsible for queueing
     * and another is responsible for dequeueing
     */
    static class Queue {

        private DynamicStack in;
        private DynamicStack out;

        /**
         * Constructor.
         */
        public Queue() {
            this.in = new DynamicStack();
            this.out = new DynamicStack();
        }

        /**
         * Deque using two stack.
         *
         * @return the element of queue.
         */
        public Object deQueue() {
            // stack 2 is null, have to push all elements of 1 and pop them to 2, then push pop 2
            if (out.isEmpty()) {
                move();
                return out.pop();
            }
            // just pop 2: S1: 4 S2:3 2 1, Queue: 1234. get 1: just pop 1
            return out.pop();
        }

        /**
         * Push elements in stack 1
         */
        public void enQueue(Object element) {
            in.push(element);
        }

        /**
         * Get the size of queue.
         *
         * @return size of queue
         */
        public int size() {
            return in.getSize() + out.getSize();
        }

        /**
         * Whether the queue is empty.
         *
         * @return whether the queue is empty.
         */
        public boolean isEmpty() {
            return in.isEmpty() && out.isEmpty();
        }

        /**
         * Move element in one stack to another.
         */
        private void move() {
            while (!in.isEmpty()) {
                out.push(in.pop());
            }
        }

    }


    public RedBlackNode root;
    public RedBlackNode nullNode;
    public static final int BLACK = 0;
    public static final int RED = 1;
    private int recentCompares = 0;

    /**
     * Constructor method.
     */
    public RedBlackTree() {
        nullNode = new RedBlackNode(BLACK, null, BigInteger.ZERO, null, null, null);
        root = new RedBlackNode(BLACK, null, BigInteger.ZERO, nullNode, nullNode, nullNode);
    }

    /**
     * Time:
     * Best: O(1)
     * Worst: O(logn)
     * The insert() method places a data item into the tree.
     *
     * @param key insert key
     */
    public void insert(String key, BigInteger value) {

        if (root.getData() == null) {
            root.setData(key);
            root.setValue(value);
            return;
        }

        if (contains(key)) {
            overWrite(key, value);
            return;
        }

        RedBlackNode y = nullNode;
        RedBlackNode x = root;
        while (x != nullNode) {
            y = x;
            if (key.compareTo(x.getData()) < 0) {
                x = x.getLc();
            } else {
                x = x.getRc();
            }
        }

        RedBlackNode insertNode = new RedBlackNode(RED, key, value, nullNode, nullNode, y);

        if (key.compareTo(y.getData()) < 0) {
            y.setLc(insertNode);
            RBInsertFixup(y.getLc());
        } else {
            y.setRc(insertNode);
            RBInsertFixup(y.getRc());
        }
    }

    /**
     * Time: O(1)
     * LeftRotate() performs a single left rotation. This would normally be a private method.
     *
     * @param x input node
     */
    public void leftRotate(RedBlackNode x) {
        if (x.getRc() == nullNode) {
            System.out.println("Error! Left rotate can not perform!");
            return;
        }
        RedBlackNode y = x.getRc();
        x.setRc(y.getLc());
        y.getLc().setP(x);
        y.setP(x.getP());

        if (x.getP() == nullNode) {
            root = y;
        } else {
            if (x == x.getP().getLc()) {
                x.getP().setLc(y);
            } else {
                x.getP().setRc(y);
            }
        }

        y.setLc(x);
        x.setP(y);
    }

    /**
     * Time: O(1)
     * RightRotate() performs a single right rotation This would normally be a private method.
     *
     * @param x input Node
     */
    public void rightRotate(RedBlackNode x) {
        if (x.getLc() == nullNode) {
            System.out.println("Error! Right rotate can not perform!");
            return;
        }

        RedBlackNode y = x.getLc();
        x.setLc(y.getRc());
        y.getRc().setP(x);
        y.setP(x.getP());

        if (x.getP() == nullNode) {
            root = y;
        } else {
            if (x == x.getP().getLc()) {
                x.getP().setLc(y);
            } else {
                x.getP().setRc(y);
            }
        }

        y.setRc(x);
        x.setP(y);
    }

    /**
     * Time: O(1)
     * Fixing up the tree so that Red Black Properties are preserved.
     *
     * @param z input node
     */
    public void RBInsertFixup(RedBlackNode z) {
        while (z.getP().getColor() == RED) {
            if (z.getP() == z.getP().getP().getLc()) {
                RedBlackNode y = z.getP().getP().getRc();
                if (y.getColor() == RED) {

                    z.getP().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    z = z.getP().getP();

                } else {
                    if (z == z.getP().getRc()) {
                        z = z.getP();
                        leftRotate(z);
                    }

                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    rightRotate(z.getP().getP());
                }
            } else {
                RedBlackNode y = z.getP().getP().getLc();
                if (y.getColor() == RED) {

                    z.getP().setColor(BLACK);
                    y.setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    z = z.getP().getP();

                } else {
                    if (z == z.getP().getLc()) {
                        z = z.getP();
                        rightRotate(z);
                    }

                    z.getP().setColor(BLACK);
                    z.getP().getP().setColor(RED);
                    leftRotate(z.getP().getP());
                }

            }

            root.setColor(BLACK);
        }
    }

    /**
     * Time:
     * Best: O(1)
     * Worst: O(logn)
     * The boolean contains() returns true if the String key is in the RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
     *
     * @param key the value to search for
     * @return true if key is in the tree, false otherwise;
     */
    public BigInteger get(String key) {
        RedBlackNode cur = root;
        recentCompares = 0;
        while (cur != nullNode) {
            recentCompares++;

            if (cur.getData() == null) {
                return null;
            } else if (key.compareTo(cur.getData()) > 0) {
                cur = cur.getRc();
            } else if (key.compareTo(cur.getData()) < 0) {
                cur = cur.getLc();
            } else {
                return cur.getValue();
            }
        }
        return null;
    }


    /**
     * Time:
     * Best: O(1)
     * Worst: O(logn)
     * The boolean contains() returns true if the String v is in the RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
     *
     * @param v the value to search for
     * @return true if v is in the tree, false otherwise;
     */
    public boolean contains(String v) {
        RedBlackNode cur = root;
        recentCompares = 0;
        while (cur != nullNode) {
            recentCompares++;
            if (v.compareTo(cur.getData()) > 0) {
                cur = cur.getRc();
            } else if (v.compareTo(cur.getData()) < 0) {
                cur = cur.getLc();
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Best: O(1)
     * Worst: O(logn)
     * Overwrite the value of the node given the key string.
     *
     * @param key   key string
     * @param value new value
     */
    private void overWrite(String key, BigInteger value) {
        RedBlackNode cur = root;
        recentCompares = 0;
        while (cur != nullNode) {
            recentCompares++;
            if (key.compareTo(cur.getData()) > 0) {
                cur = cur.getRc();
            } else if (key.compareTo(cur.getData()) < 0) {
                cur = cur.getLc();
            } else {
                cur.setValue(value);
                return;
            }
        }
    }

    /**
     * Time: O(1)
     * Get size of the tree;
     *
     * @return number of values inserted into the tree.
     */
    public int getSize() {

        if (root == null) {
            return 0;
        }

        return getSizeHelper(root);
    }

    /**
     * Time: O(n)
     * Helper method to return the size.
     *
     * @param root root node
     * @return size
     */
    private int getSizeHelper(RedBlackNode root) {
        if (root == nullNode) {
            return 0;
        }
        int leftRes = getSizeHelper(root.getLc());
        int rightRes = getSizeHelper(root.getRc());
        return 1 + leftRes + rightRes;
    }

    /**
     * Time: O(n)
     * Perfrom an inorder traversal of the tree. The inOrderTraversal(RedBlackNode)
     * method is recursive and displays the content of the tree. It makes calls on
     * System.out.println(). This method would normally be private.
     *
     * @param t the root of the tree on the first call
     */
    public void inOrderTraversal(RedBlackNode t) {
        if (t == nullNode) {
            return;
        }

        inOrderTraversal(t.getLc());

        String data = t.getData();
        String color = t.getColor() == 0 ? "Black" : "Red";
        BigInteger value = t.getValue();
        String parent = t.getP() == nullNode ? "-1" : t.getP().getData();
        String lcString = t.getLc() == nullNode ? "-1" : t.getLc().getData();
        String rcString = t.getRc() == nullNode ? "-1" : t.getRc().getData();
        System.out.println("[data = " + data + ":Value = " + value + ":Color = " + color +
                ":Parent = " + parent + ":LC = " + lcString + ":RC = " + rcString +
                "]");

        inOrderTraversal(t.getRc());
    }

    /**
     * Time: O(n)
     * The no argument inOrderTraversal() method calls the
     * recursive inOrderTraversal(RedBlackNode) - passing the root.
     */
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    /**
     * Time: O(n)
     * Perform a reverseOrder traversal of the tree. The reverseOrderTraversal
     * (RedBlackNode) method is recursive and displays the content of the tree
     * in reverse order. This method would normally be private.
     *
     * @param t the root of the tree on the first call
     */
    public void reverseOrderTraversal(RedBlackNode t) {
        if (t == nullNode) {
            return;
        }

        inOrderTraversal(t.getRc());

        System.out.println(t.toString());

        inOrderTraversal(t.getLc());
    }

    /**
     * Time: O(n)
     * The no argument reverseOrderTraversal() method calls the
     * recursive reverseOrderTraversal(RedBlackNode) - passing the root.
     */
    public void reverseOrderTraversal() {
        reverseOrderTraversal(root);
    }

    /**
     * Time: O(1)
     * Return number of comparisons made in last call on the contains method.
     *
     * @return number of comparisons made in last call on the contains method
     */
    public int getRecentCompares() {
        return recentCompares;
    }

    /**
     * Time:
     * Best: O(1)
     * Worst: O(logn)
     * The method closeBy(v) returns a value close to v in the tree. If v is
     * found in the tree it returns v.
     *
     * @param v the value to search close by for.
     * @return the closest string
     */
    public String closeBy(String v) {
        RedBlackNode recentNode = root;
        RedBlackNode cur = root;
        while (cur != nullNode) {

            if (v.compareTo(cur.getData()) > 0) {
                recentNode = cur;
                cur = cur.getRc();
            } else if (v.compareTo(cur.getData()) < 0) {
                recentNode = cur;
                cur = cur.getLc();
            } else {
                return v;
            }
        }
        return recentNode.getData();
    }

    /**
     * Time: O(n)
     * This method calls the recursive method.
     *
     * @return the height of the red black tree.
     */
    public int height() {
        return height(root);
    }

    /**
     * Time: O(n)
     * This a recursive routine that is used to compute the height of the red black tree.
     * It is called by the height() method. The height() method passes the root of the tree to this method.
     * This method would normally be private.
     *
     * @param t a pointer to a node in the tree.
     * @return the height of node t
     */
    public int height(RedBlackNode t) {
        if (t == nullNode) {
            return -1;
        }
        int leftRes = height(t.getLc());
        int rightRes = height(t.getRc());
        return Math.max(leftRes, rightRes) + 1;
    }

    /**
     * Time: O(n)
     * This method displays the RedBlackTree in level order. It first displays the root.
     * Then it displays all children of the root. Then it displays all nodes on level 3 and so on.
     * It is not recursive. It uses a queue.
     */
    public void levelOrderTraversal() {
        Queue queue = new Queue();
        queue.enQueue(root);
        while (!queue.isEmpty()) {
            RedBlackNode curNode = (RedBlackNode) queue.deQueue();

            String data = curNode.getData();
            String color = curNode.getColor() == 0 ? "Black" : "Red";
            BigInteger value = curNode.getValue();
            String parent = curNode.getP() == nullNode ? "-1" : curNode.getP().getData();
            String lcString = curNode.getLc() == nullNode ? "-1" : curNode.getLc().getData();
            String rcString = curNode.getRc() == nullNode ? "-1" : curNode.getRc().getData();

            System.out.println("[data = " + data + ":Value = " + value + ":Color = " + color +
                    ":Parent = " + parent + ":LC = " + lcString + ":RC = " + rcString +
                    "]");
            if (curNode.getLc() != nullNode) {
                queue.enQueue(curNode.getLc());
            }
            if (curNode.getRc() != nullNode) {
                queue.enQueue(curNode.getRc());
            }
        }
    }

    /**
     * Main method for testing the red black tree.
     *
     * @param args args
     */
    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        String var = "var";
        BigInteger value = BigInteger.ZERO;

        System.out.println("Inserting into the red-black tree...");
        for (int i = 1; i <= 50; i++) {
            String key = var + String.valueOf(i);
            value = value.add(BigInteger.ONE);
            redBlackTree.insert(key, value);
        }

        System.out.println("Tree Height: " + redBlackTree.height());
        System.out.println("Tree size: " + redBlackTree.getSize());
        System.out.println("Level Order Traversal: ");
        redBlackTree.levelOrderTraversal();

        System.out.println("In Order Traversal: ");
        redBlackTree.inOrderTraversal();

        System.out.println("Search for var23...");
        System.out.println("value is: " + redBlackTree.get("var23"));

        System.out.println("Insert var23 with value 0...");
        redBlackTree.insert("var23", BigInteger.ZERO);

        System.out.println("Search for var23");
        System.out.println("value is: " + redBlackTree.get("var23"));

    }

}
