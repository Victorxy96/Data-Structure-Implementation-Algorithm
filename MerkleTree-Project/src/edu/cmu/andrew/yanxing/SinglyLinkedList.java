package edu.cmu.andrew.yanxing;

import edu.colorado.nodes.ObjectNode;

import java.math.BigInteger;

/**
 * Singly Linked List class
 *
 * SinglyLinkedList has a head and tail pointer.
 *
 * Head and tail are both ObjectNodes.
 *
 * Class invariants:
 *
 * The head pointer is null or points to the first element on the list. The tail pointer is null or points to the last node on the list. An integer countNodes is maintained to keep count of the number of nodes added to the list. This provided an O(1) count to the caller.
 * This class provides iterator methods:
 *
 * reset: to reset the iteration - starting from the head of the list.
 *
 * next: returns the element pointed to by the iterator and increments the iterator to the next node
 *
 * hasNext: returns true if the iterator points to an existing node and false otherwise.
 * @author Yan Xing
 */
public class SinglyLinkedList {
    // head
    private ObjectNode head;
    // tail
    private ObjectNode tail;
    // cursor for iterator
    private ObjectNode cur;
    // node count
    private int countNodes;

    /**
     * Time: O(1)
     * Constructor method.
     */
    public SinglyLinkedList() {
        this.countNodes = 0;
        this.cur = head;
    }

    /**
     * Time: O(1)
     * Add a node containing the Object c to the end of the linked list.
     * @param c add object
     */
    public void  addAtEndNode(Object c) {
        if(tail == null) {
            tail = new ObjectNode(c,null);
            head = tail;
        } else {
            ObjectNode prevTail = tail;
            ObjectNode addedElement = new ObjectNode(c,null);
            prevTail.setLink(addedElement);
            tail = addedElement;
        }
        countNodes++;
    }

    /**
     * Time: O(1)
     * Add a node containing the Object c to the head of the linked list.
     * @param c add object
     */
    public void addAtFrontNode(Object c) {
        if(head == null) {
            head = new ObjectNode(c,null);
            tail = head;
        } else {
            ObjectNode prevHead = head;
            ObjectNode addedElement = new ObjectNode(c,null);
            head = addedElement;
            head.setLink(prevHead);
        }
        countNodes++;
    }

    /**
     * Time: O(1)
     * Counts the number of nodes in the list.
     * @return the current node number
     */
    public int countNodes() {
        return countNodes;
    }

    /**
     * Time: O(1)
     * Returns the data in the tail of the list.
     * @return data in the tail of the list
     */
    public Object getLast() {
        return tail.getData();
    }

    /**
     * Time: O(n)
     * Returns a reference (0 based) to the object with list index i.
     *
     * @param i index
     * @return reference (0 based) to the object with list index i
     */
    public Object getObjectAt(int i) {

        ObjectNode cur = getObjectNodeAt(i);

        if(cur == null) {
            return null;
        }

        return cur.getData();
    }

    /**
     * Time: O(n)
     * Returns a reference (0 based) to the object node with list index i.
     * @param i index i
     * @return object node with list index i
     */
    private ObjectNode getObjectNodeAt(int i) {
        int len = ObjectNode.listLength(head);
        if(i<0||i>len) {
            return null;
        }
        ObjectNode cur = head;
        for(int index= 0;index<i;index++) {
            cur = cur.getLink();
        }
        return cur;
    }

    /**
     * Time: O(1)
     * Reset the iterator to the beginning of the list That is, set a reference to the head of the list.
     */
    public void reset() {
        cur = head;
    }

    /**
     * Time: O(1)
     * Return true if the iterator is not null.
     * @return return true if the iterator is not null
     */
    public boolean hasNext() {
        return cur == null ? false: true;
    }

    /**
     * Time: O(1)
     * return the Object pointed to by the iterator and increment the iterator to the next node in the list.
     * @return the Object pointed to by the iterator
     */
    public Object next() {
        if(cur == null) {
            return null;
        }
        Object curObject = cur.getData();
        cur = cur.getLink();
        return curObject;
    }

    /**
     * Time: O(1)
     * Returns the list as a String.
     * @return the list as a String
     */
    @Override
    public String toString() {
        return head.toString();
    }

    /**
     * Time: O(n)
     * Test Driver: Testing with BigInteger data and a list of lists.
     * @param args input args
     */
    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        // test add end method by adding B and c
        System.out.println("Add 2 & 10 to tail");
        list.addAtEndNode(new ObjectNode(BigInteger.TWO,null));
        list.addAtEndNode(new ObjectNode(BigInteger.TEN,null));
        // test add front method by adding A
        System.out.println("Add 1 to head");
        list.addAtFrontNode(new ObjectNode(BigInteger.ONE,null));
        // test toString method
        System.out.println("The content of current list is: ");
        System.out.println(list.toString());
        // test method to get node at index 2
        System.out.println("The node at index 2 is "+list.getObjectNodeAt(2));
        // test get last and count nodes
        System.out.println("Tail is "+list.getLast());
        System.out.println("Node number is "+list.countNodes());

        // test next and hasNext
        System.out.println("test next method for 2 times, reset first");
        list.reset();
        System.out.println("Content is ");
        System.out.println(list.next());
        System.out.println(list.next());

        System.out.println("Reset and iterate the whole list");
        System.out.println("Content is ");
        list.reset();
        while(list.hasNext()) {
            System.out.println(list.next());
        }

    }

}
