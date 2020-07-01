/**
 * Linked List Class.
 * @param <K> Key
 */
public class MyLinkedList<K> {
    /**
     * Head
     */
    private ListNode head;
    /**
     * Tail
     */
    private ListNode tail;
    /**
     * Size of list
     */
    private int size;
    /**
     * Current list node
     */
    private ListNode cur;

    /**
     * Add to the tail of the linked list
     * @param key key
     */
    public void addAtTail(K key) {
        ListNode newNode = new ListNode(key,null);
        if(tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = tail.getNext();
        }
        size++;
    }

    /**
     * Add to the head of the linked list
     * @param key key
     */
    public void addAtHead(K key) {
        ListNode newNode = new ListNode(key,null);
        if(head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    /**
     * Get the value of the node
     * @param index
     * @return
     */
    public K getNodeVal(int index) {
        if(index<0 || index>=getSize()) {
            return null;
        }
        int count = 0;
        ListNode cur = head;
        while(cur != null && count<index) {
            cur = cur.getNext();
            count++;
        }
        return (K) cur.getVal();
    }

    /**
     * Reset the location of the cur node
     */
    public void reset() {
        cur = head;
    }

    /**
     * Check whether has next node
     * @return whether has next node
     */
    public boolean hasNext() {
        return cur!=null;
    }

    /**
     * Get the next node value
     * @return next node value
     */
    public K next() {
        if(!hasNext()) {
            return null;
        }
        K nextVal = (K) cur.getVal();
        cur = cur.getNext();
        return nextVal;
    }

    /**
     * Get the size of the linked list
     * @return the size of the linked list
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the tail data
     * @return the tail data
     */
    public K getTailData() {
        if(tail == null) {
            return null;
        }
        return (K) tail.getVal();
    }

    /**
     * Get all the content of the linked list
     * @return the content of the linked list
     */
    public String toString() {
        StringBuilder sb =new StringBuilder();
        ListNode cur = head;
        while(cur != null) {
            sb.append(cur.getVal().toString());
            cur = cur.getNext();
        }
        return sb.toString();
    }
}
