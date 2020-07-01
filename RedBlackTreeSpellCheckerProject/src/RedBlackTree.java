/**
 * Reb Black Trees A red-black tree is a binary search tree with an
 * extra bit of storage per node.
 *
 * @author: Yan Xing
 * @create: 2020-02-10 20:32
 **/
public class RedBlackTree {

    public RedBlackNode root;
    public RedBlackNode nullNode;
    public static final int BLACK = 0;
    public static final int RED = 1;
    private int recentCompares=0;

    private int diameter = 0;

    /**
     *  A red-black tree is a binary search tree with an extra bit of storage per node.
     *  The extra bit represents the color of the node. It's either red or black.
     *  Each node contains the fields: color, key, left, right, and p. Any nil
     *  pointers are regarded as pointers to external nodes (leaves) and key bearing
     *  nodes are considered as internal nodes of the tree.
     *
     *  Red-black tree properties:
     *  1. Every node is either red or black.
     *  2. The root is black.
     *  3. Every leaf (nil) is black.
     *  4. If a node is red then both of its children are black.
     *  5. For each node, all paths from the node to descendant leaves contain the
     *     same number of black nodes.
     *
     *  From these properties, it can be shown (by an iduction proof) that
     *  the tree has a height no more than 2 * Lg(n + 1).
     *
     *  In the implementation of the tree, we use a single node to represent all
     *  of the external nulls. Its color will always be black. The parent pointer (p)
     *  in the root will point to this node and so will all the internal nodes
     *  that would normally contain a left or right value of null. In other words,
     *  instead of containing a null pointer as a left child or a null pointer as a
     *  right child, these internal nodes will point to the one node that represents
     *  the external nulls.
     *
     *  This constructor creates an empty RedBlackTree.
     *  It creates a RedBlackNode that represents null.
     *  It sets the internal variable tree to point to this
     *  node. This node that an empty tree points to will be
     *  used as a sentinel node. That is, all pointers in the
     *  tree that would normally contain the value null, will instead
     *  point to this sentinel node.
     */
    public RedBlackTree() {
        nullNode = new RedBlackNode(BLACK,null,null,null,null);
        root = new RedBlackNode(BLACK,null,nullNode,nullNode,nullNode);
    }

    /**
     * Time: O(1)
     * Get size of the tree;
     * @return number of values inserted into the tree.
     */
    public  int getSize() {

        if(root == null) {
            return 0;
        }

        return getSizeHelper(root);
    }

    /**
     * Time: O(n)
     * Helper method to return the size.
     * @param root root node
     * @return size
     */
    private int getSizeHelper(RedBlackNode root) {
        if(root == nullNode) {
            return 0;
        }
        int leftRes = getSizeHelper(root.getLc());
        int rightRes = getSizeHelper(root.getRc());
        return 1+leftRes+rightRes;
    }

    /**
     * Time: O(n)
     * Perfrom an inorder traversal of the tree. The inOrderTraversal(RedBlackNode)
     * method is recursive and displays the content of the tree. It makes calls on
     * System.out.println(). This method would normally be private.
     * @param t the root of the tree on the first call
     */
    public void inOrderTraversal(RedBlackNode t) {
        if(t == nullNode) {
            return;
        }

        inOrderTraversal(t.getLc());

        String data = t.getData();
        String color = t.getColor() == 0 ? "Black" : "Red";
        String parent = t.getP() == nullNode ? "-1": t.getP().getData();
        String lcString = t.getLc() == nullNode ? "-1" : t.getLc().getData();
        String rcString = t.getRc() == nullNode ? "-1" : t.getRc().getData();
        System.out.println("[data = "+data+":Color = "+color+
                ":Parent = "+parent+":LC = "+lcString+":RC = "+rcString+
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
     * @param t the root of the tree on the first call
     */
    public void reverseOrderTraversal(RedBlackNode t) {
        if(t == nullNode) {
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
     * Time:
     * Best: O(1)
     * Worst: O(logn)
     * The insert() method places a data item into the tree.
     * @param value insert value
     */
    public void insert(String value) {

        if (root.getData() == null) {
            root.setData(value);
            return;
        }

        RedBlackNode y = nullNode;
        RedBlackNode x = root;
        while (x != nullNode) {
            y = x;
            if (value.compareTo(x.getData()) < 0) {
                x = x.getLc();
            } else {
                x = x.getRc();
            }
        }

        RedBlackNode insertNode = new RedBlackNode(RED,value, nullNode, nullNode,y);

        if (value.compareTo(y.getData()) < 0) {
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
     * @param x input node
     */
    public void leftRotate(RedBlackNode x) {
        if(x.getRc() == nullNode) {
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
     * @param x input Node
     */
    public void rightRotate(RedBlackNode x) {
        if(x.getLc() == nullNode) {
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
     * The boolean contains() returns true if the String v is in the RedBlackTree and false otherwise.
     * It counts each comparison it makes in the variable recentCompares.
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
     * Time: O(1)
     * Return number of comparisons made in last call on the contains method.
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
     * @param t a pointer to a node in the tree.
     * @return the height of node t
     */
    public int height(RedBlackNode t) {
        if(t == nullNode) {
            return -1;
        }
        int leftRes = height(t.getLc());
        int rightRes = height(t.getRc());
        return Math.max(leftRes,rightRes)+1;
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
            String parent = curNode.getP() == nullNode ? "-1": curNode.getP().getData();
            String lcString = curNode.getLc() == nullNode ? "-1" : curNode.getLc().getData();
            String rcString = curNode.getRc() == nullNode ? "-1" : curNode.getRc().getData();

            System.out.println("[data = "+data+":Color = "+color+
                    ":Parent = "+parent+":LC = "+lcString+":RC = "+rcString+
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
     * Test the RedBlack tree.
     * @param args no command line arguments
     */
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();

        for(int j = 1; j <= 5; j++) rbt.insert(""+j);

        // after 1..5 are inserted
        System.out.println("RBT in order");
        rbt.inOrderTraversal();
        System.out.println("RBT level order");
        rbt.levelOrderTraversal();

        // is 3 in the tree

        if(rbt.contains(""+3)) System.out.println("Found 3");
        else System.out.println("No 3 found");

        // display the height
        System.out.println("The height is " + rbt.height());
    }

    /**
     * Time: O(n)
     * Call the recursive method to get diameter.
     * @return the diameter of tree.
     */
    public int getDiameter() {
        getDiameterHelper(root);
        return diameter;
    }

    /**
     * Time: O(n)
     * Recursive method to get diameter.
     * @param node inout node
     * @return the diameter of tree.
     */
    private int getDiameterHelper(RedBlackNode node) {
        if(node == nullNode) {
            return 0;
        }

        int left = getDiameterHelper(node.getLc());
        int right = getDiameterHelper(node.getRc());
        this.diameter = Math.max(diameter,left+right);
        return Math.max(left,right)+1;
    }

}
