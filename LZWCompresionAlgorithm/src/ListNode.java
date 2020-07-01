/**
 * ListNode class
 * @param <K> key
 */
public class ListNode<K> {
    /**
     * List node value
     */
    private K val;
    /**
     * Next List node
     */
    private ListNode next;

    /**
     * Constructor method
     * @param val value
     * @param next next node
     */
    public ListNode(K val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * Get value
     * @return value
     */
    public K getVal() {
        return val;
    }

    /**
     * Set value
     * @param val value
     */
    public void setVal(K val) {
        this.val = val;
    }

    /**
     * Get next node
     * @return next node
     */
    public ListNode getNext() {
        return next;
    }

    /**
     * Set next node
     * @param next next node
     */
    public void setNext(ListNode next) {
        this.next = next;
    }
}
